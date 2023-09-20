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
public class FeatureMapper extends AbstractMapper<Feature> {
	public static FeatureMapper FEATURE_MAPPER = new FeatureMapper();

	public Feature mapRow(ResultSet rs, int rowNum) throws SQLException {
		Feature feature = new Feature();
		feature.setId(rs.getInt(ID));
		feature.setApp(rs.getString(APP_KEY));
		feature.setFeature(rs.getString(FEATURE_KEY));
		feature.setEnabled(rs.getBoolean(ENABLED));
		feature.setInsertDate(parseDateTime(rs.getString(INSERT_DATE)));
		return feature;
	}
}
