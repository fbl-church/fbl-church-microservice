/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.user.client.domain.UserStatus;
import com.fbl.app.user.dao.UserStatusDAO;
import com.fbl.jwt.utility.JwtHolder;

/**
 * Class for handling the service calls to the dao.
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
@Service
public class ManageUserStatusService {

    @Autowired
    private JwtHolder jwtHolder;

    @Autowired
    private UserStatusDAO dao;

    @Autowired
    private UserStatusService userStatusService;

    /**
     * Inserts the given user status object into the db. Will check that the passed
     * in user id is exists first.
     * 
     * @param userStatus     Object to be inserted.
     * @param updatingUserId The user id that is inserting the user status.
     * @return {@link UserStatus} object
     */
    public UserStatus insertUserStatus(UserStatus userStatus) {
        Integer insertUserId = jwtHolder.isTokenAvaiable() ? jwtHolder.getUserId() : null;
        dao.insertUserStatus(userStatus, insertUserId);
        return userStatusService.getUserStatusById(userStatus.getUserId());
    }

    /**
     * Updates a users status for the given user id
     * 
     * @param userId The id of the user to get the status for.
     * @return {@link UserStatus} object
     */
    public UserStatus updateUserStatusByUserId(int userId, UserStatus userStatus) {
        userStatus.setUpdatedUserId(jwtHolder.getUserId());
        dao.updateUserStatusByUserId(userId, userStatus);
        return userStatusService.getUserStatusById(userId);
    }

    /**
     * Updates a users app access for the given user id
     * 
     * @param userId    The id of the user to get the status for.
     * @param appAccess boolean determining what access the user has.
     * @return {@link UserStatus} object
     */
    public UserStatus updateUserAppAccessByUserId(int userId, Boolean appAccess) {
        dao.updateUserStatusByUserId(userId, new UserStatus(jwtHolder.getUserId(), null, appAccess, null));
        return userStatusService.getUserStatusById(userId);
    }
}
