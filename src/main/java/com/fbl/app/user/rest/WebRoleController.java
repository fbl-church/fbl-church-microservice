/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.user.client.domain.request.WebRoleGetRequest;
import com.fbl.app.user.openapi.TagUser;
import com.fbl.app.user.service.WebRoleService;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/roles")
@RestApiController
@TagUser
public class WebRoleController {

    @Autowired
    private WebRoleService webRoleService;

    /**
     * Gets a list of of web roles based on the request
     * 
     * @param request to filter on
     * @return list of webroles objects
     */
    @Operation(summary = "Get a list of web roles.", description = "Given a Web Role Get Request, it will return a list of webroles that match the request.")
    @GetMapping
    public Page<WebRole> getRoles(WebRoleGetRequest request) {
        return webRoleService.getRoles(request);
    }
}
