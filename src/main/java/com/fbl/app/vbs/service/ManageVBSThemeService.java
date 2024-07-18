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
import com.fbl.app.vbs.client.domain.VBSStatus;
import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.client.domain.VBSThemeGroup;
import com.fbl.app.vbs.dao.VBSDAO;
import com.fbl.common.enums.AttendanceStatus;
import com.fbl.exception.types.ServiceException;
import com.fbl.jwt.utility.JwtHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * Manage VBS Theme Service
 *
 * @author Sam Butler
 * @since Jun 08, 2024 06
 */
@Service
@Slf4j
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

    @Autowired
    private JwtHolder jwtHolder;

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
     * Update a theme
     * 
     * @param id    The id of the theme
     * @param theme The theme to update
     * @return The updated theme
     */
    public VBSTheme updateThemeById(int id, VBSTheme theme) {
        vbsDao.updateTheme(id, theme);
        return vbsThemeService.getThemeById(id);
    }

    /**
     * Update an attendance record
     * 
     * @param id     The attendance record id
     * @param status The status to update with
     * @return The record that was updated
     */
    public VBSTheme updateThemeStatus(int id, VBSStatus status) {
        VBSTheme theme = vbsThemeService.getThemeById(id);
        if (VBSStatus.CLOSED.equals(theme.getStatus())) {
            throw new ServiceException("Cannot update status of record that is already closed.");
        }

        if (VBSStatus.CLOSED.equals(status)) {
            vbsDao.closeTheme(id, jwtHolder.getUserId());
            closeThemeAttendanceRecords(id);
        } else {
            vbsDao.updateThemeStatus(id, status);
        }

        return vbsThemeService.getThemeById(id);
    }

    /**
     * Re-Opens a closed attendance record
     * 
     * @param id The attendance record to reopen
     * @return The record that was updated
     */
    public VBSTheme reopenTheme(int id) {
        VBSTheme theme = vbsThemeService.getThemeById(id);
        if (VBSStatus.ACTIVE.equals(theme.getStatus())) {
            log.warn("VBS Theme '{}' is already active", id);
            return theme;
        }

        vbsDao.reopenTheme(id);

        return vbsThemeService.getThemeById(id);
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

    /**
     * Close out all attendance records for a theme
     * 
     * @param vbsThemeId The theme id
     */
    private void closeThemeAttendanceRecords(int vbsThemeId) {
        List<VBSAttendanceRecord> records = vbsAttendanceService.getVBSAttendanceRecordsByThemeId(vbsThemeId);
        for (VBSAttendanceRecord r : records) {
            if (!AttendanceStatus.CLOSED.equals(r.getStatus())) {
                manageAttendanceService.updateAttendanceRecordStatus(r.getId(), AttendanceStatus.CLOSED);
            }
        }
    }
}
