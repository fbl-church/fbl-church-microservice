package com.fbl.app.vbs.dao;

import static com.fbl.app.vbs.mapper.VBSPointDivisionMapper.*;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fbl.app.vbs.client.domain.VBSPointDivision;
import com.fbl.common.page.Page;
import com.fbl.sql.abstracts.BaseDao;
import com.fbl.sql.builder.SqlParamBuilder;

/**
 * Handles dao calls to do the database for vbs points
 * 
 * @author Sam Butler
 * @since Jun 20, 2024
 */
@Repository
public class VBSPointDivisionDAO extends BaseDao {

    public VBSPointDivisionDAO(DataSource source) {
        super(source);
    }

    /**
     * Gets a page of vbs theme point divisions by id
     * 
     * @param vbsThemeId to filter on
     * @return page of vbs point divisions
     */
    public Page<VBSPointDivision> getVBSPointDivisionsByThemeId(int vbsThemeId) {
        return getPage("getVBSPointDivisionPageByThemeId", parameterSource(VBS_THEME_ID, vbsThemeId),
                VBS_POINT_DIVISION_MAPPER);
    }

    /**
     * Checks to see if the given value is within the range of any existing point
     * 
     * @param id    The id of the theme to check
     * @param value The value to check
     * @return true if the value is within the range of any existing point, false
     *         otherwise
     */
    public boolean isRangeValueWithinExistingRangeForThemeId(int vbsThemeId, int value) {
        return getOptional("isRangeValueWithinExistingRangeForThemeId",
                parameterSource(VBS_THEME_ID, vbsThemeId).addValue(VALUE, value),
                Boolean.class).orElse(false);
    }

    /**
     * Creates a new point division
     * 
     * @param vbsThemeId    The id of the theme to create the point division for
     * @param userId        The id of the user creating the point division
     * @param pointDivision The point division to create
     * @return the created id
     */
    public int createPointDivision(int vbsThemeId, int userId, VBSPointDivision pointDivision) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = SqlParamBuilder.with()
                .withParam(MIN, pointDivision.getMin())
                .withParam(MAX, pointDivision.getMax())
                .withParam(COLOR, pointDivision.getColor())
                .withParam(VBS_THEME_ID, vbsThemeId)
                .withParam(UPDATED_USER_ID, userId)
                .withParam(INSERT_USER_ID, userId).build();

        post("createPointDivision", params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Update a point division by id.
     * 
     * @param id            The id of the point division to update
     * @param pointDivision The point division to be updated
     */
    public void updatePointDivision(int id, VBSPointDivision pointDivision) {
        MapSqlParameterSource params = SqlParamBuilder.with()
                .withParam(ID, id)
                .withParam(MIN, pointDivision.getMin())
                .withParam(MAX, pointDivision.getMax())
                .withParam(COLOR, pointDivision.getColor())
                .withParam(UPDATED_USER_ID, pointDivision.getUpdatedUserId())
                .withParam(UPDATED_DATE, pointDivision.getUpdatedDate())
                .build();
        update("updatePointDivision", params);
    }

    /**
     * Deletes a point division by id.
     * 
     * @param id The id of the point division to delete
     */
    public void deletePointDivisionById(int id) {
        delete("deletePointDivision", parameterSource(ID, id));
    }

    /**
     * Deletes all point divisions associated to the theme id
     * 
     * @param vbsThemeId The id of the theme to remove point divisions from
     */
    public void deletePointDivisionByThemeId(int vbsThemeId) {
        delete("deletePointDivision", parameterSource(VBS_THEME_ID, vbsThemeId));
    }
}
