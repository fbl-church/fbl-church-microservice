/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.accessmanager.client.domain.Feature;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a Feature Object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class FeatureAccessMapper extends AbstractMapper<Feature> {
	public static FeatureAccessMapper FEATURE_ACCESS_MAPPER = new FeatureAccessMapper();

	public Feature mapRow(ResultSet rs, int rowNum) throws SQLException {
		Feature feature = new Feature();
		feature.setApp(rs.getString(FEATURE_APPLICATION_TEXT));
		feature.setFeature(rs.getString(FEATURE_NAME_TEXT));
		feature.setAccess(rs.getString(ACCESS));
		return feature;
	}
}
