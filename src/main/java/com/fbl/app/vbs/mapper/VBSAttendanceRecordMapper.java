/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.vbs.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fbl.app.vbs.client.domain.VBSAttendanceRecord;
import com.fbl.common.enums.AttendanceStatus;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a VBS Attendance Record Object
 * {@link VBSAttendanceRecord}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class VBSAttendanceRecordMapper extends AbstractMapper<VBSAttendanceRecord> {
    public static VBSAttendanceRecordMapper VBS_ATTENDANCE_RECORD_MAPPER = new VBSAttendanceRecordMapper();

    public VBSAttendanceRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        VBSAttendanceRecord record = new VBSAttendanceRecord();
        record.setId(rs.getInt(ID));
        record.setName(rs.getString(NAME));
        record.setStatus(AttendanceStatus.valueOf(rs.getString(STATUS)));
        record.setType(ChurchGroup.valueOf(rs.getString(TYPE)));
        record.setUnitSession(rs.getString(UNIT_SESSION));
        record.setActiveDate(parseDate(rs.getString(ACTIVE_DATE)));
        record.setStartedByUserId(rs.getObject(STARTED_BY_USER_ID, Integer.class));
        record.setClosedByUserId(rs.getObject(CLOSED_BY_USER_ID, Integer.class));
        record.setClosedDate(parseDateTime(rs.getString(CLOSED_DATE)));
        record.setInsertDate(parseDateTime(rs.getString(INSERT_DATE)));

        record.setMoney(rs.getFloat(MONEY));
        record.setSpiritTheme(rs.getString(SPIRIT_THEME));
        record.setVbsThemeId(rs.getInt(VBS_THEME_ID));

        try {
            List<String> groups = Arrays.asList(rs.getString(OFFERING_WINNERS).split(","));
            record.setOfferingWinners(groups.stream().map(ChurchGroup::valueOf).toList());
        } catch (Exception e) {
            record.setOfferingWinners(Collections.emptyList());
        }

        return record;
    }
}
