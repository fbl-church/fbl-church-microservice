/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.guardian.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.guardian.client.domain.Guardian;
import com.fbl.app.guardian.dao.GuardianDAO;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.client.domain.User;
import com.fbl.common.enums.WebRole;
import com.fbl.exception.types.InsufficientPermissionsException;
import com.fbl.jwt.utility.JwtHolder;

import io.jsonwebtoken.lang.Assert;

/**
 * Manage Guardian Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service

public class ManageGuardianService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManageGuardianService.class);
    private static final String GUARDIAN_DEFAULT_PASSWORD = "FBL-GUARDIAN";

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
        guardian.setPassword(GUARDIAN_DEFAULT_PASSWORD);

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
        userClient.updateGuardianUserById(id, guardian);
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
        Assert.notEmpty(guardians, "Can not associate child to list of empty guardians.");
        for (Guardian g : guardians) {
            try {
                if (g.getId() == null) {
                    LOGGER.info("Guardian id is null. Creating guardian.");
                    g.setId(insertGuardian(g).getId());
                }
                dao.associateChild(g.getId(), childId, g.getRelationship());
            } catch (Exception e) {
                LOGGER.warn("Unable to associate child id '{}' to Guardian id '{}'", childId, g.getId());
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
