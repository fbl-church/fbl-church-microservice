/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.service;

import org.springframework.stereotype.Service;

import com.fbl.app.user.client.domain.UserStatus;
import com.fbl.app.user.dao.UserStatusDAO;

import lombok.RequiredArgsConstructor;

/**
 * Class for handling the service calls to the dao.
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
@Service
@RequiredArgsConstructor
public class UserStatusService {

    private final UserStatusDAO dao;

    /**
     * Gets the status for the given user id.
     * 
     * @param userId The id of the user to get the status for.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    public UserStatus getUserStatusById(int userId) {
        return dao.getUserStatusById(userId);
    }
}
