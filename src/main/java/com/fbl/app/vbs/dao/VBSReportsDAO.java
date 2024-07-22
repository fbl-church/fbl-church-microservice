package com.fbl.app.vbs.dao;

import static com.fbl.app.vbs.mapper.VBSChildPointsCardMapper.*;

import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fbl.app.vbs.client.domain.VBSChildPointsCard;
import com.fbl.app.vbs.client.domain.VBSThemeGroup;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.common.page.Page;
import com.fbl.sql.abstracts.BaseDao;
import com.fbl.sql.builder.SqlParamBuilder;

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
     * Get the VBS Child Point Cards for the given attendance ids and group
     * 
     * @param attendanceIds The attendance ids to get the point cards for
     * @param group         The group to get the point cards for
     * @return a list of VBS Child Point Cards
     */
    public List<VBSChildPointsCard> getVBSChildPointCards(Set<Integer> attendanceIds, ChurchGroup group) {
        MapSqlParameterSource params = SqlParamBuilder.with()
                .withParam(ATTENDANCE_RECORD_ID, attendanceIds)
                .withParam(CHURCH_GROUP, group)
                .build();
        return getList("getVBSChildPointCards", params, VBS_CHILD_POINT_CARDS_MAPPER);
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
