/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.email.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.email.client.domain.UserEmail;
import com.fbl.app.email.processors.ContactAdminEmailProcessor;
import com.fbl.app.email.processors.EmailProcessor;
import com.fbl.app.email.processors.ForgotPasswordEmailProcessor;
import com.fbl.app.email.processors.NewUserEmailProcessor;
import com.fbl.app.user.client.domain.User;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for doing all the dirty work for sending a message.
 * 
 * @author Sam Butler
 * @since August 1, 2021
 */
@Service
@Slf4j
public class EmailService {

    @Autowired
    private ContactAdminEmailProcessor contactAdminEmailProcessor;

    @Autowired
    private ForgotPasswordEmailProcessor forgotPasswordEmailProcessor;

    @Autowired
    private NewUserEmailProcessor newUserEmailProcessor;

    /**
     * Sends the user email for the given email processor.
     * 
     * @param <T>       The generic email processor type.
     * @param processor The processor to run.
     * @return The {@link UserEmail} object that was sent.
     */
    public <T> List<UserEmail> sendEmail(EmailProcessor<T> p) {
        try {
            List<UserEmail> emails = p.process();
            emails.stream().forEach(e -> e.setBody(null));
            return emails;
        } catch (Exception e) {
            log.info("Unable to send email", e);
            return List.of();
        }
    }

    /**
     * This will send a forgot password link to the given user. If the email exists
     * in the database then the link will be sent.
     * 
     * @param email Email to search for and send an email too.
     */
    public List<UserEmail> sendForgotPasswordEmail(String email) {
        forgotPasswordEmailProcessor.setParams(email);
        return sendEmail(forgotPasswordEmailProcessor);
    }

    /**
     * This method will be called when a new user creates an account. They will
     * receieve this email to update them on the status of their account and weather
     * or not it has been approved or denied.
     * 
     * @param newUser The new user that was created.
     */
    public List<UserEmail> sendNewUserEmail(User newUser) {
        newUserEmailProcessor.setParams(newUser);
        return sendEmail(newUserEmailProcessor);
    }

    /**
     * Email to contact the admin of the website with any questions or concerns that
     * there may be.
     * 
     * @param message The message to send to admin.
     */
    public List<UserEmail> sendContactAdminEmail(String message) {
        contactAdminEmailProcessor.setParams(message);
        return sendEmail(contactAdminEmailProcessor);
    }
}
