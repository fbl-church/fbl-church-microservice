/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.awana.app.user.dao.UserCredentialsDAO;

import io.jsonwebtoken.lang.Assert;

/**
 * User Service class that handles all service calls to the
 * {@link UserCredentialsDAO}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Service
public class UserCredentialsService {

    @Autowired
    private UserCredentialsDAO dao;

    /**
     * This will be called when new users are created so that they have default
     * passwords. This will only be called when someone else is creating a user
     * account for someone.
     * 
     * @param userId The id to add the password for.
     * @param pass   Contains the hashed password.
     * @throws Exception
     */
    public void insertUserPassword(int userId, String pass) {
        Assert.hasText(pass, "Password must not be empty");
        dao.insertUserPassword(userId, BCrypt.hashpw(pass, BCrypt.gensalt()));
    }
}
