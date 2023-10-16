/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.email.processors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.email.client.domain.UserEmail;
import com.fbl.app.user.client.domain.User;
import com.fbl.environment.EnvironmentService;
import com.fbl.jwt.utility.JwtTokenUtil;

/**
 * Forgot Password email processor
 * 
 * @author Sam Butler
 * @since December 20, 2022
 */
@Service
public class NewUserEmailProcessor extends EmailProcessor<User> {

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private User user;

    @Override
    public List<UserEmail> process() {
        String emailContent = readEmailTemplate("NewUserEmail.html");
        emailContent = emailContent.replace("::SET_PASSWORD_LINK::", this.getPasswordUpdatePath(user));

        return List.of(send(buildUserEmail(user.getEmail(), "Welcome to the FBL Team!",
                emailContent.replace("::USER_NAME::", user.getFirstName()))));
    }

    private String getPasswordUpdatePath(User u) {
        String base = environmentService.getEnvironment().getUrl();
        return String.format("%s/reset-password/%s", base, jwtTokenUtil.generateToken(u, true));
    }

    @Override
    public void setParams(User params) {
        this.user = params;
    }
}
