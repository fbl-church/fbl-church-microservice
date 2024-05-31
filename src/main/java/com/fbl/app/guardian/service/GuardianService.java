/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.guardian.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.guardian.client.domain.Guardian;
import com.fbl.app.guardian.client.domain.request.GuardianGetRequest;
import com.fbl.app.guardian.dao.GuardianDAO;
import com.fbl.app.user.client.UserClient;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;
import com.fbl.exception.types.NotFoundException;
import com.google.common.collect.Sets;

/**
 * Guardian Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service
public class GuardianService {

    @Autowired
    private GuardianDAO dao;

    @Autowired
    private UserClient userClient;

    /**
     * Gets a list of guardians based of the request filter
     * 
     * @param request to filter on
     * @return list of guardians objects
     */
    public Page<Guardian> getGuardians(GuardianGetRequest request) {
        return dao.getGuardians(request);
    }

    /**
     * Gets a guardian by id
     * 
     * @param id The guardian id
     * @return The found guardian based on the id
     */
    public Guardian getGuardianById(int id) {
        Guardian foundGuardian = dao.getGuardianById(id).orElseThrow(() -> new NotFoundException("Guardian", id));
        foundGuardian.setWebRole(userClient.getUserRolesById(id, List.of(WebRole.USER)));
        return foundGuardian;
    }

    /**
     * Gets child guardians by child id.
     * 
     * @param childId The child id
     * @return The list of guardians associated to the child
     */
    public List<Guardian> getChildGuardians(int childId) {
        return dao.getChildGuardians(childId);
    }

    /**
     * Gets a child guardian by the child id and guardian Id
     * 
     * @param childId    The child id
     * @param guardianId The guardian id
     * @return The mathcing guardian id
     */
    public Optional<Guardian> getChildGuardianById(int childId, int guardianId) {
        return dao.getChildGuardianById(childId, guardianId);
    }

    /**
     * Checks to see if the guardian already exists for the given information
     * 
     * @param c The guardian to check
     * @return The list of guardian that match the given child
     */
    public Guardian doesGuardianExist(Guardian g) {
        GuardianGetRequest request = new GuardianGetRequest();
        request.setFirstName(Sets.newHashSet(g.getFirstName()));
        request.setLastName(Sets.newHashSet(g.getLastName()));
        List<Guardian> matchingGuardians = getGuardians(request).getList();
        return matchingGuardians.size() > 0 ? matchingGuardians.get(0) : null;
    }
}
