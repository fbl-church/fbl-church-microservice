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
    public VBSChildPoint addPointsToChild(List<VBSChildPoint> points) {
        if (!CollectionUtils.isEmpty(points)) {
            for (VBSChildPoint p : points) {
                vbsChildrenDAO.addPointsToChild(p.getChildId(), p.getVbsAttendanceId(), p.getVbsPointId());
            }
        }

        return null;
    }
}
