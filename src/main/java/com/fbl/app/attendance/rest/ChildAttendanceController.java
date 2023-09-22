/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.client.domain.ChildAttendance;
import com.fbl.app.attendance.client.domain.request.ChildAttendanceGetRequest;
import com.fbl.app.attendance.openapi.TagAttendance;
import com.fbl.app.attendance.service.ChildAttendanceService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Child Attendance Controller
 *
 * @author Sam Butler
 * @since 8/3/2020
 */
@RequestMapping("/api/attendance-records")
@RestApiController
@TagAttendance
public class ChildAttendanceController {

    @Autowired
    private ChildAttendanceService childAttendanceService;

    /**
     * Gets a Page of children that are the on the attendance record by id
     * 
     * @param id The attendance record id
     * @return List of workers
     */
    @Operation(summary = "Get a Page of attendance Record children by Attendance Id", description = "Given a Attendance Record Id, it will return a Page of attendance record children that match the id.")
    @GetMapping("/{id}/children")
    public Page<ChildAttendance> getAttendanceRecordChildrenById(ChildAttendanceGetRequest request,
            @PathVariable int id) {
        return childAttendanceService.getAttendanceRecordChildrenById(id, request);
    }

    /**
     * Gets the child attendance for the given record id and child id.
     * 
     * @param recordId The attendance record id
     * @param childId  The child id to check out
     * @return The Child Attendance
     */
    @Operation(summary = "Gets the child attendance", description = "Given a Attendance Record Id and child id, It will return the associated child attendance.")
    @GetMapping("/{recordId}/children/{childId}")
    public ChildAttendance getChildAttendanceById(@PathVariable int recordId, @PathVariable int childId) {
        return childAttendanceService.getChildAttendanceById(recordId, childId);
    }

    /**
     * Assigns the child to the attendance record by id
     * 
     * @param id The attendance record id
     * @param ca The child attendance containing the id and optional notes
     * @return The updated attendance record
     */
    @Operation(summary = "Assigns the child to the attendance record by id.", description = "Given a Attendance Record id and child id, it will assign them to the record.")
    @PostMapping("/{recordId}/children")
    @HasAccess(WebRole.JUNIOR_CHURCH_WORKER)
    public ChildAttendance assignChildToAttendanceRecord(@PathVariable int recordId, @RequestBody ChildAttendance ca) {
        return childAttendanceService.assignChildToAttendanceRecord(recordId, ca);
    }

    /**
     * Updates the child notes on an attendance record
     * 
     * @param id The attendance record id
     * @param ca The child attendance to update
     * @return The updated attendance record
     */
    @Operation(summary = "Updates the child notes on an attendance record.", description = "Given a Attendance Record id and child id, it will update the notes for the child attendance.")
    @PutMapping(value = "/{recordId}/children")
    @HasAccess(WebRole.JUNIOR_CHURCH_WORKER)
    public ChildAttendance updateChildNotes(@PathVariable int recordId, @RequestBody ChildAttendance ca) {
        return childAttendanceService.updateChildNotes(recordId, ca);
    }

    /**
     * Check out of the child on the given record id.
     * 
     * @param recordId The attendance record id
     * @param childId  The child id to check out
     * @return The updated child Attendance
     */
    @Operation(summary = "Will check out the child from the attendance record", description = "Given a Attendance Record id and child id, it will check out the child from the record.")
    @PutMapping(value = "/{recordId}/children/{childId}")
    @HasAccess(WebRole.JUNIOR_CHURCH_WORKER)
    public ChildAttendance checkOutChildFromAttendanceRecord(@PathVariable int recordId, @PathVariable int childId) {
        return childAttendanceService.checkOutChildFromAttendanceRecord(recordId, childId);
    }

    /**
     * Remove the child from the attendance record by id
     * 
     * @param recordId The attendance record id
     * @param childId  The child id attendance to update
     * @return The updated attendance record
     */
    @Operation(summary = "Removes the child from the attendance record by id.", description = "Given a Attendance Record id and child id, it will remove them from the record.")
    @DeleteMapping("/{recordId}/children/{childId}")
    @HasAccess(WebRole.JUNIOR_CHURCH_WORKER)
    public AttendanceRecord removeChildFromAttendanceRecord(@PathVariable int recordId, @PathVariable int childId) {
        return childAttendanceService.removeChildFromAttendanceRecord(recordId, childId);
    }

}
