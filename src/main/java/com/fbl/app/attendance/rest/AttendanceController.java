/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.rest;

import static org.springframework.http.MediaType.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.client.domain.request.AttendanceRecordGetRequest;
import com.fbl.app.attendance.openapi.TagAttendance;
import com.fbl.app.attendance.service.AttendanceService;
import com.fbl.app.user.client.domain.User;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.page.Page;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Attendance Controller
 *
 * @author Sam Butler
 * @since 8/3/2020
 */
@RequestMapping("/api/attendance-records")
@RestApiController
@TagAttendance
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    /**
     * Gets a list of attendance records based of the request filter
     * 
     * @param request to filter on
     * @return list of attendance record objects
     */
    @Operation(summary = "Get a list of attendance Records.", description = "Given a Attendance Record Get Request, it will return a list of attendance records that match the request.")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Page<AttendanceRecord> getAttendanceRecords(AttendanceRecordGetRequest request) {
        return attendanceService.getAttendanceRecords(request);
    }

    /**
     * Gets the Attendance Record based on the given id
     * 
     * @param id The attendance record id
     * @return The Attendance Record that matches that id
     */
    @Operation(summary = "Get anattendance Record  by Attendance Id", description = "Given a Attendance Record Id, it will return an attendance record workers that match the id.")
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public AttendanceRecord getAttendanceRecordById(@PathVariable int id) {
        return attendanceService.getAttendanceRecordById(id);
    }

    /**
     * Gets a list of workers that are the on the attendance record by id
     * 
     * @param id The attendance record id
     * @return List of workers
     */
    @Operation(summary = "Get a list of attendance Record Workers by Attendance Id", description = "Given a Attendance Record Id, it will return a list of attendance record workers that match the id.")
    @GetMapping(path = "/{id}/workers", produces = APPLICATION_JSON_VALUE)
    public List<User> getAttendanceRecordWorkersById(@PathVariable int id) {
        return attendanceService.getAttendanceRecordWorkersById(id);
    }
}
