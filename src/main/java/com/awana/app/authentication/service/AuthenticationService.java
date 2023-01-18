package com.awana.app.authentication.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.awana.app.authentication.client.domain.AuthToken;
import com.awana.app.authentication.client.domain.request.AuthenticationRequest;
import com.awana.app.authentication.dao.AuthenticationDAO;
import com.awana.app.user.client.UserProfileClient;
import com.awana.app.user.client.domain.User;
import com.awana.app.user.client.domain.request.UserGetRequest;
import com.awana.common.exception.InvalidCredentialsException;
import com.awana.common.jwt.utility.JwtHolder;
import com.awana.common.jwt.utility.JwtTokenUtil;
import com.google.common.collect.Sets;

/**
 * Authorization Service takes a user request and checks the values entered for
 * credentials against known values in the database. If correct credentials are
 * passed, it will grant access to the user requested.
 *
 * @author Sam Butler
 * @since August 2, 2021
 */
@Transactional
@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationDAO dao;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtHolder jwtHolder;

    @Autowired
    private UserProfileClient userProfileClient;

    /**
     * Generates a JWT token from a request
     *
     * @param authenticationRequest A email and password request.
     * @return a new JWT.
     * @throws Exception - if authentication request does not match a user.
     */
    public AuthToken authenticate(AuthenticationRequest request) throws Exception {
        User user = verifyUser(request.getEmail(), request.getPassword());

        String token = jwtTokenUtil.generateToken(user);
        return new AuthToken(token, LocalDateTime.now(), jwtTokenUtil.getExpirationDateFromToken(token), user);
    }

    /**
     * Will re-authenticate the logged in user and give a new token. If the user id
     * can not be returned from the current token, it will error and return null.
     * 
     * @return {@link AuthToken} from the token.
     * @throws Exception If the user for that id does not exist.
     */
    public AuthToken reauthenticate() throws Exception {
        User u = userProfileClient.getUserById(jwtHolder.getUserId());

        String token = jwtTokenUtil.generateToken(u);
        return new AuthToken(token, LocalDateTime.now(), jwtTokenUtil.getExpirationDateFromToken(token), u);
    }

    /**
     * Verifies user credentials.
     *
     * @param email    Entered email at login.
     * @param password Password entered at login.
     * @throws Exception Throw an exception if the credentials do not match.
     */
    private User verifyUser(String email, String password) throws Exception {
        if(BCrypt.checkpw(password, dao.getUserAuthPassword(email))) {
            return getAuthenticatedUser(email);
        }
        else {
            throw new InvalidCredentialsException(email);
        }
    }

    /**
     * Get a user based on their email address. Used when a user has sucessfully
     * authenticated.
     * 
     * @param email The email to search for.
     * @return {@link User} object of the authenticated user.
     * @throws Exception
     */
    private User getAuthenticatedUser(String email) throws Exception {
        UserGetRequest request = new UserGetRequest();
        request.setEmail(Sets.newHashSet(email));
        return userProfileClient.getUsers(request).get(0);
    }
}
