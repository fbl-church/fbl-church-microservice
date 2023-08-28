/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.rest;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fbl.app.accessmanager.service.FeatureAccessService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.WebRole;

import lombok.RequiredArgsConstructor;

/**
 * Gets feature access for a user
 *
 * @author Sam Butler
 * @since 8/3/2020
 */
@RequestMapping("/api/feature/access")
@RestApiController
@RequiredArgsConstructor
public class FeatureAccessController {

    private final FeatureAccessService service;

    /**
     * Get a map of feature access for the provided web roles.
     * 
     * @param roles The web roles to get the feature access for
     * @return {@link Map<String,String>} of the feature access
     */
    @GetMapping
    @HasAccess(WebRole.SITE_ADMINISTRATOR)
    public Map<String, List<Map<String, String>>> getWebRoleFeatureAccess(@RequestParam List<WebRole> roles) {
        return service.getWebRoleFeatureAccess(roles);
    }
}
