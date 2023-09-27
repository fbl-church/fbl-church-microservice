/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.email.processors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.email.client.domain.UserEmail;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.common.enums.WebRole;
import com.google.common.collect.Sets;

/**
 * Forgot Password email processor
 * 
 * @author Sam Butler
 * @since December 20, 2022
 */
@Service
public class ContactAdminEmailProcessor extends EmailProcessor<String> {
    private static final String EMAIL_DYNAMIC_NAME = "::USER_NAME::";
    private static final String EMAIL_DYNAMIC_BODY = "::EMAIL_BODY::";

    @Autowired
    private UserClient userClient;

    private String emailMessage;

    @Override
    public List<UserEmail> process() {
        final User emailUser = userClient.getCurrentUser();
        String emailContent = readEmailTemplate("ContactAdminEmail.html");

        final UserGetRequest request = new UserGetRequest();
        request.setWebRole(Sets.newHashSet(WebRole.ADMINISTRATOR));
        final List<User> adminUsers = userClient.getUsers(request);

        List<UserEmail> emails = new ArrayList<>();
        for (final User user : adminUsers) {
            emails.add(send(user.getEmail(), "New Message", buildEmailBody(emailUser, emailContent)));
        }
        return emails;
    }

    @Override
    public void setParams(final String params) {
        this.emailMessage = params;
    }

    /**
     * Helper method for replacing the dynamic fields in the email message.
     * 
     * @param emailUser The email user.
     * @param content   The content to replace with.
     * @return String of the email content.
     */
    private String buildEmailBody(User emailUser, String content) {
        String username = String.format("%s %s", emailUser.getFirstName().trim(), emailUser.getLastName().trim());
        content = content.replace(EMAIL_DYNAMIC_NAME, username);
        content = content.replace(EMAIL_DYNAMIC_BODY, emailMessage);
        return content;
    }
}
