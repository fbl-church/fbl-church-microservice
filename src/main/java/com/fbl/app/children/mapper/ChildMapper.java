/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.children.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.children.client.domain.Child;
import com.fbl.common.enums.ChurchGroup;
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
		child.setFirstName(rs.getString(FIRST_NAME));
		child.setLastName(rs.getString(LAST_NAME));
		child.setChurchGroup(ChurchGroup.valueOf(rs.getString(CHURCH_GROUP)));
		child.setBirthday(parseDate(rs.getString(BIRTHDAY)));
		child.setAllergies(rs.getString(ALLERGIES));
		child.setAdditionalInfo(rs.getString(ADDITIONAL_INFO));

		child.setInsertDate(rs.getTimestamp(INSERT_DATE).toLocalDateTime());
		return child;
	}
}