/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.vbs.dao;

import static com.fbl.app.guardian.mapper.GuardianMapper.*;
import static com.fbl.app.vbs.mapper.VBSThemeGroupMapper.*;
import static com.fbl.app.vbs.mapper.VBSThemeMapper.*;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fbl.app.guardian.client.domain.Guardian;
import com.fbl.app.guardian.client.domain.request.GuardianGetRequest;
import com.fbl.app.vbs.client.domain.VBSStatus;
import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.client.domain.VBSThemeGroup;
import com.fbl.app.vbs.client.domain.request.VBSGuardianChildrenGetRequest;
import com.fbl.app.vbs.client.domain.request.VBSThemeGetRequest;
import com.fbl.common.page.Page;
import com.fbl.sql.abstracts.BaseDao;
import com.fbl.sql.builder.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for vbs guardian
 * children
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Repository
public class VBSDAO extends BaseDao {

    public VBSDAO(DataSource source) {
        super(source);
    }

    /**
     * Gets a list of vbs guardians based of the request filter
     * 
     * @param request to filter on
     * @return page of guardian children
     */
    public Page<Guardian> getGuardians(GuardianGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(EMAIL, request.getEmail())
                .withParam(PHONE, request.getPhone()).build();
        return getPage("getGuardiansPage", params, GUARDIAN_MAPPER);
    }

    /**
     * Gets a list of guardian children ids based of the request filter
     * 
     * @param request to filter on
     * @return list of ids
     */
    public List<Integer> getGuardianChildrenIds(VBSGuardianChildrenGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams().build();
        return getList("getGuardianChildrenIds", params, Integer.class);
    }

    /**
     * Gets a page of vbs themes
     * 
     * @param request The request to fitler by
     * @return The page of vbs themes
     */
    public Page<VBSTheme> getThemes(VBSThemeGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams().build();
        return getPage("getVBSThemePage", params, VBS_THEME_MAPPER);
    }

    /**
     * Gets the theme by id
     * 
     * @param request to filter on
     * @return vbs theme object
     */
    public Optional<VBSTheme> getThemeById(int id) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(ID, id).build();
        return getOptional("getThemeById", params, VBS_THEME_MAPPER);
    }

    /**
     * Gets the latest active theme. If there is no active theme, then it will
     * return the latest theme.
     * 
     * @return The latest active theme
     */
    public Optional<VBSTheme> getLatestActiveTheme() {
        return getOptional("getLatestActiveTheme", VBS_THEME_MAPPER);
    }

    /**
     * Gets the list of vbs theme groups by theme id.
     * 
     * @param id The id of the theme
     */
    public List<VBSThemeGroup> getThemeGroupsById(int id) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(VBS_THEME_ID, id).build();
        return getList("getThemeGroupsById", params, VBS_THEME_GROUP_MAPPER);
    }

    /**
     * Gets the theme offering total
     * 
     * @param vbsThemeId The theme id
     * @return The total offering for the theme
     */
    public Float getThemeOfferingTotal(int vbsThemeId) {
        return get("getThemeOfferingTotal", parameterSource(VBS_THEME_ID, vbsThemeId), Float.class);
    }

    /**
     * Gets the total children attended for the theme
     * 
     * @param vbsThemeId The theme id
     * @return The total children attended
     */
    public int getTotalChildrenAttended(int vbsThemeId) {
        return get("getTotalChildrenAttended", parameterSource(VBS_THEME_ID, vbsThemeId), Integer.class);
    }

    /**
     * The theme to be created
     * 
     * @param theme The theme to create
     */
    public int createTheme(VBSTheme theme) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(NAME, theme.getName())
                .withParam(START_DATE, theme.getStartDate()).withParam(END_DATE, theme.getEndDate())
                .withParam(DONATION, theme.getDonation()).build();

        post("createVBSTheme", params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Creates a theme group
     * 
     * @param vbsThemeId The vbs theme id
     * @param g          The group to be created
     */
    public VBSThemeGroup createThemeGroup(int vbsThemeId, VBSThemeGroup g) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(VBS_THEME_ID, vbsThemeId)
                .withParam(CHURCH_GROUP, g.getGroup()).withParam(NAME, g.getName()).build();
        post("createThemeGroup", params);
        g.setVbsThemeId(vbsThemeId);
        return g;
    }

    /**
     * Update a theme
     * 
     * @param id    The theme id
     * @param theme The theme to update with
     */
    public void updateTheme(int id, VBSTheme theme) {
        MapSqlParameterSource params = SqlParamBuilder.with()
                .withParam(ID, id)
                .withParam(NAME, theme.getName())
                .withParam(DONATION, theme.getDonation())
                .withParam(CHILDREN_REGISTERED, theme.getChildrenRegistered())
                .build();

        update("updateTheme", params);
    }

    /**
     * Update an attendance record
     * 
     * @param id     The attendance record id
     * @param status The status to update with
     */
    public void updateThemeStatus(int id, VBSStatus status) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(STATUS, status).withParam(ID, id).build();
        update("updateThemeStatus", params);
    }

    /**
     * Reopens a theme
     * 
     * @param id The id of the theme to reopen
     */
    public void reopenTheme(int id) {
        updateThemeStatus(id, VBSStatus.ACTIVE);
    }

    /**
     * Closes a theme
     * 
     * @param id             The id of the theme to close
     * @param closedByUserId The user id that closed the theme
     */
    public void closeTheme(int id, int closedByUserId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(STATUS, VBSStatus.CLOSED)
                .withParam(ID, id).withParam(CLOSED_BY_USER_ID, closedByUserId).build();
        update("closeTheme", params);
    }

    /**
     * Creates a theme group
     * 
     * @param vbsThemeId The vbs theme id
     * @param g          The group to be created
     */
    public void updateGroupByThemeId(int vbsThemeId, VBSThemeGroup g) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(VBS_THEME_ID, vbsThemeId)
                .withParam(CHURCH_GROUP, g.getGroup()).withParam(NAME, g.getName()).build();
        update("updateGroupByThemeId", params);
    }

    /**
     * The theme to be deleted
     * 
     * @param id The id of the vbs theme to delete
     */
    public void deleteTheme(int id) {
        delete("deleteTheme", parameterSource(VBS_THEME_ID, id));
    }
}
