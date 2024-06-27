package com.fbl.app.vbs.service;

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
}
