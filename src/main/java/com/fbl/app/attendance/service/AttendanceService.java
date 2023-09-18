/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.client.domain.request.AttendanceRecordGetRequest;
import com.fbl.app.attendance.dao.AttendanceDAO;
import com.fbl.app.user.client.domain.User;
import com.fbl.common.page.Page;
import com.google.common.collect.Sets;

/**
 * Attendance Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since August 21, 2023
 */
@Service

public class AttendanceService {

    @Autowired
    private AttendanceDAO attendanceDAO;

    /**
     * Get attendance records based on given request filter
     * 
     * @param request of the attendance record
     * @return Page of {@link AttendanceRecord}
     */
    public Page<AttendanceRecord> getAttendanceRecords(AttendanceRecordGetRequest request) {
        return attendanceDAO.getAttendanceRecords(request);
    }

    /**
     * Gets the Attendance Record based on the given id
     * 
     * @param id The attendance record id
     * @return The Attendance Record that matches that id
     */
    public AttendanceRecord getAttendanceRecordById(@PathVariable int id) {
        AttendanceRecordGetRequest request = new AttendanceRecordGetRequest();
        request.setId(Sets.newHashSet(id));
        AttendanceRecord record = getAttendanceRecords(request).getList().get(0);
        record.setWorkers(getAttendanceRecordWorkersById(id));
        return record;
    }

    /**
     * Gets a list of workers that are the on the attendance record by id
     * 
     * @param id The attendance record id
     * @return List of workers
     */
    public List<User> getAttendanceRecordWorkersById(int id) {
        return attendanceDAO.getAttendanceRecordWorkersById(id);
    }
}
