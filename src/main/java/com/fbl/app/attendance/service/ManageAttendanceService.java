/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.dao.AttendanceDAO;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;

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
    private AttendanceDAO attendanceDAO;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private UserClient userClient;

    /**
     * Create a new attendance record
     * 
     * @param record The attendance record to create
     * @return The record that was created
     */
    public AttendanceRecord createAttendanceRecord(AttendanceRecord record) {
        int recordId = attendanceDAO.createAttendanceRecord(record);
        assignWorkersToAttendanceRecord(recordId, record.getWorkers());
        return attendanceService.getAttendanceRecordById(recordId);
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
        request.setId(workers.stream().map(User::getId).collect(Collectors.toSet()));
        List<User> filteredWorkers = userClient.getUsers(request);

        if (!filteredWorkers.isEmpty()) {
            attendanceDAO.deleteAttendanceRecordWorkers(recordId);
        }

        for (User u : filteredWorkers) {
            try {
                attendanceDAO.assignWorkerToAttendanceRecord(recordId, u.getId());
            } catch (Exception e) {
                LOGGER.error("Unable to assign user id '{}' to attendance record.", u.getId(), e);
            }
        }

        return attendanceService.getAttendanceRecordWorkersById(recordId);
    }
}
