/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.featureaccess.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.featureaccess.dao.FeatureAccessDAO;
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
     * Gets the feature access in an application for user.
     * 
     * @return {@link Map} of the list of feature access.
     */
    public Map<String, List<Map<String, String>>> getFeatureAccess(WebRole role) {
        return dao.getFeatureAccess(role);
    }
}