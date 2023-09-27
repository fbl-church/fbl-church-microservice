/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.email.processors;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fbl.app.email.client.domain.UserEmail;
import com.fbl.app.user.client.domain.User;

/**
 * Forgot Password email processor
 * 
 * @author Sam Butler
 * @since December 20, 2022
 */
@Service
public class NewUserEmailProcessor extends EmailProcessor<User> {

    private User newUser;

    @Override
    public List<UserEmail> process() {
        String emailContent = readEmailTemplate("NewUserEmail.html");

        return List.of(send(buildUserEmail(newUser.getEmail(), "Welcome to Marc's!",
                emailContent.replace("::USER_NAME::", newUser.getFirstName()))));
    }

    @Override
    public void setParams(User params) {
        this.newUser = params;

    }
}
