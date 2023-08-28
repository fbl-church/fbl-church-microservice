/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.authentication.client;

import com.fbl.app.authentication.client.domain.AuthToken;
import com.fbl.app.authentication.client.domain.request.AuthenticationRequest;
import com.fbl.app.authentication.service.AuthenticationService;
import com.fbl.common.annotations.interfaces.Client;

import lombok.RequiredArgsConstructor;

/**
 * Client method for authentication of a user.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Client
@RequiredArgsConstructor
public class AuthenticationClient {

    private final AuthenticationService service;

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
