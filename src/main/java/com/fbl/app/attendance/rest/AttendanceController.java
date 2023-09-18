/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.client.domain.request.AttendanceRecordGetRequest;
import com.fbl.app.attendance.openapi.TagAttendance;
import com.fbl.app.attendance.service.AttendanceService;
import com.fbl.app.attendance.service.ManageAttendanceService;
import com.fbl.app.user.client.domain.User;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.AttendanceStatus;
import com.fbl.common.enums.WebRole;
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

    @Autowired
    private ManageAttendanceService manageAttendanceService;

    /**
     * Gets a list of attendance records based of the request filter
     * 
     * @param request to filter on
     * @return list of attendance record objects
     */
    @Operation(summary = "Get a list of attendance Records.", description = "Given a Attendance Record Get Request, it will return a list of attendance records that match the request.")
    @GetMapping
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
    @GetMapping("/{id}")
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
    @GetMapping("/{id}/workers")
    public List<User> getAttendanceRecordWorkersById(@PathVariable int id) {
        return attendanceService.getAttendanceRecordWorkersById(id);
    }

    /**
     * Create a new attendance record
     * 
     * @param record The attendance record to create
     * @return The record that was created
     */
    @Operation(summary = "Create a new Attendance Record", description = "Given an Attendance Record, it will create a new record for that attendance.")
    @PostMapping
    @HasAccess(WebRole.JUNIOR_CHURCH_SUPERVISOR)
    public AttendanceRecord createAttendanceRecord(@RequestBody @Valid AttendanceRecord record) {
        return manageAttendanceService.createAttendanceRecord(record);
    }

    /**
     * Update an attendance record
     * 
     * @param id     The attendance record id
     * @param status The status to update with
     * @return The record that was updated
     */
    @Operation(summary = "Update a new Attendance Record", description = "Given an Attendance Record, it will update that attendance record.")
    @PutMapping("/{id}")
    @HasAccess(WebRole.JUNIOR_CHURCH_SUPERVISOR)
    public AttendanceRecord updateAttendanceRecord(@PathVariable int id, @RequestBody @Valid AttendanceRecord record) {
        return manageAttendanceService.updateAttendanceRecord(id, record);
    }

    /**
     * Update an attendance record status
     * 
     * @param record The attendance record to update
     * @return The record that was updated
     */
    @Operation(summary = "Update a the status of an attendance record", description = "Given an Attendance Record id and status, it will update the status of that record.")
    @PutMapping("/{id}/status/{status}")
    @HasAccess(WebRole.JUNIOR_CHURCH_SUPERVISOR)
    public AttendanceRecord updateAttendanceRecordStatus(@PathVariable int id, @PathVariable AttendanceStatus status) {
        return manageAttendanceService.updateAttendanceRecordStatus(id, status);
    }

    /**
     * Updates the workers of an attendance record by id
     * 
     * @param id      The attendance record id
     * @param workers The list of workers to be assigned
     * @return The list of users that were assigned to the attendance record
     */
    @Operation(summary = "Update the workers on an attendance record", description = "Given a Attendance Record id and a list of user ids, it will update the workers on the attendance record.")
    @PutMapping("/{id}/workers")
    @HasAccess(WebRole.SITE_ADMINISTRATOR)
    public List<User> updateAttendanceRecordWorkers(@PathVariable int id, @RequestBody List<User> workers) {
        return manageAttendanceService.assignWorkersToAttendanceRecord(id, workers);
    }

    /**
     * Delete an attendance record by id.
     * 
     * @param id The attendance record id
     */
    @Operation(summary = "Delete an attendance record.", description = "Given a Attendance Record id, it will delete that record.")
    @DeleteMapping("/{id}")
    @HasAccess(WebRole.SITE_ADMINISTRATOR)
    public void deleteAttendanceRecordById(@PathVariable int id) {
        manageAttendanceService.deleteAttendanceRecordById(id);
    }
}
