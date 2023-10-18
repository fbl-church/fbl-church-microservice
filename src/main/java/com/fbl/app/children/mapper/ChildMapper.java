/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.children.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

import com.fbl.app.children.client.domain.Child;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a {@link Child} Object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class ChildMapper extends AbstractMapper<Child> {
	public static ChildMapper CHILD_MAPPER = new ChildMapper();

	public Child mapRow(ResultSet rs, int rowNum) throws SQLException {
		Child child = new Child();
		child.setId(rs.getInt(ID));
		child.setCuid(rs.getString(CUID));
		child.setFirstName(rs.getString(FIRST_NAME));
		child.setLastName(rs.getString(LAST_NAME));
		child.setBirthday(parseDate(rs.getString(BIRTHDAY)));

		child.setAdditionalInfo(rs.getString(ADDITIONAL_INFO));
		child.setReleaseOfLiability(rs.getBoolean(RELEASE_OF_LIABILITY));

		try {
			child.setAllergies(Arrays.asList(rs.getString(ALLERGIES).split(",")));
		} catch (Exception e) {
			child.setAllergies(Collections.emptyList());
		}

		child.setInsertDate(parseDateTime(rs.getString(INSERT_DATE)));
		return child;
	}
}
