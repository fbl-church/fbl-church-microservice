package com.fbl.app.vbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.vbs.client.domain.VBSPointDivision;
import com.fbl.app.vbs.dao.VBSPointDivisionDAO;
import com.fbl.common.page.Page;

/**
 * VBs points Division service for making calls to the dao
 * 
 * @author Sam Butler
 * @since Jul 25, 2024
 */
@Service
public class VBSPointDivisionService {
    @Autowired
    private VBSPointDivisionDAO vbsPointDivisionDAO;

    /**
     * Gets a page of vbs theme points by id
     * 
     * @param vbsThemeId to filter on
     * @return page of vbs points
     */
    public Page<VBSPointDivision> getVBSPointDivisionsByThemeId(int vbsThemeId) {
        return vbsPointDivisionDAO.getVBSPointDivisionsByThemeId(vbsThemeId);
    }

    /**
     * Checks to see if the given value is within the range of any existing point
     * 
     * @param id    The id of the theme to check
     * @param value The value to check
     * @return true if the value is within the range of any existing point, false
     *         otherwise
     */
    public boolean isRangeValueWithinExistingRangeForThemeId(int id, int value) {
        return vbsPointDivisionDAO.isRangeValueWithinExistingRangeForThemeId(id, value);
    }
}
