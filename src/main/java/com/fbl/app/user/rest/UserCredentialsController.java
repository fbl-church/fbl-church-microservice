/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.user.client.domain.PasswordUpdate;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.openapi.TagUser;
import com.fbl.app.user.service.UserCredentialsService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.WebRole;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/users/credentials")
@RestApiController
@RequiredArgsConstructor
@TagUser
public class UserCredentialsController {

    private final UserCredentialsService service;

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
    @PutMapping("/password")
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
    @PutMapping("/password/{id}")
    @HasAccess(WebRole.SITE_ADMINISTRATOR)
    public User updateUserPasswordById(@PathVariable int id, @RequestBody PasswordUpdate passUpdate) {
        return service.updateUserPasswordById(id, passUpdate);
    }
}
