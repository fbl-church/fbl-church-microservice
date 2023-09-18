/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * Get a page of web role feature access
     * 
     * @param request The request to filter on.
     * @return {@link Page} of the feature access
     */
    @GetMapping("/roles")
    @HasAccess(WebRole.ADMINISTRATOR)
    public Page<WebRoleFeature> getPageOfWebRoleFeatures(WebRoleFeatureGetRequest request) {
        return service.getPageOfWebRoleFeatures(request);
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

}
