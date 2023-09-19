/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.accessmanager.client.domain.CRUD;
import com.fbl.app.accessmanager.client.domain.Feature;
import com.fbl.app.accessmanager.client.domain.WebRoleFeature;
import com.fbl.app.accessmanager.client.domain.request.FeatureGetRequest;
import com.fbl.app.accessmanager.client.domain.request.WebRoleFeatureGetRequest;
import com.fbl.app.accessmanager.service.FeatureService;
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
@RequestMapping("/api/features")
@RestApiController
public class FeatureController {

    @Autowired
    private FeatureService service;

    /**
     * Get a page of features
     * 
     * @param request The request to filter on.
     * @return {@link Page} of the features
     */
    @GetMapping
    @HasAccess(WebRole.ADMINISTRATOR)
    public Page<Feature> getPageOfFeatures(FeatureGetRequest request) {
        return service.getPageOfFeatures(request);
    }

    /**
     * Get feature by id
     * 
     * @param id The id of the feature to get
     * @return {@link Page} of the features
     */
    @GetMapping("/{id}")
    @HasAccess(WebRole.ADMINISTRATOR)
    public Feature getFeatureById(@PathVariable int id) {
        return service.getFeatureById(id);
    }

    /**
     * Get a page of web role feature access
     * 
     * @param request The request to filter on.
     * @return {@link Page} of the feature access
     */
    @GetMapping("/{featureId}/roles")
    @HasAccess(WebRole.ADMINISTRATOR)
    public Page<WebRoleFeature> getPageOfWebRoleFeatures(@PathVariable int featureId,
            WebRoleFeatureGetRequest request) {
        return service.getPageOfWebRoleFeatures(featureId, request);
    }

    /**
     * Client method to get a map of feature access for the provided user id.
     * 
     * @param userId The user id of the user.
     * @return {@link Map<String,String>} of the feature access
     */
    @GetMapping("/user/{id}")
    @HasAccess(WebRole.ADMINISTRATOR)
    public Map<String, List<Map<String, String>>> getWebRoleFeatureAccess(@PathVariable int id) {
        return service.getWebRoleFeatureAccess(id);
    }

    /**
     * Updates the crud access for the given web role feature
     * 
     * @param webRoleFeature The web role feature update
     * @return The updated web role feature
     */
    @PutMapping("/{featureId}/roles/{webRole}")
    @HasAccess(WebRole.ADMINISTRATOR)
    public WebRoleFeature updateWebRoleFeatureAccess(@PathVariable int featureId, @PathVariable WebRole webRole,
            @RequestBody CRUD crud) {
        return service.updateWebRoleFeatureAccess(featureId, webRole, crud);
    }

}
