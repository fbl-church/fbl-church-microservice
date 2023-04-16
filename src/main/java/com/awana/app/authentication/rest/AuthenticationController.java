/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.authentication.rest;

import static org.springframework.http.MediaType.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.awana.app.authentication.client.domain.AuthToken;
import com.awana.app.authentication.client.domain.request.AuthenticationRequest;
import com.awana.app.authentication.openapi.TagAuthentication;
import com.awana.app.authentication.service.AuthenticationService;
import com.awana.common.annotations.interfaces.RestApiController;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Generates a JWT after being passed a request
 *
 * @author Sam Butler
 * @since August 1, 2021
 */
@RequestMapping("/api")
@RestApiController
@TagAuthentication
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    /**
     * Generates a JWT token from a request
     *
     * @param authenticationRequest A email and password request.
     * @return a JWT token.
     * @throws Exception if authentication request does not match a user.
     */
    @Operation(summary = "Authentication for a user", description = "Generates a unique JWT token for an authenticated user.")
    @PostMapping(path = "/authenticate", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthToken> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        return ResponseEntity.ok(service.authenticate(authenticationRequest));
    }

    /**
     * Reauthenticates a user and generate a new token.
     *
     * @param authenticationRequest A email and password request.
     * @return a new JWT token.
     * @throws Exception If user does not exist.
     */
    @Operation(summary = "Re-authenticate a user", description = "Will re-authenticate a user. An existing token is required.")
    @PostMapping(path = "/reauthenticate", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthToken> reauthenticateUser() throws Exception {
        return ResponseEntity.ok(service.reauthenticate());
    }
}
