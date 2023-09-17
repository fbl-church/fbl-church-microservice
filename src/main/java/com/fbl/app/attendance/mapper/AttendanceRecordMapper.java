/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.common.enums.AttendanceStatus;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a Attendance Record Object {@link AttendanceRecord}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class AttendanceRecordMapper extends AbstractMapper<AttendanceRecord> {
    public static AttendanceRecordMapper ATTENDANCE_RECORD_MAPPER = new AttendanceRecordMapper();

    public AttendanceRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        AttendanceRecord record = new AttendanceRecord();
        record.setId(rs.getInt(ID));
        record.setName(rs.getString(NAME));
        record.setStatus(AttendanceStatus.valueOf(rs.getString(STATUS)));
        record.setType(ChurchGroup.valueOf(rs.getString(TYPE)));
        record.setActiveDate(parseDate(rs.getString(ACTIVE_DATE)));
        record.setClosedDate(parseDateTime(rs.getString(CLOSED_DATE)));
        record.setInsertDate(parseDateTime(rs.getString(INSERT_DATE)));
        return record;
    }
}
