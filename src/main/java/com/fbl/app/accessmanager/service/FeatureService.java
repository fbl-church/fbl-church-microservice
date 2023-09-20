/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.accessmanager.client.domain.CRUD;
import com.fbl.app.accessmanager.client.domain.Feature;
import com.fbl.app.accessmanager.client.domain.WebRoleFeature;
import com.fbl.app.accessmanager.client.domain.request.FeatureGetRequest;
import com.fbl.app.accessmanager.client.domain.request.WebRoleFeatureGetRequest;
import com.fbl.app.accessmanager.dao.FeatureDAO;
import com.fbl.app.user.client.UserClient;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;
import com.fbl.exception.types.BaseException;
import com.google.common.collect.Sets;

import io.jsonwebtoken.lang.Assert;

/**
 * Feature Access Service
 *
 * @author Sam Butler
 * @since 8/2/2020
 */
@Service
public class FeatureService {
    private static final List<WebRole> FILTERED_ROLES = List.of(WebRole.LEADER, WebRole.WORKER);

    @Autowired
    private FeatureDAO dao;

    @Autowired
    private UserClient userClient;

    /**
     * Get a page of features
     * 
     * @param request The request to filter on.
     * @return {@link Page} of the features
     */
    public Page<Feature> getPageOfFeatures(FeatureGetRequest request) {
        return dao.getPageOfFeatures(request);
    }

    /**
     * Get feature by id
     * 
     * @param id The id of the feature to get
     * @return {@link Page} of the features
     */
    public Feature getFeatureById(int id) {
        FeatureGetRequest request = new FeatureGetRequest();
        request.setId(Sets.newHashSet(id));
        return getPageOfFeatures(request).getList().get(0);
    }

    /**
     * Get a page of web role feature access
     * 
     * @param request The request to filter on.
     * @return {@link Page} of the feature access
     */
    public Page<WebRoleFeature> getPageOfWebRoleFeatures(int featureId, WebRoleFeatureGetRequest request) {
        return dao.getPageOfWebRoleFeatures(featureId, request);
    }

    /**
     * Client method to get a map of feature access for the provided user id.
     * 
     * @param userId The user id of the user.
     * @return {@link Map<String,String>} of the feature access
     */
    public Map<String, List<Map<String, String>>> getWebRoleFeatureAccess(int userId) {
        List<WebRole> userRoles = userClient.getUserRolesById(userId);
        return dao.getWebRoleFeatureAccess(userRoles);
    }

    /**
     * Updates the feature enabled flag
     * 
     * @param featureId The feaure id to update
     * @param enabled   The flag to set on the feature
     * @return The updated feature
     */
    public Feature updateFeatureEnabledFlag(int featureId, boolean enabled) {
        dao.updateFeatureEnabledFlag(featureId, enabled);
        return getFeatureById(featureId);
    }

    /**
     * Updates the crud access for the given web role feature
     * 
     * @param webRoleFeature The web role feature update
     * @return The updated web role feature
     */
    public WebRoleFeature updateWebRoleFeatureAccess(int featureId, WebRole webRole, CRUD crud) {
        dao.updateWebRoleFeatureAccess(featureId, webRole, crud);
        WebRoleFeatureGetRequest request = new WebRoleFeatureGetRequest();
        request.setWebRole(Sets.newHashSet(webRole));
        return getPageOfWebRoleFeatures(featureId, request).getList().get(0);
    }

    /**
     * Create a new feature
     * 
     * @param feature The feature to be created
     * @return The created feature
     */
    public Feature createNewFeature(String key, int appId) {
        Assert.notNull(key, "Feature Key can not be null");
        int createdId = dao.createNewFeature(key, appId);
        resetFeatureRoles(createdId);
        return getFeatureById(createdId);
    }

    /**
     * Delete a feature
     * 
     * @param id The id of the feature to delete
     */
    public void deleteFeatureById(int id) {
        dao.deleteFeatureById(id);
    }

    /**
     * Assign roles to the feature id. This will remove all existing roles on
     * the application and insert the roles again with default access of false.
     * 
     * @param appId The feature id to assign the roles too.
     */
    private void resetFeatureRoles(int featureId) {
        dao.deleteRolesFromFeature(featureId);

        List<WebRole> roles = Arrays.asList(WebRole.values());
        for (WebRole r : roles.stream().filter(r -> !FILTERED_ROLES.contains(r)).collect(Collectors.toList())) {
            try {
                dao.assignWebRoleToFeature(featureId, r, false);
            } catch (Exception e) {
                throw new BaseException(
                        String.format("Unable to assign role %s to feature id '%i'", r.toString(), featureId));
            }
        }
    }
}
