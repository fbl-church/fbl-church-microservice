/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.client.domain.AttendanceRecordEntity;
import com.fbl.app.attendance.client.domain.AttendanceRecordJPARepository;
import com.fbl.app.attendance.client.domain.request.AttendanceRecordGetRequest;
import com.fbl.app.attendance.dao.AttendanceDAO;
import com.fbl.app.user.client.domain.User;
import com.fbl.common.page.Page;
import com.fbl.common.page.domain.PageSort;
import com.fbl.exception.types.NotFoundException;

/**
 * Attendance Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since August 21, 2023
 */
@Service
public class AttendanceService {

    @Autowired
    private AttendanceDAO dao;

    @Autowired
    private AttendanceRecordJPARepository attendanceRecordJPARepository;

    public AttendanceRecordEntity getEntityById(int id) {
        return attendanceRecordJPARepository.findById(id).orElseThrow(() -> new NotFoundException("Attendance", id));
    }

    /**
     * Get attendance records based on given request filter
     * 
     * @param request of the attendance record
     * @return Page of {@link AttendanceRecord}
     */
    public Page<AttendanceRecord> getAttendanceRecords(AttendanceRecordGetRequest request, PageSort sort) {
        return dao.getAttendanceRecords(request, sort);
    }

    /**
     * Gets the Attendance Record based on the given id
     * 
     * @param id The attendance record id
     * @return The Attendance Record that matches that id
     */
    public AttendanceRecord getAttendanceRecordById(int id) {
        AttendanceRecord r = dao.getAttendanceRecordById(id).orElseThrow(() -> new NotFoundException("Attendance", id));
        r.setWorkers(getAttendanceRecordWorkersById(id));
        return r;
    }

    /**
     * Gets a list of workers that are the on the attendance record by id
     * 
     * @param id The attendance record id
     * @return List of workers
     */
    public List<User> getAttendanceRecordWorkersById(int id) {
        return dao.getAttendanceRecordWorkersById(id);
    }
}
