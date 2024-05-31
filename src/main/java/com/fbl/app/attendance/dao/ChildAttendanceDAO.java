/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.dao;

import static com.fbl.app.attendance.mapper.ChildAttendanceMapper.*;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fbl.app.attendance.client.domain.ChildAttendance;
import com.fbl.app.attendance.client.domain.request.ChildAttendanceGetRequest;
import com.fbl.common.date.TimeZoneUtil;
import com.fbl.common.enums.ChurchGroup;
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
public class ChildAttendanceDAO extends BaseDao {

    ChildAttendanceDAO(DataSource source) {
        super(source);
    }

    /**
     * Gets a Page of children that are the on the attendance record by id
     * 
     * @param id The attendance record id
     * @return Page of workers
     */
    public Page<ChildAttendance> getChildrenAttendanceById(int id, ChildAttendanceGetRequest request,
            ChurchGroup group) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams()
                .withParam(ATTENDANCE_RECORD_ID, id).withParam(PRESENT, request.getPresent())
                .withParam(CHURCH_GROUP, group).build();
        return getPage("getChildrenAttendancePage", params, CHILD_ATTENDANCE_MAPPER);
    }

    /**
     * Gets a page of child attendance records for a child id
     * 
     * @param childId The child id
     * @return The page of Child Attendances
     */
    public Page<ChildAttendance> getPageOfChildAttendanceByChildId(int childId, ChildAttendanceGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams().withParam(CHILD_ID, childId)
                .withParamTextEnumCollection(CHURCH_GROUP, request.getGroup())
                .build();
        return getPage("getChildAttendancePageByChildId", params, CHILD_ATTENDANCE_MAPPER);
    }

    /**
     * Gets the child attendance for the given record id and child id.
     * 
     * @param recordId The attendance record id
     * @param childId  The child id to check out
     * @return The Child Attendance
     */
    public Optional<ChildAttendance> getChildAttendanceById(int recordId, int childId) {
        MapSqlParameterSource params = SqlParamBuilder.with().useAllParams()
                .withParam(ATTENDANCE_RECORD_ID, recordId).withParam(CHILD_ID, childId)
                .build();
        return getOptional("getChildAttendanceById", params, CHILD_ATTENDANCE_MAPPER);
    }

    /**
     * Assigns the given child id to the attendance record
     * 
     * @param recordId The attendance record id
     * @param childId  The child id to assign to it
     */
    public void assignChildToAttendanceRecord(int recordId, ChildAttendance ca, int updatedUserId) {
        update("assignChildToAttendanceRecord", buildChildAttendanceParams(recordId, ca, updatedUserId));
    }

    /**
     * Updates the child notes on an attendance record
     * 
     * @param id    The attendance record id
     * @param child The child attendance to update
     * @param notes The notes to be set on the child
     * @return The updated attendance record
     */
    public void updateChild(int recordId, ChildAttendance ca, int updatedUserId) {
        update("updateChild", buildChildAttendanceParams(recordId, ca, updatedUserId));
    }

    /**
     * Check out of the child on the given record id.
     * 
     * @param recordId The attendance record id
     * @param childId  The child id to check out
     * @return The updated child Attendance
     */
    public void checkOutChildFromAttendanceRecord(int recordId, int childId, Integer guardianId, int updatedUserId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(ATTENDANCE_RECORD_ID, recordId)
                .withParam(CHILD_ID, childId).withParam(GUARDIAN_ID, guardianId)
                .withParam(UPDATE_USER_ID, updatedUserId)
                .withParam(CHECK_OUT_DATE, LocalDateTime.now(TimeZoneUtil.SYSTEM_ZONE)).build();
        update("checkOutChildFromAttendanceRecord", params);
    }

    /**
     * Remove the child from the attendance record by id
     * 
     * @param recordId The attendance record id
     * @param childId  The child id attendance to update
     * @return The updated attendance record
     */
    public void removeChildFromAttendanceRecord(int recordId, int childId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(ATTENDANCE_RECORD_ID, recordId)
                .withParam(CHILD_ID, childId).build();
        delete("removeChildFromAttendanceRecord", params);
    }

    /**
     * Helper method to build the params for a child attendance
     * 
     * @param recordId      The record id
     * @param ca            The child attendance object
     * @param updatedUserId The updating user
     * @return The built sql parameter source
     */
    private MapSqlParameterSource buildChildAttendanceParams(int recordId, ChildAttendance ca, int updatedUserId) {
        return SqlParamBuilder.with().withParam(ATTENDANCE_RECORD_ID, recordId)
                .withParam(CHILD_ID, ca.getId()).withParam(NOTES, ca.getNotes())
                .withParam(GUARDIAN_PICKED_UP_ID, ca.getGuardianPickedUpId())
                .withParam(UPDATE_USER_ID, updatedUserId)
                .build();
    }
}
