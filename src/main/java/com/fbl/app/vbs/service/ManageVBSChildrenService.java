package com.fbl.app.vbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fbl.app.vbs.client.domain.VBSChildPoint;
import com.fbl.app.vbs.dao.VBSChildrenDAO;

/**
 * Manage VBS Children Service
 * 
 * @author Sam Butler
 * @since Jun 27, 2024
 */
@Service
public class ManageVBSChildrenService {

    @Autowired
    private VBSChildrenDAO vbsChildrenDAO;

    /**
     * Add points to a child
     * 
     * @param points The points to add to the child
     * @return The child points
     */
    public void updateChildPoints(int childId, List<VBSChildPoint> points) {
        if (!CollectionUtils.isEmpty(points)) {
            deleteChildPoints(childId, points.stream().map(VBSChildPoint::getVbsAttendanceId).toList());
            for (VBSChildPoint p : points) {
                vbsChildrenDAO.addPointsToChild(childId, p.getVbsAttendanceId(), p.getVbsPointId());
            }
        }
    }

    /**
     * Delete points from a child by the child id and attendance id
     * 
     * @param childId      The child id
     * @param attendanceId The attendance id
     */
    public void deleteChildPoints(int childId, List<Integer> attendanceIds) {
        vbsChildrenDAO.deleteChildPoints(childId, attendanceIds);
    }
}
