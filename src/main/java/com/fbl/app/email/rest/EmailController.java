/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.email.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.email.client.domain.UserEmail;
import com.fbl.app.email.openapi.TagEmail;
import com.fbl.app.email.service.EmailService;
import com.fbl.app.user.client.domain.User;
import com.fbl.common.annotations.interfaces.RestApiController;

/**
 * Email Controller for dealing with sending emails to users.
 * 
 * @author Sam Butler
 * @since August 1, 2021
 */
@RequestMapping("/api/mail")
@RestApiController
@TagEmail
public class EmailController {
    @Autowired
    private EmailService service;

    /**
     * This will send a forgot password link to the given user. If the email exists
     * in the database then the link will be sent.
     * 
     * @param email Email to search for and send an email too
     */
    @PostMapping("/forgot-password")
    public List<UserEmail> sendForgotPasswordEmail(@RequestBody String email) {
        return service.sendForgotPasswordEmail(email);
    }

    /**
     * This method will be called when a new user creates an account. They will
     * receieve this email to update them on the status of their account and weather
     * or not it has been approved or denied.
     * 
     * @param newUser The email of the new user that was created.
     * 
     */
    @PostMapping("/new-user")
    public List<UserEmail> sendNewUserEmail(User newUser) {
        return service.sendNewUserEmail(newUser);
    }

    /**
     * Email to contact the admin of the website with any questions or concerns that
     * there may be.
     * 
     * @param message The message to send to admin.
     */
    @PostMapping("/contact")
    public List<UserEmail> sendContactAdminEmail(@RequestBody String message) {
        return service.sendContactAdminEmail(message);
    }
}
