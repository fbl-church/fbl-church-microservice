/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.fbl.app.accessmanager.client.domain.Application;
import com.fbl.app.accessmanager.client.domain.request.ApplicationGetRequest;
import com.fbl.app.accessmanager.dao.ApplicationDAO;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;
import com.fbl.exception.types.BaseException;
import com.fbl.exception.types.NotFoundException;
import com.google.common.collect.Sets;

/**
 * Feature Access Service
 *
 * @author Sam Butler
 * @since 8/2/2020
 */
@Service
public class ApplicationService {

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
     * Update the access of an application
     * 
     * @param application The application to update the status for
     * @param enabled     The status of the application to set.
     * @return The application that was updated
     */
    @CacheEvict(cacheNames = "user.apps", allEntries = true)
    public Application updateApplicationEnabledFlag(int appId, boolean enabled) {
        Application app = getApplicationById(appId);
        dao.updateApplicationEnabledFlag(app.getId(), enabled);
        return getApplicationById(app.getId());
    }

    /**
     * Create a new application
     * 
     * @param application The application to be created
     * @return The created application
     */
    @CacheEvict(cacheNames = "user.apps", allEntries = true)
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

        for (WebRole r : WebRole.values()) {
            try {
                dao.assignWebRoleToApplication(appId, r, false);
            } catch (Exception e) {
                throw new BaseException(
                        String.format("Unable to assign role %s to application id '%i'", r.toString(), appId));
            }
        }
    }
}
