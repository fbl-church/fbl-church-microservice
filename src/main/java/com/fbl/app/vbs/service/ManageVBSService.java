package com.fbl.app.vbs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.children.client.domain.request.ChildGetRequest;
import com.fbl.app.children.service.ChildrenService;
import com.fbl.app.children.service.ManageChildrenService;
import com.fbl.app.guardian.client.GuardianClient;
import com.fbl.app.guardian.client.domain.Guardian;
import com.fbl.app.vbs.client.domain.VBSRegistration;
import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.dao.VBSDAO;
import com.fbl.common.enums.ChurchGroup;

/**
 * VBS Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service
public class ManageVBSService {

    private final List<ChurchGroup> VBS_GROUPS = List.of(ChurchGroup.VBS_JUNIOR, ChurchGroup.VBS_MIDDLER,
            ChurchGroup.VBS_PRE_PRIMARY, ChurchGroup.VBS_PRIMARY);

    @Autowired
    private VBSDAO vbsDao;

    @Autowired
    private ChildrenService childrenService;

    @Autowired
    private ManageChildrenService manageChildrenService;

    @Autowired
    private GuardianClient guardianClient;

    /**
     * The theme to be created
     * 
     * @param theme The theme to create
     */
    public VBSTheme getThemeById(int id) {
        return vbsDao.getThemeById(id);
    }

    /**
     * The theme to be created
     * 
     * @param theme The theme to create
     */
    public VBSTheme createTheme(VBSTheme theme) {
        int id = vbsDao.createTheme(theme);
        return getThemeById(id);
    }

    /**
     * Takes in a list of children to register for VBS
     * 
     * @param registration The vbs registration
     */
    public void registerChildren(VBSRegistration registration) {
        List<Guardian> guardians = processGuardians(registration.getGuardians());
        processChildren(registration.getChildren(), guardians);
    }

    /**
     * Process the guardians on the vbs registration request
     * 
     * @param guardians The guardians to be processed
     * @return The created and processed guardians
     */
    private List<Guardian> processGuardians(List<Guardian> guardians) {
        List<Guardian> createdGuardians = new ArrayList<>();
        for (Guardian g : guardians) {
            if (g.getId() != null && g.getId() > 0) {
                // Id is on the guardian, look them up by that id
                Guardian existingGuardian = guardianClient.getGuardianById(g.getId());
                createdGuardians.add(existingGuardian);
            } else {
                // Guardian is new, create them
                createdGuardians.add(guardianClient.insertGuardian(g));
            }
        }
        return createdGuardians;
    }

    /**
     * Processes the children to be registered for VBS.
     * 
     * @param children  The children to register
     * @param guardians The guardians of the children being registered.
     */
    private void processChildren(List<Child> children, List<Guardian> guardians) {
        for (Child c : children) {
            if (c.getId() != null && c.getId() > 0) {
                Child processingChild = childrenService.getChildById(c.getId());
                registerChildForVBS(processingChild, c.getChurchGroup());
            } else {
                assignGuardianIdsToNewChildGuardians(c, guardians);
                createNewChild(c);
            }
        }
    }

    /**
     * Will create a new child for the given information. If a child already exists
     * by the child's first name, last name, and birthday, then it will update that
     * child and return that matching child.
     * 
     * @param c The child to create or update
     * @return The inserted or matching child
     */
    private void createNewChild(Child c) {
        Child matchedChild = getMatchingChild(c);
        if (matchedChild == null) {
            manageChildrenService.insertChild(c);
        } else {
            matchedChild = manageChildrenService.updateChildById(matchedChild.getId(), c, false);
            c.setId(matchedChild.getId());
            registerChildForVBS(matchedChild, c.getChurchGroup());
            addGuardiansToChild(matchedChild, c.getGuardians());
        }
    }

    /**
     * Gets the matching child based on the passed in information
     * 
     * @param c The child to check
     * @return The child if it was found, otherwise null.
     */
    private Child getMatchingChild(Child c) {
        ChildGetRequest request = new ChildGetRequest();
        request.setFirstName(Set.of(c.getFirstName()));
        request.setLastName(Set.of(c.getLastName()));
        request.setBirthday(Set.of(c.getBirthday()));
        List<Child> matchingChildren = childrenService.getChildren(request).getList();
        return matchingChildren.size() > 0 ? matchingChildren.get(0) : null;
    }

    /**
     * Will register the processing child with the given vbs groups. If the
     * vbsGroups is empty then it will not update the child groups.
     * 
     * @param c         The child to update
     * @param vbsGroups The vbs groups to add to the child
     */
    private void registerChildForVBS(Child c, List<ChurchGroup> vbsGroups) {
        if (!CollectionUtils.isEmpty(vbsGroups)) {
            List<ChurchGroup> childGroups = removeVBSGroups(c.getChurchGroup());
            childGroups.addAll(vbsGroups);
            manageChildrenService.updateChildGroupsById(c.getId(), childGroups);
        }
    }

    /**
     * Adds the list of guardians to the child if they are not already associated to
     * them.
     * 
     * @param c         The child to add the guardians too
     * @param guardians The list of guardians.
     */
    private void addGuardiansToChild(Child c, List<Guardian> guardians) {
        List<Integer> currentGuardians = c.getGuardians().stream().map(Guardian::getId).toList();
        List<Guardian> newGuardians = guardians.stream().filter(g -> !currentGuardians.contains(g.getId())).toList();
        if (!newGuardians.isEmpty()) {
            guardianClient.associateChild(c.getId(), newGuardians);
        }
    }

    /**
     * If child has guardians, it will find the guardians in the list by id and
     * assign that id to the child guardians with the relationship
     * 
     * @param c         The child to update guardians for
     * @param guardians The guardians with the ids
     */
    private void assignGuardianIdsToNewChildGuardians(Child c, List<Guardian> guardians) {
        c.getGuardians().forEach(g -> {
            Optional<Integer> foundId = guardians.stream().filter(guard -> guard.getPhone().equals(g.getPhone()))
                    .map(Guardian::getId).findFirst();
            foundId.ifPresent(g::setId);
        });
    }

    /**
     * Takes in a list of church groups and will remove all VBS groups from the
     * list.
     * 
     * @param groups The list of groups to update
     * @return The filterd list of groups.
     */
    private List<ChurchGroup> removeVBSGroups(List<ChurchGroup> groups) {
        if (!CollectionUtils.isEmpty(groups)) {
            return groups.stream().filter(cg -> !VBS_GROUPS.contains(cg)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
