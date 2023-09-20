/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.accessmanager.client.domain.WebRoleApp;
import com.fbl.common.enums.WebRole;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a Web Role App Object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class WebRoleAppMapper extends AbstractMapper<WebRoleApp> {
	public static WebRoleAppMapper WEB_ROLE_APP_MAPPER = new WebRoleAppMapper();

	public WebRoleApp mapRow(ResultSet rs, int rowNum) throws SQLException {
		WebRoleApp webRoleFeature = new WebRoleApp();
		webRoleFeature.setWebRole(WebRole.valueOf(rs.getString(WEB_ROLE)));
		webRoleFeature.setAppId(rs.getInt(APP_ID));
		webRoleFeature.setAccess(rs.getBoolean(ACCESS));
		return webRoleFeature;
	}
}
