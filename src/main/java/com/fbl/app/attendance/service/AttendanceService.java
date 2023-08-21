/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.client.domain.request.AttendanceRecordGetRequest;
import com.fbl.app.attendance.dao.AttendanceDAO;
import com.fbl.common.page.Page;

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
}
