/*
 * Copyright of FBL Church App. All rights reserved.
*/
package com.fbl.app.vbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fbl.app.attendance.service.ManageAttendanceService;
import com.fbl.app.vbs.client.domain.VBSAttendanceRecord;
import com.fbl.app.vbs.client.domain.VBSPoint;
import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.client.domain.VBSThemeGroup;
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
    private VBSAttendanceService vbsAttendanceService;

    @Autowired
    private ManageVBSPointsService manageVBSPointsService;

    @Autowired
    private ManageVBSAttendanceService manageVBSAttendanceService;

    @Autowired
    private ManageAttendanceService manageAttendanceService;

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

        if (!CollectionUtils.isEmpty(theme.getGroups())) {
            for (VBSThemeGroup g : theme.getGroups()) {
                if (!StringUtils.hasText(g.getName())) {
                    g.setName(null);
                }
                vbsDao.createThemeGroup(id, g);
            }
        }

        VBSTheme createdTheme = vbsThemeService.getThemeById(id);
        createdTheme.setPoints(createdPoints);

        manageVBSAttendanceService.createVBSAttendanceRecordsByTheme(id, createdTheme);

        return createdTheme;
    }

    /**
     * Updates the group by theme id
     * 
     * @param id    The id of the theme
     * @param group The group to update
     */
    public void updateGroupByThemeId(int id, VBSThemeGroup group) {
        vbsDao.updateGroupByThemeId(id, group);
    }

    /**
     * The theme to be deleted
     * 
     * @param id The id of the vbs theme to delete
     */
    public void deleteTheme(int id) {
        List<VBSAttendanceRecord> attendanceRecords = vbsAttendanceService.getVBSAttendanceRecordsByThemeId(id);
        vbsDao.deleteTheme(id);
        for (VBSAttendanceRecord r : attendanceRecords) {
            manageAttendanceService.deleteAttendanceRecordById(r.getId());
        }
    }
}
