/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.accessmanager.client.domain.Application;
import com.fbl.app.accessmanager.client.domain.WebRoleApp;
import com.fbl.app.accessmanager.client.domain.request.ApplicationGetRequest;
import com.fbl.app.accessmanager.client.domain.request.WebRoleAppGetRequest;
import com.fbl.app.accessmanager.dao.ApplicationDAO;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;
import com.fbl.exception.types.NotFoundException;
import com.fbl.exception.types.ServiceException;
import com.google.common.collect.Sets;

/**
 * Feature Access Service
 *
 * @author Sam Butler
 * @since 8/2/2020
 */
@Service
public class ApplicationService {
    private static final List<WebRole> FILTERED_ROLES = List.of(WebRole.LEADER, WebRole.WORKER);

    @Autowired
    private ApplicationDAO dao;

    /**
     * Gets a page of applications
     * 
     * @param request The request to filter applications
     * @return A page of applications
     */
    public Page<Application> getApplications(ApplicationGetRequest request) {
        return dao.getApplications(request);
    }

    /**
     * Gets an application by id.
     * 
     * @param id The id of the application
     * @return The found application
     */
    public Application getApplicationById(int id) {
        ApplicationGetRequest request = new ApplicationGetRequest();
        request.setId(Sets.newHashSet(id));
        List<Application> apps = getApplications(request).getList();

        if (apps.isEmpty()) {
            throw new NotFoundException("Application", id);
        }
        return apps.get(0);
    }

    /**
     * Get a page of web role app access
     * 
     * @param request The request to filter on.
     * @return {@link Page} of the app access
     */
    public Page<WebRoleApp> getPageOfWebRoleApps(int appId, WebRoleAppGetRequest request) {
        return dao.getPageOfWebRoleApps(appId, request);
    }

    /**
     * Update the access of an application
     * 
     * @param application The application to update the status for
     * @param enabled     The status of the application to set.
     * @return The application that was updated
     */
    public Application updateApplicationEnabledFlag(int appId, boolean enabled) {
        dao.updateApplicationEnabledFlag(appId, enabled);
        return getApplicationById(appId);
    }

    /**
     * Updates the app access for the web role
     * 
     * @param appId   The web role feature update
     * @param webRole The web role to update
     * @param boolean The access to give
     * @return The updated web role app
     */
    public WebRoleApp updateWebRoleAppAccess(int appId, WebRole webRole, boolean access) {
        dao.updateWebRoleAppAccess(appId, webRole, access);
        WebRoleAppGetRequest request = new WebRoleAppGetRequest();
        request.setWebRole(Sets.newHashSet(webRole));
        return getPageOfWebRoleApps(appId, request).getList().get(0);
    }

    /**
     * Create a new application
     * 
     * @param application The application to be created
     * @return The created application
     */
    public Application createNewApplication(Application app) {
        int createdId = dao.createNewApplication(app);
        resetApplicationRoles(createdId);
        return getApplicationById(createdId);
    }

    /**
     * Delete an application
     * 
     * @param id The id of the application to delete
     */
    public void deleteApplicationById(int id) {
        dao.deleteApplicationById(id);
    }

    /**
     * Assign roles to the application id. This will remove all existing roles on
     * the application and insert the roles again with default access of false.
     * 
     * @param appId The app to assign the roles too.
     */
    private void resetApplicationRoles(int appId) {
        dao.deleteRolesFromApplication(appId);

        List<WebRole> roles = Arrays.asList(WebRole.values());
        for (WebRole r : roles.stream().filter(r -> !FILTERED_ROLES.contains(r)).collect(Collectors.toList())) {
            try {
                dao.assignWebRoleToApplication(appId, r, false);
            } catch (Exception e) {
                throw new ServiceException(
                        String.format("Unable to assign role %s to application id '%i'", r.toString(), appId));
            }
        }
    }
}
