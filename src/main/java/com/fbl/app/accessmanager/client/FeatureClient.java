/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fbl.app.accessmanager.service.FeatureService;
import com.fbl.common.annotations.interfaces.Client;

/**
 * This class exposes the feature access endpoint's to other app's to pull data
 * across the platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class FeatureClient {

	@Autowired
	private FeatureService service;

	/**
	 * Client method to get a map of feature access for the provided user id.
	 * 
	 * @param userId The user id of the user.
	 * @return {@link Map<String,String>} of the feature access
	 */
	public Map<String, List<Map<String, String>>> getUserFeatureAccess(int userId) {
		return service.getWebRoleFeatureAccess(userId);
	}
}
