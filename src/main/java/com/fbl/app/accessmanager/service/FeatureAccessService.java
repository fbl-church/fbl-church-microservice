/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.accessmanager.dao.FeatureAccessDAO;
import com.fbl.common.enums.WebRole;

/**
 * Feature Access Service
 *
 * @author Sam Butler
 * @since 8/2/2020
 */
@Service
public class FeatureAccessService {

    @Autowired
    private FeatureAccessDAO dao;

    /**
     * Get a map of feature access for the provided web roles.
     * 
     * @param roles The web roles to get the feature access for
     * @return {@link Map<String,String>} of the feature access
     */
    public Map<String, List<Map<String, String>>> getWebRoleFeatureAccess(List<WebRole> roles) {
        return dao.getWebRoleFeatureAccess(roles);
    }
}
