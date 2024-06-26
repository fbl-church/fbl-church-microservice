/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.dao;

import static com.fbl.app.attendance.mapper.AttendanceRecordMapper.*;
import static com.fbl.app.user.mapper.UserMapper.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.client.domain.request.AttendanceRecordGetRequest;
import com.fbl.app.user.client.domain.User;
import com.fbl.common.date.TimeZoneUtil;
import com.fbl.common.enums.AttendanceStatus;
import com.fbl.common.page.Page;
import com.fbl.common.page.domain.PageSort;
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
    public Page<AttendanceRecord> getAttendanceRecords(AttendanceRecordGetRequest request, PageSort sort) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams()
                .withParam(ID, request.getId())
                .withParam(NAME, request.getName()).withParamTextEnumCollection(TYPE, request.getType())
                .withParamTextEnumCollection(STATUS, request.getStatus())
                .withParam(SORT, sort != null ? sort : PageSort.DESC)
                .withParam(START_DATE, request.getStartDate()).withParam(END_DATE, request.getEndDate())
                .build();

        return getPage("getAttendanceRecordsPage", params, ATTENDANCE_RECORD_MAPPER);
    }

    /**
     * Gets the Attendance Record based on the given id
     * 
     * @param id The attendance record id
     * @return The Optional Attendance Record
     */
    public Optional<AttendanceRecord> getAttendanceRecordById(int id) {
        return getOptional("getAttendanceRecordById", parameterSource(ID, id), ATTENDANCE_RECORD_MAPPER);
    }

    /**
     * Gets a list of workers that are the on the attendance record by id
     * 
     * @param id The attendance record id
     * @return List of workers
     */
    public List<User> getAttendanceRecordWorkersById(int id) {
        MapSqlParameterSource params = parameterSource(ATTENDANCE_RECORD_ID, id);
        return getList("getAttendanceRecordWorkersById", params, USER_MAPPER);
    }

    /**
     * Create a new attendance record
     * 
     * @param record The attendance record to create
     * @return The record that was created
     */
    public int createAttendanceRecord(AttendanceRecord record) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(NAME, record.getName())
                .withParam(TYPE, record.getType())
                .withParam(UNIT_SESSION, StringUtils.hasText(record.getUnitSession()) ? record.getUnitSession() : null)
                .withParam(ACTIVE_DATE, record.getActiveDate()).build();

        post("insertAttendanceRecord", params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Update an attendance record
     * 
     * @param record The attendance record to update
     * @return The record that was updated
     */
    public void updateAttendanceRecord(int recordId, AttendanceRecord record) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(NAME, record.getName())
                .withParam(UNIT_SESSION, StringUtils.hasText(record.getUnitSession()) ? record.getUnitSession() : null)
                .withParam(ACTIVE_DATE, record.getActiveDate()).withParam(ID, recordId).build();

        update("updateAttendanceRecord", params);
    }

    /**
     * Update an attendance record
     * 
     * @param id     The attendance record id
     * @param status The status to update with
     */
    public void updateAttendanceRecordStatus(int id, AttendanceStatus status) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(STATUS, status).withParam(ID, id).build();
        update("updateAttendanceRecordStatus", params);
    }

    /**
     * Closes the attendance record and sets the closed date time.
     * 
     * @param id             The attendance record id
     * @param closedByUserId The id of who closed the record
     */
    public void closeAttendanceRecord(int id, int closedByUserId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(STATUS, AttendanceStatus.CLOSED)
                .withParam(CLOSED_DATE, LocalDateTime.now(TimeZoneUtil.SYSTEM_ZONE))
                .withParam(ID, id).withParam(CLOSED_BY_USER_ID, closedByUserId).build();
        update("updateAttendanceRecordStatus", params.addValue(STARTED_BY_USER_ID, null));
    }

    /**
     * Will reopen a closed attendance record
     * 
     * @param id The attendance record to reopen
     */
    public void reopenAttendanceRecord(int id) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(ID, id).build();
        update("reopenAttendanceRecord", params);
    }

    /**
     * Will check out all the children on the attendance record if they are not
     * already checked out.
     * 
     * @param id The attendance record id
     */
    public void checkoutChildrenFromRecord(int id) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(ID, id)
                .withParam(CHECK_OUT_DATE, LocalDateTime.now(TimeZoneUtil.SYSTEM_ZONE)).build();
        update("checkoutChildrenFromRecord", params);
    }

    /**
     * Activates the attendance record,
     * 
     * @param id              The attendance record id
     * @param startedByUserId The id of who activated the record
     */
    public void activateAttendanceRecord(int id, int startedByUserId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(STATUS, AttendanceStatus.ACTIVE)
                .withParam(ID, id).withParam(STARTED_BY_USER_ID, startedByUserId).build();
        update("updateAttendanceRecordStatus", params.addValue(CLOSED_BY_USER_ID, null).addValue(CLOSED_DATE, null));
    }

    /**
     * Assigns the given worker id to the attendance record
     * 
     * @param recordId The attendance record id
     * @param workerId The user id to assign to it
     */
    public void assignWorkerToAttendanceRecord(int recordId, int workerId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(ATTENDANCE_RECORD_ID, recordId)
                .withParam(USER_ID, workerId).build();

        update("assignWorkerToAttendanceRecord", params);
    }

    /**
     * Delete all workers from attendance record
     * 
     * @param recordId The attendance record id
     */
    public void deleteAttendanceRecordWorkers(int recordId) {
        delete("deleteAttendanceRecordWorkers", parameterSource(ATTENDANCE_RECORD_ID, recordId));
    }

    /**
     * Delete an attendance record by id.
     * 
     * @param id The attendance record id
     */
    public void deleteAttendanceRecordById(int id) {
        delete("deleteAttendanceRecordById", parameterSource(ID, id));
    }
}
