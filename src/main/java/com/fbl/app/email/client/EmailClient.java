/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.email.client;

import org.springframework.beans.factory.annotation.Autowired;

import com.fbl.app.email.service.EmailService;
import com.fbl.app.user.client.domain.User;
import com.fbl.common.annotations.interfaces.Client;

/**
 * Class to expose the email client to other services.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class EmailClient {

    @Autowired
    private EmailService service;

    /**
     * This will send a forgot password link to the given user. If the email exists
     * in the database then the link will be sent.
     * 
     * @param email Email to search for and send an email too.
     */
    public void sendForgotPasswordEmail(String email) {
        service.sendForgotPasswordEmail(email);
    }

    /**
     * This method will be called when a new user creates an account. They will
     * receieve this email to update them on the status of their account and weather
     * or not it has been approved or denied.
     * 
     * @param newUser The new user that was created.
     * 
     */
    public void sendNewUserEmail(User newUser) {
        service.sendNewUserEmail(newUser);
    }
}
