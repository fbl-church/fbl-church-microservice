/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.dao;

import static com.fbl.app.accessmanager.mapper.ApplicationMapper.*;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fbl.app.accessmanager.client.domain.Application;
import com.fbl.app.accessmanager.client.domain.request.ApplicationGetRequest;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;
import com.fbl.sql.abstracts.BaseDao;
import com.fbl.sql.builder.SqlParamBuilder;

/**
 * DAO for handing feature access for a user
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class ApplicationDAO extends BaseDao {

    public ApplicationDAO(DataSource source) {
        super(source);
    }

    /**
     * Gets a page of applications
     * 
     * @param request The request to filter applications
     * @return A page of applications
     */
    public Page<Application> getApplications(ApplicationGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams().withParam(APP_ID, request.getId())
                .withParam(NAME, request.getName()).build();
        return getPage("getApplicationsPage", params, APPLICATION_MAPPER);
    }

    /**
     * Update the access of an application
     * 
     * @param application The application to update the status for
     * @param enabled     The status of the application to set.
     * @return The application that was updated
     */
    public void updateApplicationEnabledFlag(int appId, boolean enabled) {
        update("updateApplicationEnabledFlag", parameterSource(ENABLED, enabled).addValue(APP_ID, appId));
    }

    /**
     * Create a new application
     * 
     * @param application The application to be created
     * @return The created application
     */
    public int createNewApplication(Application app) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(NAME, app.getName())
                .withParam(ENABLED, app.isEnabled()).build();

        post("createNewApplication", params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Will assign a web role to an application.
     * 
     * @param appId The app id to assign the role too
     * @param role  The role to be assigned
     */
    public void assignWebRoleToApplication(int appId, WebRole role, boolean enabled) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(WEB_ROLE, role)
                .withParam(ACCESS, enabled).withParam(APP_ID, appId).build();
        post("assignWebRoleToApplication", params);
    }

    /**
     * Delete the roles from the application access
     * 
     * @param appId The app id to remove role access from
     */
    public void deleteRolesFromApplication(int appId) {
        delete("deleteRolesFromApplication", parameterSource(APP_ID, appId));
    }

    /**
     * Delete an application
     * 
     * @param id The id of the application to delete
     */
    public void deleteApplicationById(int appId) {
        delete("deleteApplicationById", parameterSource(APP_ID, appId));
    }
}
