package com.fbl.app.vbs.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.vbs.openapi.TagVBS;
import com.fbl.app.vbs.service.ManageVBSChildrenAttendanceService;
import com.fbl.common.annotations.interfaces.RestApiController;

/**
 * VBS Children Attendance Controller
 * 
 * @author Sam Butler
 * @since Jun 27, 2024
 */
@RequestMapping("/api/vbs/children")
@RestApiController
@TagVBS
public class VBSChildrenAttendanceController {

    @Autowired
    private ManageVBSChildrenAttendanceService manageVBSChildrenService;

    /**
     * Check in child for vbs attedance record
     * 
     * @param childId  The points to add to the child
     * @param recordId The record id to add the child too
     * @param points   The points to add to the child
     */
    @PostMapping("/{childId}/attendance/{recordId}")
    public void checkIn(@PathVariable int childId, @PathVariable int recordId,
            @RequestBody List<Integer> points) {
        manageVBSChildrenService.checkIn(childId, recordId, points);
    }
}
