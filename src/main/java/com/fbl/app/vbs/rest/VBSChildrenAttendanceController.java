package com.fbl.app.vbs.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.attendance.client.domain.request.ChildAttendanceGetRequest;
import com.fbl.app.vbs.client.domain.VBSChildAttendance;
import com.fbl.app.vbs.openapi.TagVBS;
import com.fbl.app.vbs.service.ManageVBSChildrenAttendanceService;
import com.fbl.app.vbs.service.VBSChildrenAttendanceService;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.page.Page;

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

    @Autowired
    private VBSChildrenAttendanceService vbsChildrenAttendanceService;

    /**
     * Get a page of child attendance records and points for a given theme id and
     * child id
     * 
     * @param id      The id of the child
     * @param themeId The theme id
     * @param request The page request
     * @return The page of VBS Child Attendance
     */
    @GetMapping("/{id}/theme/{themeId}/attendance")
    public Page<VBSChildAttendance> getVBSChildAttendance(@PathVariable int id, @PathVariable int themeId,
            ChildAttendanceGetRequest request) {
        return vbsChildrenAttendanceService.getVBSChildAttendance(id, themeId, request);
    }

    /**
     * Get a page of VBS children by attendance id
     * 
     * @param attendanceId The attendance id to get the children for
     * @param request      The page request
     * @return The page of VBS children
     */
    @GetMapping("/attendance/{attendanceId}")
    public Page<VBSChildAttendance> getVBSChildrenByAttendanceId(@PathVariable int attendanceId,
            ChildAttendanceGetRequest request) {
        return vbsChildrenAttendanceService.getVBSChildrenByAttendanceId(attendanceId, request);
    }

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

    /**
     * Mark child as absent for vbs attedance record
     * 
     * @param childId  The points to add to the child
     * @param recordId The record id to add the child too
     */
    @DeleteMapping("/{childId}/attendance/{recordId}")
    public void markAbsent(@PathVariable int childId, @PathVariable int recordId) {
        manageVBSChildrenService.markAbsent(childId, recordId);
    }
}
