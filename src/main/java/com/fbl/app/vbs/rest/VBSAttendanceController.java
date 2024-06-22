/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.vbs.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.vbs.client.domain.VBSAttendanceRecord;
import com.fbl.app.vbs.openapi.TagVBS;
import com.fbl.app.vbs.service.VBSAttendanceService;
import com.fbl.common.annotations.interfaces.RestApiController;

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

    /**
     * Returns a list of vbs attendance records for the given theme id.
     * 
     * @param id the id of the vbs theme
     * @return list of vbs attendance records
     */
    @GetMapping("/themes/{id}")
    public List<VBSAttendanceRecord> getGuardians(@PathVariable int id) {
        return vbsAttendanceService.getVBSAttendanceRecordsByThemeId(id);
    }
}
