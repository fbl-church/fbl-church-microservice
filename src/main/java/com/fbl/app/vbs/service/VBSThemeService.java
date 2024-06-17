/*
 * Copyright of FBL Church App. All rights reserved.
*/
package com.fbl.app.vbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.dao.VBSDAO;

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
     * The theme to be created
     * 
     * @param theme The theme to create
     */
    public VBSTheme getThemeById(int id) {
        return vbsDao.getThemeById(id);
    }
}
