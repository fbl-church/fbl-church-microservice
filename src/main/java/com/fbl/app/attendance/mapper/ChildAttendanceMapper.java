/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.attendance.client.domain.ChildAttendance;
import com.fbl.app.children.client.domain.Child;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a {@link Child} Object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class ChildAttendanceMapper extends AbstractMapper<ChildAttendance> {
	public static ChildAttendanceMapper CHILD_ATTENDANCE_MAPPER = new ChildAttendanceMapper();

	public ChildAttendance mapRow(ResultSet rs, int rowNum) throws SQLException {
		ChildAttendance child = new ChildAttendance();
		child.setId(rs.getInt(ID));
		child.setCuid(rs.getString(CUID));
		child.setFirstName(rs.getString(FIRST_NAME));
		child.setLastName(rs.getString(LAST_NAME));
		child.setAttendanceRecordId(rs.getInt(ATTENDANCE_RECORD_ID));
		child.setPresent(rs.getBoolean(PRESENT));
		child.setNotes(rs.getString(NOTES));
		child.setUpdatedUserId(rs.getInt(UPDATE_USER_ID));
		child.setCheckInDate(parseDateTime(rs.getString(CHECK_IN_DATE)));
		return child;
	}
}
