/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.rest;

import static org.springframework.http.MediaType.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awana.app.user.client.domain.PasswordUpdate;
import com.awana.app.user.client.domain.User;
import com.awana.app.user.openapi.TagUser;
import com.awana.app.user.service.UserCredentialsService;
import com.awana.common.annotations.interfaces.HasAccess;
import com.awana.common.enums.WebRole;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/users/credentials")
@RestController
@TagUser
public class UserCredentialsController {

    @Autowired
    private UserCredentialsService service;

    /**
     * This will take in a {@link PasswordUpdate} object that will confirm that the
     * current password matches the database password. If it does then it will
     * update the password to the new password.
     * 
     * @param passUpdate Object the holds the current password and new user password
     *                   to change it too.
     * @return {@link User} object of the user that was updated.
     */
    @Operation(summary = "Update the currently logged in user password.", description = "Given a password update request it will update the active users password.")
    @PutMapping(path = "/password", produces = APPLICATION_JSON_VALUE)
    public User updateUserPassword(@RequestBody PasswordUpdate passUpdate) {
        return service.updateUserPassword(passUpdate);
    }

    /**
     * This will take in a {@link PasswordUpdate} object and a user id that needs
     * the password updated.
     * 
     * @param passUpdate Object the holds the current password and new user password
     *                   to change it too.
     * @return {@link User} object of the user that was updated.
     */
    @Operation(summary = "Update a users password by id.", description = "Update a users password given there user id and password update request.")
    @PutMapping(path = "/password/{id}", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.SITE_ADMIN)
    public User updateUserPasswordById(@PathVariable int id, @RequestBody PasswordUpdate passUpdate) {
        return service.updateUserPasswordById(id, passUpdate);
    }
}
