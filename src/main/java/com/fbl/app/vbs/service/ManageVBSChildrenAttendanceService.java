package com.fbl.app.vbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.attendance.client.domain.ChildAttendance;
import com.fbl.app.attendance.service.ChildAttendanceService;
import com.fbl.app.vbs.client.domain.VBSChildPoint;

/**
 * Manage VBS Children Attendance Service
 * 
 * @author Sam Butler
 * @since Jul 08, 2024
 */
@Service
public class ManageVBSChildrenAttendanceService {

    @Autowired
    private ChildAttendanceService childAttendanceService;

    @Autowired
    private ManageVBSChildrenService manageVBSChildrenService;

    /**
     * Check in child for vbs attedance record
     * 
     * @param childId  The points to add to the child
     * @param recordId The record id to add the child too
     * @param points   The points to add to the child
     * @return The child points
     */
    public void checkIn(int childId, int recordId, List<Integer> points) {
        ChildAttendance c = new ChildAttendance();
        c.setId(childId);
        List<VBSChildPoint> childPoints = points.stream()
                .map(p -> VBSChildPoint.builder().childId(childId).vbsAttendanceId(recordId).vbsPointId(p).build())
                .toList();

        childAttendanceService.assignChildToAttendanceRecord(recordId, c);
        manageVBSChildrenService.updateChildPoints(childId, childPoints);
    }

    /**
     * Mark child as absent for vbs attedance record
     * 
     * @param childId  The points to add to the child
     * @param recordId The record id to add the child too
     */
    public void markAbsent(int childId, int recordId) {
        childAttendanceService.removeChildFromAttendanceRecord(recordId, childId);
        manageVBSChildrenService.deleteChildPoints(childId, List.of(recordId));
    }
}
