package com.fbl.app.vbs.dao;

import static com.fbl.app.vbs.mapper.VBSPointsMapper.*;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fbl.app.vbs.client.domain.VBSPoint;
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
public class VBSPointsDAO extends BaseDao {

    public VBSPointsDAO(DataSource source) {
        super(source);
    }

    /**
     * Gets a page of vbs theme points by id
     * 
     * @param vbsThemeId to filter on
     * @return page of vbs points
     */
    public Page<VBSPoint> getVBSPointsByThemeId(int vbsThemeId) {
        return getPage("getVBSPointsPageByThemeId", parameterSource(VBS_THEME_ID, vbsThemeId), VBS_POINTS_MAPPER);
    }

    /**
     * Creates a new point config
     * 
     * @param vbsThemeId  The id of the vbs theme to link the points too
     * @param userId      The id of the user creating the point config
     * @param pointConfig The point config to create
     * @return the created id
     */
    public int createPointConfig(int vbsThemeId, int userId, VBSPoint pointConfig) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = SqlParamBuilder.with()
                .withParam(TYPE, pointConfig.getType())
                .withParam(DISPLAY_NAME, pointConfig.getDisplayName())
                .withParam(POINTS, pointConfig.getPoints())
                .withParam(VBS_THEME_ID, vbsThemeId)
                .withParam(UPDATED_USER_ID, userId)
                .withParam(INSERT_USER_ID, userId).build();

        post("createPointConfig", params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Deletes a point config by id.
     * 
     * @param id The id of the point config to delete
     */
    public void deletePointConfigById(int id) {
        delete("deletePointConfigs", parameterSource(ID, id));
    }

    /**
     * Deletes all point configs associated to the theme id
     * 
     * @param vbsThemeId The id of the theme to remove point configs from
     */
    public void deletePointConfigByThemeId(int vbsThemeId) {
        delete("deletePointConfigs", parameterSource(VBS_THEME_ID, vbsThemeId));
    }
}
