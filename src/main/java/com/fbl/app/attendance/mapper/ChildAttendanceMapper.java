/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.attendance.client.domain.ChildAttendance;
import com.fbl.app.children.client.domain.Child;
import com.fbl.common.enums.AttendanceStatus;
import com.fbl.common.enums.ChurchGroup;
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
		child.setNotes(rs.getString(NOTES));
		child.setAttendanceRecordId(rs.getObject(ATTENDANCE_RECORD_ID, Integer.class));
		child.setUpdatedUserId(rs.getObject(UPDATED_USER_ID, Integer.class));
		child.setGuardianPickedUpId(rs.getObject(GUARDIAN_PICKED_UP_ID, Integer.class));
		child.setCheckInDate(parseDateTime(rs.getString(CHECK_IN_DATE)));
		child.setCheckOutDate(parseDateTime(rs.getString(CHECK_OUT_DATE)));
		child.setRecordName(rs.getString(NAME));

		if (rs.getString(STATUS) != null) {
			child.setStatus(AttendanceStatus.valueOf(rs.getString(STATUS)));
		}

		child.setRecordType(rs.getString(TYPE) == null ? null : ChurchGroup.valueOf(rs.getString(TYPE)));
		child.setRecordDate(parseDate(rs.getString(ACTIVE_DATE)));
		return child;
	}
}
