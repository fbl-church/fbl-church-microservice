/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.user.client.domain.UserAccess;
import com.fbl.app.user.openapi.TagUser;
import com.fbl.app.user.service.UserAccessService;
import com.fbl.common.annotations.interfaces.RestApiController;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Gets feature access for a user
 *
 * @author Sam Butler
 * @since 8/3/2020
 */
@RequestMapping("/api/users/access")
@RestApiController
@TagUser
public class UserAccessController {

    @Autowired
    private UserAccessService userAccessService;

    /**
     * Gets the current user access. This includes the feature access, application
     * access, and roles.
     *
     * @param authenticationRequest A email and password request.
     * @return a new JWT token.
     */
    @Operation(summary = "Get Current User Access", description = "Gets the current user access. This includes the feature access, application access, and roles")
    @GetMapping
    public UserAccess currentUserAccess() {
        return userAccessService.getCurrentUserAccess();
    }
}
