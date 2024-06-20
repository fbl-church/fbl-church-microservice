/*
 * Copyright of FBL Church App. All rights reserved.
*/
package com.fbl.app.vbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fbl.app.vbs.client.domain.VBSPoint;
import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.dao.VBSDAO;

/**
 * Manage VBS Theme Service
 *
 * @author Sam Butler
 * @since Jun 08, 2024 06
 */
@Service
public class ManageVBSThemeService {
    @Autowired
    private VBSDAO vbsDao;

    @Autowired
    private VBSThemeService vbsThemeService;

    @Autowired
    private ManageVBSPointsService manageVBSPointsService;

    /**
     * The theme to be created.
     * 
     * @param theme The theme to create
     */
    public VBSTheme createTheme(VBSTheme theme) {
        int id = vbsDao.createTheme(theme);

        List<VBSPoint> createdPoints = null;
        if (!CollectionUtils.isEmpty(theme.getPoints())) {
            createdPoints = manageVBSPointsService.createPointConfigs(id, theme.getPoints());
        }

        VBSTheme createdTheme = vbsThemeService.getThemeById(id);
        createdTheme.setPoints(createdPoints);
        return createdTheme;
    }
}
