package com.fbl.app.children.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.children.dao.ChildrenDAO;
import com.fbl.app.gurdian.client.GurdianClient;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.client.domain.User;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.common.enums.WebRole;

import io.jsonwebtoken.lang.Assert;

/**
 * Manage Children Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service
public class ManageChildrenService {

    private static final String CHILD_DEFAULT_PASSWORD = "FBL-CHILD";

    @Autowired
    private ChildrenDAO dao;

    @Autowired
    private ChildrenService childrenService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private GurdianClient gurdianClient;

    /**
     * Creates a new child for the given user object.
     * 
     * @param child The child to create.
     * @return {@link Child} that was created.
     */
    public Child insertChild(Child child) {
        Assert.notEmpty(child.getGurdians(), "Child must be associated to at least one gurdian");
        child.setPassword(CHILD_DEFAULT_PASSWORD);

        User createdUser = userClient.createUser(child);
        return assignChildRoleToExistingUser(createdUser.getId(), child);
    }

    /**
     * Used when assigning the child role to an existing user.
     * 
     * @param userId The id of the user to assign the child role too.
     * @param child  The child information to store.
     * @return {@link Child} that was created.
     */
    public Child assignChildRoleToExistingUser(int userId, Child child) {
        User u = userClient.getUserById(userId);
        child.setWebRole(u.getWebRole());
        child.getWebRole().add(WebRole.CHILD);
        child.setCuid(generateChildUID(u));

        userClient.updateUserRoles(userId, child.getWebRole());
        dao.insertChild(userId, child);
        assignChildGroups(userId, child.getChurchGroup());
        gurdianClient.associateChild(userId, child.getGurdians());
        return childrenService.getChildById(userId);
    }

    /**
     * Update the child personal information.
     * 
     * @param id    The id of the child to update
     * @param child what information on the child needs to be updated.
     * @return child associated to that id with the updated information
     */
    public Child updateChildById(int id, Child child) {
        userClient.updateUserById(id, child);
        dao.updateChildById(id, child);
        return childrenService.getChildById(id);
    }

    /**
     * Update child groups by id.
     * 
     * @param id    The id of the child to be updated
     * @param group The list of groups to assign to the child
     * @return The updated child with the new groups.
     */
    public Child updateChildGroupsById(int id, List<ChurchGroup> groups) {
        assignChildGroups(id, groups);
        return childrenService.getChildById(id);
    }

    /**
     * Delete a child by id.
     * 
     * @param childId The child id to delete.
     */
    public void deleteChild(int childId) {
        dao.deleteChild(childId);

        User u = userClient.getUserById(childId);
        if (u.getWebRole().contains(WebRole.CHILD) && u.getWebRole().size() <= 2) {
            userClient.deleteUser(childId);
        } else {
            u.getWebRole().removeIf(r -> r.equals(WebRole.CHILD));
            userClient.updateUserRoles(childId, u.getWebRole());
        }
    }

    /**
     * Adds the list of church groups to the given child id.
     * 
     * @param childId The child id
     * @param roles   The church groups to assign to the child id
     */
    private void assignChildGroups(int childId, List<ChurchGroup> groups) {
        dao.deleteChildGroups(childId);

        if (groups == null) {
            groups = new ArrayList<>();
        }

        for (ChurchGroup g : groups) {
            dao.insertChildGroup(childId, g);
        }
    }

    /**
     * Generates a childs unique identifier.
     * 
     * @param u The user child object
     * @return The generated CUID
     */
    private String generateChildUID(User u) {
        long h = u.getInsertDate().getHour();
        long m = u.getInsertDate().getMinute();
        long s = u.getInsertDate().getSecond();
        return String.format("%02d%02d%02d%06d", h, m, s, u.getId());
    }
}
