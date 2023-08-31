/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.accessmanager.client.domain.Application;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a Application Object {@link Application}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class ApplicationMapper extends AbstractMapper<Application> {
	public static ApplicationMapper APPLICATION_MAPPER = new ApplicationMapper();

	public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
		Application app = new Application();
		app.setId(rs.getInt(ID));
		app.setName(rs.getString(NAME));
		app.setEnabled(rs.getBoolean(ENABLED));
		return app;
	}
}