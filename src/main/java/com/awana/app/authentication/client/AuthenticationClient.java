/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.authentication.client;

import org.springframework.beans.factory.annotation.Autowired;

import com.awana.app.authentication.client.domain.AuthToken;
import com.awana.app.authentication.client.domain.request.AuthenticationRequest;
import com.awana.app.authentication.service.AuthenticationService;
import com.awana.common.annotations.interfaces.Client;

/**
 * Client method for authentication of a user.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Client
public class AuthenticationClient {

    @Autowired
    private AuthenticationService service;

    /**
     * Authenticates a user for the given email and password.
     *
     * @param email    Entered email at login.
     * @param password Password entered at login.
     * @return {@link AuthToken} with the jwt auth.
     */
    public AuthToken authenticate(String email, String password) {
        return service.authenticate(new AuthenticationRequest(email, password));
    }
}
