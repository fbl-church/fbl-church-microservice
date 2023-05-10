/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.featureaccess.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.awana.app.featureaccess.service.FeatureAccessService;
import com.awana.common.annotations.interfaces.HasAccess;
import com.awana.common.annotations.interfaces.RestApiController;
import com.awana.common.enums.WebRole;

/**
 * Gets feature access for a user
 *
 * @author Sam Butler
 * @since 8/3/2020
 */
@RequestMapping("/api/feature/access")
@RestApiController
public class FeatureAccessController {

    @Autowired
    private FeatureAccessService service;

    /**
     * Gets the feature access in an application for user.
     * 
     * @param webRole The web role to get the feature access for
     * @return {@link Map} of the list of feature access.
     */
    @GetMapping("/{webRole}")
    @HasAccess(WebRole.SITE_ADMIN)
    public Map<String, List<Map<String, String>>> getFeatureAccess(@PathVariable WebRole webRole) {
        return service.getFeatureAccess(webRole);
    }
}
