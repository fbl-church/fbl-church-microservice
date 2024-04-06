package com.fbl.app.attendance.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.attendance.client.domain.request.AttendanceRecordGetRequest;
import com.fbl.app.attendance.openapi.TagAttendance;
import com.fbl.app.attendance.service.AttendanceScheduleService;
import com.fbl.common.annotations.interfaces.RestApiController;

/**
 * Attendance Schedule Controller
 * 
 * @author Sam Butler
 * @since Apr 05, 2024
 */
@RequestMapping("/api/attendance-records/schedule")
@RestApiController
@TagAttendance
public class AttendanceScheduleController {

    @Autowired
    private AttendanceScheduleService service;

    /**
     * Gets a list of attendance records based of the request filter
     * 
     * @param request to filter on
     * @return list of attendance record objects
     */
    @GetMapping
    public byte[] downloadSchedule(AttendanceRecordGetRequest request) throws Exception {
        return service.downloadSchedule(request);
    }
}
