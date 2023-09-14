/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.accessmanager.client.domain.Application;
import com.fbl.app.accessmanager.client.domain.request.ApplicationGetRequest;
import com.fbl.app.accessmanager.service.ApplicationService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;

/**
 * Gets feature access for a user
 *
 * @author Sam Butler
 * @since 8/3/2020
 */
@RequestMapping("/api/applications")
@RestApiController
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    /**
     * Gets a page of applications
     * 
     * @param request The request to filter applications
     * @return A page of applications
     */
    @GetMapping
    @HasAccess(WebRole.ADMINISTRATOR)
    public Page<Application> getApplications(ApplicationGetRequest request) {
        return applicationService.getApplications(request);
    }

    /**
     * Gets an application by id.
     * 
     * @param id The id of the application
     * @return The found application
     */
    @GetMapping("/{id}")
    @HasAccess(WebRole.SITE_ADMINISTRATOR)
    public Application getApplicationById(@PathVariable int id) {
        return applicationService.getApplicationById(id);
    }

    /**
     * Update the access of an application
     * 
     * @param application The application to update the status for
     * @param enabled     The status of the application to set.
     * @return The application that was updated
     */
    @PutMapping("/{appId}/enabled/{enabled}")
    @HasAccess(WebRole.ADMINISTRATOR)
    public Application updateApplicationEnabledFlag(@PathVariable int appId, @PathVariable boolean enabled) {
        return applicationService.updateApplicationEnabledFlag(appId, enabled);
    }
}