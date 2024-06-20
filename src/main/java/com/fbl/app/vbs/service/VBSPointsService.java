package com.fbl.app.vbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.vbs.client.domain.VBSPoint;
import com.fbl.app.vbs.dao.VBSPointsDAO;
import com.fbl.common.page.Page;

/**
 * VBS points service for making calls to the dao
 * 
 * @author Sam Butler
 * @since Jun 20, 2024
 */
@Service
public class VBSPointsService {

    @Autowired
    private VBSPointsDAO vbsPointsDao;

    /**
     * Gets a page of vbs theme points by id
     * 
     * @param vbsThemeId to filter on
     * @return page of vbs points
     */
    public Page<VBSPoint> getVbsPointsByThemeId(int vbsThemeId) {
        return vbsPointsDao.getVBSPointsByThemeId(vbsThemeId);
    }
}
