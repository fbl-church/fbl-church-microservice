/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.awana.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.awana.app.user.client.domain.UserStatus;
import com.awana.app.user.dao.UserStatusDAO;

/**
 * Class for handling the service calls to the dao.
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
@Component
public class UserStatusService {

    @Autowired
    private UserStatusDAO dao;

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