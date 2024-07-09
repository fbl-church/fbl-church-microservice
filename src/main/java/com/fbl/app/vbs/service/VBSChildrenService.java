package com.fbl.app.vbs.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.vbs.client.domain.VBSChildPoint;
import com.fbl.app.vbs.client.domain.request.VBSChildrenPointsGetRequest;
import com.fbl.app.vbs.dao.VBSChildrenDAO;
import com.fbl.common.page.Page;

/**
 * Description
 * 
 * @author Sam Butler
 * @since Jun 27, 2024
 */
@Service
public class VBSChildrenService {
    @Autowired
    private VBSChildrenDAO vbsChildrenDAO;

    /**
     * Get child points by the children points get request
     * 
     * @param childId The child id
     * @param request The children points get request
     * @return The child points
     */
    public Page<VBSChildPoint> getChildPoints(int childId, VBSChildrenPointsGetRequest request) {
        return vbsChildrenDAO.getChildPoints(childId, request);
    }

    /**
     * Get child points by the child id and attendance id
     * 
     * @param childId      The child id
     * @param attendanceId The attendance id
     * @return The list of child points
     */
    public List<VBSChildPoint> getChildPointsByAttendanceId(int childId, int attendanceId) {
        VBSChildrenPointsGetRequest request = Page.unpaged(VBSChildrenPointsGetRequest.class);
        request.setAttendanceId(Set.of(attendanceId));
        return vbsChildrenDAO.getChildPoints(childId, request).getList();
    }
}
