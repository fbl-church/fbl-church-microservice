/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.vbs.dao;

import static com.fbl.app.guardian.mapper.GuardianMapper.*;
import static com.fbl.app.vbs.mapper.VBSThemeMapper.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fbl.app.guardian.client.domain.Guardian;
import com.fbl.app.guardian.client.domain.request.GuardianGetRequest;
import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.client.domain.request.VBSGuardianChildrenGetRequest;
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
     * Gets the theme by id
     * 
     * @param request to filter on
     * @return vbs theme object
     */
    public VBSTheme getThemeById(int id) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(ID, id).build();
        return get("getThemeById", params, VBS_THEME_MAPPER);
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
     * The theme to be created
     * 
     * @param theme The theme to create
     */
    public int createTheme(VBSTheme theme) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(NAME, theme.getName())
                .withParam(YEAR, theme.getYear()).withParam(DONATION, theme.getDonation()).build();

        post("createVBSTheme", params, keyHolder);
        return keyHolder.getKey().intValue();
    }
}
