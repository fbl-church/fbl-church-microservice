/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.client;

import com.fbl.app.user.service.UserCredentialsService;
import com.fbl.common.annotations.interfaces.Client;

import lombok.RequiredArgsConstructor;

/**
 * This class exposes the user endpoint's to other app's to pull data across the
 * platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
@RequiredArgsConstructor
public class UserCredentialsClient {

    private final UserCredentialsService service;

    /**
     * This will be called when new users are created so that they have default
     * passwords. This will only be called when someone else is creating a user
     * account for someone.
     * 
     * @param userId The id to add the password for.
     * @param pass   Contains the hashed password.
     */
    public void insertUserPassword(int userId, String pass) {
        service.insertUserPassword(userId, pass);
    }
}
