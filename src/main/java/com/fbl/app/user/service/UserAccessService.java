package com.fbl.app.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.accessmanager.client.FeatureClient;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.UserAccess;

/**
 * Feature Access Service
 *
 * @author Sam Butler
 * @since 8/2/2020
 */
@Service
public class UserAccessService {

    @Autowired
    private UserService userService;

    @Autowired
    private FeatureClient featureClient;

    /**
     * Gets the current user access. This includes the feature access, application
     * access, and roles.
     *
     * @param authenticationRequest A email and password request.
     * @return a new JWT token.
     */
    public UserAccess getCurrentUserAccess() {
        User currentUser = userService.getCurrentUser();
        List<String> userApps = userService.getUserAppsById(currentUser.getId());
        Map<String, List<Map<String, String>>> userFeatures = featureClient.getUserFeatureAccess(currentUser.getId());
        return new UserAccess(currentUser, userFeatures, userApps);
    }
}
