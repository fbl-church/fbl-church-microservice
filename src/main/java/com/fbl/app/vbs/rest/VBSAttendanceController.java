/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.vbs.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.vbs.client.domain.VBSAttendanceRecord;
import com.fbl.app.vbs.openapi.TagVBS;
import com.fbl.app.vbs.service.ManageVBSAttendanceService;
import com.fbl.app.vbs.service.VBSAttendanceService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.AttendanceStatus;
import com.fbl.common.enums.WebRole;

/**
 * VBS Attendance Controller
 * 
 * @author Sam Butler
 * @since Jun 22, 2024
 */
@RequestMapping("/api/vbs/attendance")
@RestApiController
@TagVBS
public class VBSAttendanceController {
    @Autowired
    private VBSAttendanceService vbsAttendanceService;

    @Autowired
    private ManageVBSAttendanceService manageVBSAttendanceService;

    /**
     * Returns a list of vbs attendance records for the given theme id.
     * 
     * @param id the id of the vbs theme
     * @return list of vbs attendance records
     */
    @GetMapping("/themes/{id}")
    public List<VBSAttendanceRecord> getAttendanceRecordsByThemeId(@PathVariable int id) {
        return vbsAttendanceService.getVBSAttendanceRecordsByThemeId(id);
    }

    /**
     * Gets a vbs attendance record by id
     * 
     * @param id the id of the vbs attendance record
     * @return vbs attendance record
     */
    @GetMapping("/{id}")
    public VBSAttendanceRecord getAttendanceRecordById(@PathVariable int id) {
        return vbsAttendanceService.getAttendanceRecordById(id);
    }

    /**
     * Updates a VBS attendance record by id
     * 
     * @param id     The vbs attendance record id
     * @param record The record data to update
     */
    @PutMapping("/{id}")
    public void updateVBSAttendanceRecordById(@PathVariable int id, @RequestBody VBSAttendanceRecord record) {
        manageVBSAttendanceService.updateVBSAttendanceRecordById(id, record);
    }

    /**
     * Create a VBS attendance record by theme id
     * 
     * @param id     The vbs theme id
     * @param record The record data to create
     */
    @PostMapping("/themes/{id}")
    public VBSAttendanceRecord createVBSAttendanceRecord(@PathVariable int id,
            @RequestBody VBSAttendanceRecord record) {
        return manageVBSAttendanceService.createVBSAttendanceRecord(id, record);
    }

    /**
     * Updates the status of an attendance record. If the status of the theme is not
     * active, then it will update the theme status to active.
     * 
     * @param id     The attendance record id
     * @param status The status to update with
     */
    @PutMapping("/{id}/status/{status}")
    public void updateAttendanceRecordStatus(@PathVariable int id, @PathVariable AttendanceStatus status) {
        manageVBSAttendanceService.updateAttendanceRecordStatus(id, status);
    }

    /**
     * Re-Open a closed vbs attendance record
     * 
     * @param id The id of the theme
     * @return The updated theme
     */
    @PutMapping("/{id}/reopen")
    @HasAccess(WebRole.ADMINISTRATOR)
    public VBSAttendanceRecord reopenTheme(@PathVariable int id) {
        return manageVBSAttendanceService.reopenTheme(id);
    }
}
