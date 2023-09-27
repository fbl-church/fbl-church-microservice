/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.email.processors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.email.client.domain.UserEmail;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.client.domain.User;

/**
 * Forgot Password email processor
 * 
 * @author Sam Butler
 * @since December 20, 2022
 */
@Service
public class UserAccountStatusUpadteEmailProcessor extends EmailProcessor<Integer> {
    @Autowired
    private UserClient userClient;

    private int userId;

    @Override
    public List<UserEmail> process() {
        User emailUser = userClient.getUserById(userId);
        String emailContent = readEmailTemplate(String.format("UserAccount%s.html",
                emailUser.getAccountStatus().name()));

        return List.of(send(buildUserEmail(emailUser.getEmail(), "Marc's Account Update!",
                emailContent.replace("::USER_NAME::", emailUser.getFirstName()))));
    }

    @Override
    public void setParams(Integer params) {
        this.userId = params;

    }
}
