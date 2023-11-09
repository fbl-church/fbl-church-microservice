/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.authentication.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.fbl.app.authentication.client.domain.AuthToken;
import com.fbl.app.authentication.client.domain.request.AuthenticationRequest;
import com.fbl.app.authentication.dao.AuthenticationDAO;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.common.date.TimeZoneUtil;
import com.fbl.exception.types.InvalidCredentialsException;
import com.fbl.exception.types.NotFoundException;
import com.fbl.jwt.domain.JwtPair;
import com.fbl.jwt.utility.JwtHolder;
import com.fbl.jwt.utility.JwtTokenUtil;
import com.google.common.collect.Sets;

/**
 * Authorization Service takes a user request and checks the values entered for
 * credentials against known values in the database. If correct credentials are
 * passed, it will grant access to the user requested.
 *
 * @author Sam Butler
 * @since August 2, 2021
 */
@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationDAO dao;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtHolder jwtHolder;

    @Autowired
    private UserClient userClient;

    /**
     * Generates a JWT token from a request
     *
     * @param authenticationRequest A email and password request.
     * @return a new JWT.
     */
    public AuthToken authenticate(AuthenticationRequest request) {
        User user = verifyUser(request.getEmail(), request.getPassword());

        JwtPair pair = jwtTokenUtil.generateToken(user);
        return new AuthToken(pair.getToken(), LocalDateTime.now(TimeZoneUtil.SYSTEM_ZONE),
                pair.getExpiration(), user);
    }

    /**
     * Will re-authenticate the logged in user and give a new token. If the user id
     * can not be returned from the current token, it will error and return null.
     * 
     * @return {@link AuthToken} from the token.
     */
    public AuthToken reauthenticate() {
        User user = userClient.getUserById(jwtHolder.getUserId());
        userClient.updateUserLastLoginToNow(user.getId());

        JwtPair pair = jwtTokenUtil.generateToken(user);
        return new AuthToken(pair.getToken(), LocalDateTime.now(TimeZoneUtil.SYSTEM_ZONE),
                pair.getExpiration(), user);
    }

    /**
     * Verifies user credentials.
     *
     * @param email    Entered email at login.
     * @param password Password entered at login.
     */
    public User verifyUser(String email, String password) {
        String hashedPassword = dao.getUserAuthPassword(email).orElseThrow(() -> new NotFoundException(String
                .format("User not found or does not have access for email: '%s'", email)));

        if (BCrypt.checkpw(password, hashedPassword)) {
            User authUser = getAuthenticatedUser(email);
            return userClient.updateUserLastLoginToNow(authUser.getId());
        } else {
            throw new InvalidCredentialsException(email);
        }
    }

    /**
     * Get a user based on their email address. Used when a user has sucessfully
     * authenticated.
     * 
     * @param email The email to search for.
     * @return {@link User} object of the authenticated user.
     */
    private User getAuthenticatedUser(String email) {
        UserGetRequest request = new UserGetRequest();
        request.setEmail(Sets.newHashSet(email));
        return userClient.getUsers(request).get(0);
    }
}
