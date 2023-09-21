/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.guardian.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.guardian.client.domain.Guardian;
import com.fbl.common.enums.RelationshipType;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a Guardian Object {@link Guardian}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class GuardianMapper extends AbstractMapper<Guardian> {
	public static GuardianMapper GUARDIAN_MAPPER = new GuardianMapper();

	public Guardian mapRow(ResultSet rs, int rowNum) throws SQLException {
		Guardian guardian = new Guardian();

		guardian.setId(rs.getInt(ID));
		guardian.setFirstName(rs.getString(FIRST_NAME));
		guardian.setLastName(rs.getString(LAST_NAME));
		try {
			guardian.setRelationship(RelationshipType.valueOf(rs.getString(RELATIONSHIP)));
		} catch (Exception e) {
			guardian.setRelationship(null);
		}

		guardian.setEmail(rs.getString(EMAIL));
		guardian.setPhone(rs.getString(PHONE));
		guardian.setAddress(rs.getString(ADDRESS));
		guardian.setCity(rs.getString(CITY));
		guardian.setState(rs.getString(STATE));
		guardian.setZipCode(rs.getString(ZIP_CODE));
		guardian.setInsertDate(parseDateTime(rs.getString(INSERT_DATE)));

		return guardian;
	}
}
