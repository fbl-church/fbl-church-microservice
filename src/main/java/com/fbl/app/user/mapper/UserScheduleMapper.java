/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.user.client.domain.UserSchedule;
import com.fbl.common.enums.AttendanceStatus;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a User Schedule Object {@link UserSchedule}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class UserScheduleMapper extends AbstractMapper<UserSchedule> {
    public static UserScheduleMapper USER_SCHEDULE_MAPPER = new UserScheduleMapper();

    public UserSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserSchedule userSchedule = new UserSchedule();
        userSchedule.setRecordId(rs.getInt(ID));
        userSchedule.setUserId(rs.getInt(USER_ID));
        userSchedule.setStatus(AttendanceStatus.valueOf(rs.getString(STATUS)));
        userSchedule.setType(ChurchGroup.valueOf(rs.getString(TYPE)));
        userSchedule.setRecordName(rs.getString(NAME));
        userSchedule.setActiveDate(parseDate(rs.getString(ACTIVE_DATE)));
        return userSchedule;
    }
}
