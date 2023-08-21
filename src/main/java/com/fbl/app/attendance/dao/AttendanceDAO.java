/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.dao;

import static com.fbl.app.attendance.mapper.AttendanceRecordMapper.*;
import static com.fbl.app.user.mapper.UserMapper.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.client.domain.request.AttendanceRecordGetRequest;
import com.fbl.app.user.client.domain.User;
import com.fbl.common.page.Page;
import com.fbl.sql.abstracts.BaseDao;
import com.fbl.sql.builder.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class AttendanceDAO extends BaseDao {

    AttendanceDAO(DataSource source) {
        super(source);
    }

    /**
     * Get attendance records based on given request filter
     * 
     * @param request of the attendance record
     * @return Page of {@link AttendanceRecord}
     */
    public Page<AttendanceRecord> getAttendanceRecords(AttendanceRecordGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams()
                .withParam(ATTENDANCE_ID, request.getId())
                .withParam(NAME, request.getName()).withParamTextEnumCollection(TYPE, request.getType())
                .withParamTextEnumCollection(STATUS, request.getStatus())
                .build();

        return getPage("getAttendanceRecordsPage", params, ATTENDANCE_RECORD_MAPPER);
    }

    /**
     * Gets a list of workers that are the on the attendance record by id
     * 
     * @param id The attendance record id
     * @return List of workers
     */
    public List<User> getAttendanceRecordWorkersById(int id) {
        MapSqlParameterSource params = parameterSource(ATTENDANCE_ID, id);
        return getList("getAttendanceRecordWorkersPage", params, USER_MAPPER);
    }
}
