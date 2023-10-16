/**
 * Copyright of fbl App. All rights reserved.
 */
package com.fbl.app.email.processors;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.email.client.domain.UserEmail;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.environment.EnvironmentService;
import com.fbl.jwt.utility.JwtTokenUtil;
import com.google.common.collect.Sets;

/**
 * Forgot Password email processor
 * 
 * @author Sam Butler
 * @since December 20, 2022
 */
@Service
public class ForgotPasswordEmailProcessor extends EmailProcessor<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordEmailProcessor.class);

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EnvironmentService environmentService;

    private String email;

    @Override
    public List<UserEmail> process() {
        String content = getForgotPasswordContent(email);

        if (!"".equals(content)) {
            return List.of(send(buildUserEmail(email, "Forgot Password", content)));
        } else {
            LOGGER.warn("Email could not be processed. No user found for email '{}'", email);
        }
        return null;
    }

    @Override
    public void setParams(String email) {
        this.email = email;
    }

    /**
     * This will build out the reset password link that will be sent with the email.
     * If the users email does not exist this method will return an empty string and
     * it will not send an email.
     * 
     * @param email The users email to search for and send an email too.
     * @return {@link String} of the email content with the replaced link.
     */
    private String getForgotPasswordContent(String email) {
        final String emailContent = readEmailTemplate("ForgotPasswordEmail.html");

        UserGetRequest request = new UserGetRequest();
        request.setEmail(Sets.newHashSet(email));
        List<User> users = userClient.getUsers(request);

        if (users.size() < 1)
            return "";
        else
            return emailContent.replace("::FORGOT_PASSWORD_LINK::", this.getResetPath(users.get(0)));
    }

    private String getResetPath(User u) {
        String base = environmentService.getEnvironment().getUrl();
        return String.format("%s/reset-password/%s", base, jwtTokenUtil.generateToken(u, true));
    }
}
