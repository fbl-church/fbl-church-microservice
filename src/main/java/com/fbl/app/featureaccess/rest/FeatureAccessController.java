/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.featureaccess.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fbl.app.featureaccess.service.FeatureAccessService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.WebRole;

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
    @GetMapping
    @HasAccess(WebRole.SITE_ADMINISTRATOR)
    public Map<String, List<Map<String, String>>> getFeatureAccess(@RequestParam List<WebRole> webRole) {
        return service.getFeatureAccess(webRole);
    }
}
