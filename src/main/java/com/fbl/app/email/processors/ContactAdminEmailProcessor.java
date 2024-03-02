/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.email.processors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.email.client.domain.ContactAdminEmailData;
import com.fbl.app.email.client.domain.UserEmail;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.common.enums.WebRole;
import com.fbl.common.freemarker.FreeMarkerUtility;
import com.google.common.collect.Sets;

/**
 * Forgot Password email processor
 * 
 * @author Sam Butler
 * @since December 20, 2022
 */
@Service
public class ContactAdminEmailProcessor extends EmailProcessor<String> {
    @Autowired
    private UserClient userClient;

    @Autowired
    private FreeMarkerUtility freeMarkerUtility;

    private String emailMessage;

    @Override
    public List<UserEmail> process() throws Exception {
        final User emailUser = userClient.getCurrentUser();
        ContactAdminEmailData data = new ContactAdminEmailData();
        data.setUsername(String.format("%s %s", emailUser.getFirstName(), emailUser.getLastName()));
        data.setEmailMessage(emailMessage);
        String emailContent = freeMarkerUtility.generateMessage("ContactAdminEmail.html", data);

        final UserGetRequest request = new UserGetRequest();
        request.setWebRole(Sets.newHashSet(WebRole.ADMINISTRATOR));
        final List<User> adminUsers = userClient.getUsers(request);

        List<UserEmail> emails = new ArrayList<>();
        for (final User user : adminUsers) {
            emails.add(send(user.getEmail(), "New Message", emailContent));
        }
        return emails;
    }

    @Override
    public void setParams(final String params) {
        this.emailMessage = params;
    }
}
