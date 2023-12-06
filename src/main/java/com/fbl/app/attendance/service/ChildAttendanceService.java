/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.client.domain.ChildAttendance;
import com.fbl.app.attendance.client.domain.request.ChildAttendanceGetRequest;
import com.fbl.app.attendance.dao.ChildAttendanceDAO;
import com.fbl.common.page.Page;
import com.fbl.exception.types.NotFoundException;
import com.fbl.jwt.utility.JwtHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * Manage Attendance Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since August 21, 2023
 */
@Slf4j
@Service
public class ChildAttendanceService {

    @Autowired
    private ChildAttendanceDAO dao;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private JwtHolder jwtHolder;

    /**
     * Gets a Page of children that are the on the attendance record by id
     * 
     * @param id The attendance record id
     * @return Page of workers
     */
    public Page<ChildAttendance> getChildrenAttendanceById(int id, ChildAttendanceGetRequest request) {
        AttendanceRecord record = attendanceService.getAttendanceRecordById(id);
        return dao.getChildrenAttendanceById(id, request, record.getType());
    }

    /**
     * Gets a page of child attendance records for a child id
     * 
     * @param childId The child id
     * @return The page of Child Attendances
     */
    public Page<ChildAttendance> getPageOfChildAttendanceByChildId(int childId, ChildAttendanceGetRequest request) {
        return dao.getPageOfChildAttendanceByChildId(childId, request);
    }

    /**
     * Gets the child attendance for the given record id and child id.
     * 
     * @param recordId The attendance record id
     * @param childId  The child id to check out
     * @return The Child Attendance
     */
    public ChildAttendance getChildAttendanceById(int recordId, int childId) {
        return dao.getChildAttendanceById(recordId, childId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Child id '%d' not found for attendance record id '%d'", childId, recordId)));
    }

    /**
     * Assigns the child to the attendance record by id
     * 
     * @param recordId The attendance record id
     * @param childId  The child id attendance to update
     * @return The updated attendance record
     */
    public ChildAttendance assignChildToAttendanceRecord(int recordId, ChildAttendance ca) {
        try {
            dao.assignChildToAttendanceRecord(recordId, ca, jwtHolder.getUserId());
        } catch (Exception e) {
            log.error("Unable to assign child id '{}' to attendance record id '{}'.", ca.getId(), recordId, e);
        }
        return getChildAttendanceById(recordId, ca.getId());
    }

    /**
     * Updates the child notes on an attendance record
     * 
     * @param id    The attendance record id
     * @param child The child attendance to update
     * @param notes The notes to be set on the child
     * @return The updated attendance record
     */
    public ChildAttendance updateChildNotes(int recordId, ChildAttendance ca) {
        try {
            dao.updateChildNotes(recordId, ca, jwtHolder.getUserId());
        } catch (Exception e) {
            log.error("Unable to update notes for child id '{}' on attendance record id '{}'.", ca.getId(), recordId,
                    e);
        }
        return getChildAttendanceById(recordId, ca.getId());
    }

    /**
     * Check out of the child on the given record id.
     * 
     * @param recordId The attendance record id
     * @param childId  The child id to check out
     * @return The updated child Attendance
     */
    public ChildAttendance checkOutChildFromAttendanceRecord(int recordId, int childId) {
        dao.checkOutChildFromAttendanceRecord(recordId, childId, jwtHolder.getUserId());
        return getChildAttendanceById(recordId, childId);
    }

    /**
     * Remove the child from the attendance record by id
     * 
     * @param recordId The attendance record id
     * @param childId  The child id attendance to update
     * @return The updated attendance record
     */
    public AttendanceRecord removeChildFromAttendanceRecord(int recordId, int childId) {
        try {
            dao.removeChildFromAttendanceRecord(recordId, childId);
        } catch (Exception e) {
            log.error("Unable to remove child id '{}' from attendance record id '{}'.", childId, recordId, e);
        }
        return attendanceService.getAttendanceRecordById(recordId);
    }
}
