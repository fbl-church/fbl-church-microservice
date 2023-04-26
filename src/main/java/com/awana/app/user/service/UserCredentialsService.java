/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.awana.app.authentication.client.AuthenticationClient;
import com.awana.app.user.client.UserClient;
import com.awana.app.user.client.domain.PasswordUpdate;
import com.awana.app.user.client.domain.User;
import com.awana.app.user.dao.UserCredentialsDAO;
import com.awana.exception.types.InsufficientPermissionsException;
import com.awana.jwt.utility.JwtHolder;

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

    @Autowired
    private JwtHolder jwtHolder;

    @Autowired
    private AuthenticationClient authClient;

    @Autowired
    private UserClient userClient;

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

    /**
     * This will take in a {@link PasswordUpdate} object that will confirm that the
     * current password matches the database password. If it does then it will
     * update the password to the new password.
     * 
     * @param passUpdate Object the holds the current password and new user password
     *                   to change it too.
     * @return {@link User} object of the user that was updated.
     */
    public User updateUserPassword(PasswordUpdate passUpdate) {
        authClient.authenticate(jwtHolder.getEmail(), passUpdate.getCurrentPassword());
        return passwordUpdate(jwtHolder.getUserId(), passUpdate.getNewPassword());
    }

    /**
     * Method that will take in an id and a PasswordUpdate object
     * 
     * @param passUpdate Object the holds the current password and new user password
     *                   to change it too.
     * @return {@link User} object of the user that was updated.
     */
    public User updateUserPasswordById(int userId, PasswordUpdate passUpdate) {
        User updatingUser = userClient.getUserById(userId);
        if(userId != updatingUser.getId() && jwtHolder.getWebRole().getRank() <= updatingUser.getWebRole().getRank()) {
            throw new InsufficientPermissionsException(String
                    .format("Your role of '%s' can not update a user of role '%s'", jwtHolder.getWebRole(),
                            updatingUser.getWebRole()));
        }
        return passwordUpdate(userId, passUpdate.getNewPassword());
    }

    /**
     * Update the users credentials.
     * 
     * @param userId   Id of the user wanting to update their password.
     * @param password THe password to update on the user's account.
     * @return {@link User} object of the user that was updated.
     */
    private User passwordUpdate(int userId, String password) {
        if(password != null && password.trim() != "") {
            dao.updateUserPassword(userId, BCrypt.hashpw(password, BCrypt.gensalt()));
        }
        return userClient.getCurrentUser();
    }
}
