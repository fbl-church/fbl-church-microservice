/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.service;

import java.util.List;
import java.util.Map;

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
import com.google.common.collect.Sets;

/**
 * Feature Access Service
 *
 * @author Sam Butler
 * @since 8/2/2020
 */
@Service
public class FeatureService {

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
}
