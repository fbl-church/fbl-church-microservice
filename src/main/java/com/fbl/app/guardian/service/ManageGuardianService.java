/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.guardian.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fbl.app.guardian.client.domain.Guardian;
import com.fbl.app.guardian.dao.GuardianDAO;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.client.domain.User;
import com.fbl.common.enums.WebRole;
import com.fbl.exception.types.InsufficientPermissionsException;
import com.fbl.jwt.utility.JwtHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * Manage Guardian Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Slf4j
@Service
public class ManageGuardianService {

    @Autowired
    private GuardianDAO dao;

    @Autowired
    private GuardianService guardianService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtHolder jwtHolder;

    /**
     * Creates a new guardian for the given user object.
     * 
     * @param userId   The user id to associate with
     * @param guardian The guardian to create.
     * @return {@link Guardian} that was created.
     */
    public Guardian insertGuardian(Guardian guardian) {
        User createdUser = userClient.createUser(guardian);
        return assignGuardianToExistingUser(createdUser.getId(), guardian);
    }

    /**
     * Used when assigning the guardian role to an existing user.
     * 
     * @param guardian The guardian to create.
     * @return {@link Guardian} that was created.
     */
    public Guardian assignGuardianToExistingUser(int userId, Guardian guardian) {
        User u = userClient.getUserById(userId);
        guardian.setWebRole(u.getWebRole());
        guardian.getWebRole().add(WebRole.GUARDIAN);

        userClient.updateUserRoles(userId, guardian.getWebRole());
        dao.insertGuardian(userId, guardian);
        return guardianService.getGuardianById(userId);
    }

    /**
     * Update the guardian's profile information. Only guardian can update their own
     * information
     * 
     * @param guardian what information on the guardian needs to be updated.
     * @return guardian associated to that id with the updated information
     */
    public Guardian updateGuardianProfile(int id, Guardian guardian) {
        if (jwtHolder.getUserId() != id) {
            throw new InsufficientPermissionsException(
                    "Insufficient permission: Guardians can only update their own profile information");
        }
        return updateGuardianById(id, guardian);
    }

    /**
     * Update the guardian's information such as email, first name, and last name
     * 
     * @param guardian what information on the guardian needs to be updated.
     * @return guardian associated to that id with the updated information
     */
    public Guardian updateGuardianById(int id, Guardian guardian) {
        userClient.updateUserById(id, guardian, false);
        dao.updateGuardianById(id, guardian);
        return guardianService.getGuardianById(id);
    }

    /**
     * Update the child's list of guardians that they are associated too.
     * 
     * @param childId   The child id to be updated
     * @param guardians The list of guardians to associate to them.
     * @return List of guardians that were updated on the child
     */
    public List<Guardian> updateChildGuardiansById(int childId, List<Guardian> guardians) {
        unassociateChildGuardians(childId);
        associateChild(childId, guardians);
        return guardians;
    }

    /**
     * Associate a child to a list of guardians.
     * 
     * @param childId   The id of the child.
     * @param guardians List of guardians to associate
     */
    public void associateChild(int childId, List<Guardian> guardians) {
        if (CollectionUtils.isEmpty(guardians)) {
            return;
        }

        for (Guardian g : guardians) {
            try {
                if (g.getId() == null) {
                    log.info("Guardian id is null. Creating guardian.");
                    g.setId(insertGuardian(g).getId());
                }
                dao.associateChild(g.getId(), childId, g.getRelationship());
            } catch (Exception e) {
                log.warn("Unable to associate child id '{}' to Guardian id '{}'", childId, g.getId());
            }
        }
    }

    /**
     * Unassociates a child from a list of guardians.
     * 
     * @param childId   The id of the child.
     * @param guardians List of guardians to associate
     */
    public void unassociateChildGuardians(int childId) {
        dao.unassociateChildGuardians(childId);
    }

    /**
     * Delete guardian by id.
     * 
     * @param userId The id of the guardian
     */
    public void deleteGuardian(int userId) {
        dao.deleteGuardian(userId);

        User u = userClient.getUserById(userId);
        if (u.getWebRole().contains(WebRole.GUARDIAN) && u.getWebRole().size() == 1) {
            userClient.deleteUser(userId);
        } else {
            u.getWebRole().removeIf(r -> r.equals(WebRole.GUARDIAN));
            userClient.updateUserRoles(userId, u.getWebRole());
        }
    }
}
