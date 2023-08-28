/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.authentication.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.authentication.client.domain.AuthToken;
import com.fbl.app.authentication.client.domain.request.AuthenticationRequest;
import com.fbl.app.authentication.openapi.TagAuthentication;
import com.fbl.app.authentication.service.AuthenticationService;
import com.fbl.common.annotations.interfaces.RestApiController;

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
     */
    @Operation(summary = "Authentication for a user", description = "Generates a unique JWT token for an authenticated user.")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthToken> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(service.authenticate(authenticationRequest));
    }

    /**
     * Reauthenticates a user and generate a new token.
     *
     * @param authenticationRequest A email and password request.
     * @return a new JWT token.
     */
    @Operation(summary = "Re-authenticate a user", description = "Will re-authenticate a user. An existing token is required.")
    @PostMapping("/reauthenticate")
    public ResponseEntity<AuthToken> reauthenticateUser() {
        return ResponseEntity.ok(service.reauthenticate());
    }
}
