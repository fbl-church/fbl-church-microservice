package com.fbl.app.vbs.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.vbs.client.domain.VBSPointDivision;
import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.dao.VBSPointDivisionDAO;
import com.fbl.jwt.utility.JwtHolder;

/**
 * Manage VBS Point Division Service
 * 
 * @author Sam Butler
 * @since Jul 25, 2024
 */
@Service
public class ManageVBSPointDivisionService {

    @Autowired
    private VBSPointDivisionDAO vbsPointDivisionDAO;

    @Autowired
    private VBSThemeService vbsThemeService;

    @Autowired
    private VBSPointDivisionService vbsPointDivisionService;

    @Autowired
    private JwtHolder jwtHolder;

    /**
     * Creates new point divisions for a theme
     * 
     * @param pointDivisions The point divisions to create
     * @return the point divisions for the theme
     */
    public List<VBSPointDivision> createPointDivisions(int vbsThemeId, List<VBSPointDivision> pointDivisions) {
        VBSTheme theme = vbsThemeService.getThemeById(vbsThemeId);
        for (VBSPointDivision vpd : pointDivisions) {
            vbsPointDivisionDAO.createPointDivision(theme.getId(), jwtHolder.getUserId(), vpd);
        }
        return vbsPointDivisionService.getVBSPointDivisionsByThemeId(vbsThemeId).getList();
    }

    /**
     * Update a point division by id.
     * 
     * @param id            The id of the point division to update
     * @param pointDivision The point division to be updated
     */
    public void updatePointsDivision(int id, VBSPointDivision pointDivision) {
        pointDivision.setUpdatedUserId(jwtHolder.getUserId());
        pointDivision.setUpdatedDate(LocalDateTime.now());
        vbsPointDivisionDAO.updatePointDivision(id, pointDivision);
    }

    /**
     * Deletes a point division by id.
     * 
     * @param id The id of the point division to delete
     */
    public void deletePointDivisionById(int id) {
        vbsPointDivisionDAO.deletePointDivisionById(id);
    }

    /**
     * Deletes all point divisions associated to the theme id
     * 
     * @param vbsThemeId The id of the theme to remove point division from
     */
    public void deletePointDivisionByThemeId(int vbsThemeId) {
        vbsPointDivisionDAO.deletePointDivisionByThemeId(vbsThemeId);
    }
}
