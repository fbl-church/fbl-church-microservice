package com.fbl.app.vbs.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.vbs.client.domain.VBSPoint;
import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.dao.VBSPointsDAO;
import com.fbl.jwt.utility.JwtHolder;

/**
 * Service to manage vbs points and calls to the dao
 * 
 * @author Sam Butler
 * @since Jun 20, 2024
 */
@Service
public class ManageVBSPointsService {

    @Autowired
    private VBSPointsDAO vbsPointsDao;

    @Autowired
    private VBSThemeService vbsThemeService;

    @Autowired
    private VBSPointsService vbsPointsService;

    @Autowired
    private JwtHolder jwtHolder;

    /**
     * Creates a new point config
     * 
     * @param pointConfig The point config to create
     * @return the created id
     */
    public List<VBSPoint> createPointConfigs(int vbsThemeId, List<VBSPoint> points) {
        VBSTheme theme = vbsThemeService.getThemeById(vbsThemeId);
        for (VBSPoint vbp : points) {
            vbp.setType(vbp.getDisplayName().trim().toUpperCase().replaceAll("\\s+", "_"));
            vbsPointsDao.createPointConfig(theme.getId(), jwtHolder.getUserId(), vbp);
        }
        return vbsPointsService.getVbsPointsByThemeId(vbsThemeId).getList();
    }

    /**
     * Update a point config by id.
     * 
     * @param id     The id of the point config to update
     * @param points The point configs to be updated
     */
    public void updatePointsConfig(int id, VBSPoint points) {
        points.setUpdatedUserId(jwtHolder.getUserId());
        points.setUpdatedDate(LocalDateTime.now());
        points.setType(points.getDisplayName().trim().toUpperCase().replaceAll("\\s+", "_"));
        vbsPointsDao.updatePointsConfig(id, points);
    }

    /**
     * Deletes a point config by id.
     * 
     * @param id The id of the point config to delete
     */
    public void deletePointConfigById(int id) {
        vbsPointsDao.deletePointConfigById(id);
    }

    /**
     * Deletes all point configs associated to the theme id
     * 
     * @param vbsThemeId The id of the theme to remove point configs from
     */
    public void deletePointConfigByThemeId(int vbsThemeId) {
        vbsPointsDao.deletePointConfigByThemeId(vbsThemeId);
    }
}
