package com.fbl.app.accessmanager.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.accessmanager.client.domain.Application;
import com.fbl.app.accessmanager.client.domain.request.ApplicationGetRequest;
import com.fbl.app.accessmanager.service.ApplicationService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;

import lombok.RequiredArgsConstructor;

/**
 * Gets feature access for a user
 *
 * @author Sam Butler
 * @since 8/3/2020
 */
@RequestMapping("/api/applications")
@RestApiController
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

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
}
