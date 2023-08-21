/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.rest;

import static org.springframework.http.MediaType.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.client.domain.request.AttendanceRecordGetRequest;
import com.fbl.app.attendance.openapi.TagAttendance;
import com.fbl.app.attendance.service.AttendanceService;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.page.Page;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Attendance Controller
 *
 * @author Sam Butler
 * @since 8/3/2020
 */
@RequestMapping("/api/attendance")
@RestApiController
@TagAttendance
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    /**
     * Gets a list of users based of the request filter
     * 
     * @param request to filter on
     * @return list of user objects
     */
    @Operation(summary = "Get a list of attendance Records.", description = "Given a Attendance Record Get Request, it will return a list of attendance records that match the request.")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Page<AttendanceRecord> getAttendanceRecords(AttendanceRecordGetRequest request) {
        return attendanceService.getAttendanceRecords(request);
    }
}
