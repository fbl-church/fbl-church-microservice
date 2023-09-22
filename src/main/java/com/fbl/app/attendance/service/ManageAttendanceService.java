/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.dao.AttendanceDAO;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.common.enums.AttendanceStatus;
import com.fbl.exception.types.BaseException;
import com.fbl.jwt.utility.JwtHolder;

/**
 * Manage Attendance Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since August 21, 2023
 */
@Service
public class ManageAttendanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManageAttendanceService.class);

    @Autowired
    private AttendanceDAO dao;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtHolder jwtHolder;

    /**
     * Create a new attendance record
     * 
     * @param record The attendance record to create
     * @return The record that was created
     */
    public AttendanceRecord createAttendanceRecord(AttendanceRecord record) {
        int recordId = dao.createAttendanceRecord(record);
        assignWorkersToAttendanceRecord(recordId, record.getWorkers());
        return attendanceService.getAttendanceRecordById(recordId);
    }

    /**
     * Update an attendance record
     * 
     * @param record The attendance record to update
     * @return The record that was updated
     */
    public AttendanceRecord updateAttendanceRecord(int recordId, AttendanceRecord record) {
        attendanceService.getAttendanceRecordById(recordId);
        dao.updateAttendanceRecord(recordId, record);
        assignWorkersToAttendanceRecord(recordId, record.getWorkers());
        return attendanceService.getAttendanceRecordById(recordId);
    }

    /**
     * Update an attendance record
     * 
     * @param id     The attendance record id
     * @param status The status to update with
     * @return The record that was updated
     */
    public AttendanceRecord updateAttendanceRecordStatus(int id, AttendanceStatus status) {
        AttendanceRecord currentRecord = attendanceService.getAttendanceRecordById(id);
        if (AttendanceStatus.CLOSED.equals(currentRecord.getStatus())) {
            throw new BaseException("Cannot update status of record that is already closed.");
        }

        if (AttendanceStatus.CLOSED.equals(status)) {
            dao.closeAttendanceRecord(id, jwtHolder.getUserId());
        } else if (AttendanceStatus.ACTIVE.equals(status)) {
            dao.activateAttendanceRecord(id, jwtHolder.getUserId());
        } else {
            dao.updateAttendanceRecordStatus(id, status);
        }

        return attendanceService.getAttendanceRecordById(id);
    }

    /**
     * Assigns the given worker id to the attendance record
     * 
     * @param recordId The attendance record id
     * @param workers  The list of workers to assign to the record.
     * @return the list of users that were assigned
     */
    public List<User> assignWorkersToAttendanceRecord(int recordId, List<User> workers) {
        UserGetRequest request = new UserGetRequest();

        List<User> filteredWorkers = Collections.emptyList();
        if (!CollectionUtils.isEmpty(workers)) {
            request.setId(workers.stream().map(User::getId).collect(Collectors.toSet()));
            filteredWorkers = userClient.getUsers(request);
        }

        if (!filteredWorkers.isEmpty()) {
            dao.deleteAttendanceRecordWorkers(recordId);
        }

        for (User u : filteredWorkers) {
            try {
                dao.assignWorkerToAttendanceRecord(recordId, u.getId());
            } catch (Exception e) {
                LOGGER.error("Unable to assign user id '{}' to attendance record.", u.getId(), e);
            }
        }

        return attendanceService.getAttendanceRecordWorkersById(recordId);
    }

    /**
     * Delete an attendance record by id.
     * 
     * @param id The attendance record id
     */
    public void deleteAttendanceRecordById(int id) {
        dao.deleteAttendanceRecordById(id);
    }
}
