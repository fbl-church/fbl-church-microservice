/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.featureaccess.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fbl.app.featureaccess.service.FeatureAccessService;
import com.fbl.common.annotations.interfaces.Client;
import com.fbl.common.enums.WebRole;

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
	public Map<String, List<Map<String, String>>> getFeatureAccess(List<WebRole> role) {
		return service.getFeatureAccess(role);
	}
}
