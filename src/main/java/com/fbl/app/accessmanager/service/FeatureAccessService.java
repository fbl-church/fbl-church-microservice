/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.accessmanager.dao.FeatureAccessDAO;
import com.fbl.app.user.client.UserClient;
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

    @Autowired
    private UserClient userClient;

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
}