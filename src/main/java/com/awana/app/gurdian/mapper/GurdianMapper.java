/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.gurdian.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.awana.app.gurdian.client.domain.Gurdian;
import com.awana.common.enums.RelationshipType;
import com.awana.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a Gurdian Object {@link Gurdian}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class GurdianMapper extends AbstractMapper<Gurdian> {
	public static GurdianMapper GURDIAN_MAPPER = new GurdianMapper();

	public Gurdian mapRow(ResultSet rs, int rowNum) throws SQLException {
		Gurdian gurdian = new Gurdian();

		gurdian.setId(rs.getInt(ID));
		gurdian.setFirstName(rs.getString(FIRST_NAME));
		gurdian.setLastName(rs.getString(LAST_NAME));
		gurdian.setRelationship(RelationshipType.valueOf(rs.getString(RELATIONSHIP)));
		gurdian.setEmail(rs.getString(EMAIL));
		gurdian.setPhone(rs.getString(PHONE));
		gurdian.setAddress(rs.getString(ADDRESS));
		gurdian.setInsertDate(parseDateTime(rs.getString(INSERT_DATE)));

		return gurdian;
	}
}
