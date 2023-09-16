/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.accessmanager.client.domain.WebRoleFeature;
import com.fbl.common.enums.WebRole;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a Web Role Feature Object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class WebRoleFeatureMapper extends AbstractMapper<WebRoleFeature> {
	public static WebRoleFeatureMapper WEB_ROLE_FEATURE_MAPPER = new WebRoleFeatureMapper();

	public WebRoleFeature mapRow(ResultSet rs, int rowNum) throws SQLException {
		WebRoleFeature webRoleFeature = new WebRoleFeature();
		webRoleFeature.setWebRole(WebRole.valueOf(rs.getString(WEB_ROLE)));
		webRoleFeature.setFeatureId(rs.getInt(ID));
		webRoleFeature.setApp(rs.getString(APPLICATION_TEXT));
		webRoleFeature.setFeature(rs.getString(NAME));
		webRoleFeature.setCreate(rs.getBoolean(CREATE));
		webRoleFeature.setRead(rs.getBoolean(READ));
		webRoleFeature.setUpdate(rs.getBoolean(UPDATE));
		webRoleFeature.setDelete(rs.getBoolean(DELETE));
		return webRoleFeature;
	}
}
