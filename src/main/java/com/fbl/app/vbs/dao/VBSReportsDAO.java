package com.fbl.app.vbs.dao;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.fbl.app.vbs.client.domain.VBSThemeGroup;
import com.fbl.common.page.Page;
import com.fbl.sql.abstracts.BaseDao;

/**
 * VBS Reports DAO
 * 
 * @author Sam Butler
 * @since Jun 21, 2024
 */
@Repository
public class VBSReportsDAO extends BaseDao {

    public VBSReportsDAO(DataSource source) {
        super(source);
    }

    /**
     * Gets the count of how many children are registered for VBS.
     * 
     * @return The number of registered VBS Children
     */
    public Integer getCountOfRegisteredVBSChildren() {
        return get("getRegisteredVBSChildren", Integer.class);
    }

    /**
     * Get the snack details for the VBS
     * 
     * @param id The id of the attendance record to get for the snack details
     * @return a page of VBS Theme Groups
     */
    public Page<VBSThemeGroup> getSnackDetails(int id) {
        return null;
    }

}
