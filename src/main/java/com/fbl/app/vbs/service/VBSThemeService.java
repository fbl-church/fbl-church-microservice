/*
 * Copyright of FBL Church App. All rights reserved.
*/
package com.fbl.app.vbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.client.domain.VBSThemeGroup;
import com.fbl.app.vbs.client.domain.request.VBSThemeGetRequest;
import com.fbl.app.vbs.dao.VBSDAO;
import com.fbl.common.page.Page;
import com.fbl.exception.types.NotFoundException;

/**
 * VBS Theme Service
 *
 * @author Sam Butler
 * @since Jun 08, 2024 06
 */
@Service
public class VBSThemeService {
    @Autowired
    private VBSDAO vbsDao;

    /**
     * Gets a page of vbs themes
     * 
     * @param request The request to fitler by
     * @return The page of vbs themes
     */
    public Page<VBSTheme> getThemes(VBSThemeGetRequest request) {
        return vbsDao.getThemes(request);
    }

    /**
     * Gets the theme by id. If no id is matched, then it will throw a not found
     * exception.
     * 
     * @param id The id to fetch
     * @return the found vbs theme
     */
    public VBSTheme getThemeById(int id) {
        VBSTheme t = vbsDao.getThemeById(id)
                .orElseThrow(() -> new NotFoundException("VBS Theme not found for id: " + id));
        t.setMoney(getThemeOfferingTotal(id));
        t.setChildrenAttended(getTotalChildrenAttended(id));
        return t;
    }

    /**
     * Gets the latest active theme. If there is no active theme, then it will
     * return the latest theme.
     * 
     * @return The latest active theme
     */
    public VBSTheme getLatestActiveTheme() {
        VBSTheme t = vbsDao.getLatestActiveTheme().orElse(null);
        if (t == null) {
            return null;
        }
        t.setChildrenAttended(getTotalChildrenAttended(t.getId()));
        return t;
    }

    /**
     * Gets the list of vbs theme groups by theme id.
     * 
     * @param id The id of the theme
     */
    public List<VBSThemeGroup> getThemeGroupsById(int id) {
        return vbsDao.getThemeGroupsById(id);
    }

    /**
     * Gets the theme offering total
     * 
     * @param vbsThemeId The theme id
     * @return The total offering for the theme
     */
    private Float getThemeOfferingTotal(int vbsThemeId) {
        return vbsDao.getThemeOfferingTotal(vbsThemeId);
    }

    /**
     * Gets the total children attended for the theme
     * 
     * @param vbsThemeId The theme id
     * @return The total children attended
     */
    private int getTotalChildrenAttended(int vbsThemeId) {
        return vbsDao.getTotalChildrenAttended(vbsThemeId);
    }
}
