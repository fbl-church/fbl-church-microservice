/*
 * Copyright of FBL Church App. All rights reserved.
*/
package com.fbl.app.vbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.dao.VBSDAO;
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
     * Gets the theme by id. If no id is matched, then it will throw a not found
     * exception.
     * 
     * @param id The id to fetch
     * @return the found vbs theme
     */
    public VBSTheme getThemeById(int id) {
        return vbsDao.getThemeById(id).orElseThrow(() -> new NotFoundException("VBS Theme not found for id: " + id));
    }
}
