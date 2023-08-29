/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.client;

import org.springframework.beans.factory.annotation.Autowired;

import com.fbl.app.user.client.domain.UserStatus;
import com.fbl.app.user.service.ManageUserStatusService;
import com.fbl.app.user.service.UserStatusService;
import com.fbl.common.annotations.interfaces.Client;

/**
 * This class exposes the user endpoint's to other app's to pull data across the
 * platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class UserStatusClient {

    @Autowired
    private UserStatusService userStatusService;

    @Autowired
    private ManageUserStatusService manageUserStatusService;

    /**
     * Gets the status for the given user id.
     * 
     * @param userId The id of the user to get the status for.
     * @return {@link UserStatus} object
     */
    public UserStatus getUserStatusById(int userId) {
        return userStatusService.getUserStatusById(userId);
    }

    /**
     * Inserts the given user status object into the db. Will check that the passed
     * in user id is exists first.
     * 
     * @param userStatus     Object to be inserted.
     * @param updatingUserId The user id that is inserting the user status.
     * @return {@link UserStatus} object
     */
    public UserStatus insertUserStatus(UserStatus userStatus) {
        return manageUserStatusService.insertUserStatus(userStatus);
    }

    /**
     * Updates a users status for the given user id
     * 
     * @param userId The id of the user to get the status for.
     * @return {@link UserStatus} object
     */
    public UserStatus updateUserStatusByUserId(int userId, UserStatus userStatus) {
        return manageUserStatusService.updateUserStatusByUserId(userId, userStatus);
    }

    /**
     * Updates a users app access for the given user id
     * 
     * @param userId    The id of the user to get the status for.
     * @param appAccess boolean determining what access the user has.
     * @return {@link UserStatus} object
     */
    public UserStatus updateUserAppAccessByUserId(int userId, Boolean appAccess) {
        return manageUserStatusService.updateUserAppAccessByUserId(userId, appAccess);
    }
}
