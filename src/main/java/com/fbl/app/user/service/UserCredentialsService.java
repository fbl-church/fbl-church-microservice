/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.fbl.app.authentication.client.AuthenticationClient;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.client.domain.PasswordUpdate;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.dao.UserCredentialsDAO;
import com.fbl.common.enums.WebRole;
import com.fbl.exception.types.InsufficientPermissionsException;
import com.fbl.jwt.utility.JwtHolder;

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
    private JwtHolder jwtHolder;

    @Autowired
    private UserCredentialsDAO dao;

    @Autowired
    private UserClient userClient;

    @Autowired
    private AuthenticationClient authClient;

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
     * @param id         The id of the user being updated
     * @param passUpdate Object the holds the current password and new user password
     *                   to change it too.
     * @return {@link User} object of the user that was updated.
     */
    public User updateUserPasswordById(int id, PasswordUpdate passUpdate) {
        User updatingUser = userClient.getUserById(id);
        if (id != updatingUser.getId() && !WebRole.hasPermission(jwtHolder.getWebRole(), updatingUser.getWebRole())) {
            throw new InsufficientPermissionsException(String
                    .format("Insufficient permission to update a user of role '%s'", jwtHolder.getWebRole(),
                            updatingUser.getWebRole()));
        }
        return passwordUpdate(id, passUpdate.getNewPassword());
    }

    /**
     * Update the users credentials.
     * 
     * @param userId   Id of the user wanting to update their password.
     * @param password THe password to update on the user's account.
     * @return {@link User} object of the user that was updated.
     */
    private User passwordUpdate(int userId, String password) {
        if (password != null && password.trim() != "") {
            dao.updateUserPassword(userId, BCrypt.hashpw(password, BCrypt.gensalt()));
        }
        return userClient.getCurrentUser();
    }
}
