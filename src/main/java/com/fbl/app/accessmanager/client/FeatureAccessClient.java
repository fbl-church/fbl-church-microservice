/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fbl.app.accessmanager.service.FeatureAccessService;
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
	 * Client method to get a map of feature access for the provided web roles.
	 * 
	 * @param roles The web roles to get the feature access for
	 * @return {@link Map<String,String>} of the feature access
	 */
	public Map<String, List<Map<String, String>>> getWebRoleFeatureAccess(List<WebRole> roles) {
		return service.getWebRoleFeatureAccess(roles);
	}
}
