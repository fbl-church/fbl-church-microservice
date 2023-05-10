/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.featureaccess.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.awana.app.featureaccess.service.FeatureAccessService;
import com.awana.common.annotations.interfaces.Client;
import com.awana.common.enums.WebRole;

/**
 * This class exposes the feature access endpoint's to other app's to pull data
 * across the platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class FeatureAccessClient {

	@Autowired
	private FeatureAccessService service;

	/**
	 * Client method to get list of user feature access
	 * 
	 * @param request to filter stores on
	 * @return List of stores {@link Map<String,String>}
	 */
	public Map<String, List<Map<String, String>>> getFeatureAccess(WebRole role) {
		return service.getFeatureAccess(role);
	}
}
