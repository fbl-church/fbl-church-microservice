/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.client.domain.ChildAttendance;
import com.fbl.app.attendance.client.domain.request.ChildAttendanceGetRequest;
import com.fbl.app.attendance.dao.ChildAttendanceDAO;
import com.fbl.common.page.Page;
import com.fbl.jwt.utility.JwtHolder;

/**
 * Manage Attendance Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since August 21, 2023
 */
@Service
public class ChildAttendanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManageAttendanceService.class);

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
    public Page<ChildAttendance> getAttendanceRecordChildrenById(int id, ChildAttendanceGetRequest request) {
        AttendanceRecord record = attendanceService.getAttendanceRecordById(id);
        return dao.getAttendanceRecordChildrenById(id, request, record.getType());
    }

    /**
     * Assigns the child to the attendance record by id
     * 
     * @param recordId The attendance record id
     * @param childId  The child id attendance to update
     * @return The updated attendance record
     */
    public AttendanceRecord assignChildToAttendanceRecord(int recordId, ChildAttendance ca) {
        try {
            dao.assignChildToAttendanceRecord(recordId, ca, jwtHolder.getUserId());
        } catch (Exception e) {
            LOGGER.error("Unable to assign child id '{}' to attendance record id '{}'.", ca.getId(), recordId, e);
        }
        return attendanceService.getAttendanceRecordById(recordId);
    }

    /**
     * Updates the child notes on an attendance record
     * 
     * @param id    The attendance record id
     * @param child The child attendance to update
     * @param notes The notes to be set on the child
     * @return The updated attendance record
     */
    public AttendanceRecord updateChildNotes(int recordId, ChildAttendance ca) {
        try {
            dao.updateChildNotes(recordId, ca, jwtHolder.getUserId());
        } catch (Exception e) {
            LOGGER.error("Unable to update notes for child id '{}' on attendance record id '{}'.", ca.getId(), recordId,
                    e);
        }
        return attendanceService.getAttendanceRecordById(recordId);
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
            LOGGER.error("Unable to remove child id '{}' from attendance record id '{}'.", childId, recordId, e);
        }
        return attendanceService.getAttendanceRecordById(recordId);
    }
}