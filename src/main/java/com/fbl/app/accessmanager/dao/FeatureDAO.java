/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.dao;

import static com.fbl.app.accessmanager.mapper.FeatureAccessMapper.*;
import static com.fbl.app.accessmanager.mapper.FeatureMapper.*;
import static com.fbl.app.accessmanager.mapper.WebRoleFeatureMapper.*;

import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fbl.app.accessmanager.client.domain.CRUD;
import com.fbl.app.accessmanager.client.domain.Feature;
import com.fbl.app.accessmanager.client.domain.WebRoleFeature;
import com.fbl.app.accessmanager.client.domain.request.FeatureGetRequest;
import com.fbl.app.accessmanager.client.domain.request.WebRoleFeatureGetRequest;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;
import com.fbl.sql.abstracts.BaseDao;
import com.fbl.sql.builder.SqlParamBuilder;

/**
 * DAO for handing feature access for a user
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class FeatureDAO extends BaseDao {

    public FeatureDAO(DataSource source) {
        super(source);
    }

    /**
     * Get a page of features
     * 
     * @param request The request to filter on.
     * @return {@link Page} of the features
     */
    public Page<Feature> getPageOfFeatures(FeatureGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams().withParam(ID, request.getId())
                .build();
        return getPage("getFeaturesPage", params, FEATURE_MAPPER);
    }

    /**
     * Get a page of web role feature access
     * 
     * @param request The request to filter on.
     * @return {@link Page} of the feature access
     */
    public Page<WebRoleFeature> getPageOfWebRoleFeatures(int featureId, WebRoleFeatureGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams()
                .withParamTextEnumCollection(WEB_ROLE, request.getWebRole())
                .withParam(FEATURE_ID, featureId).build();

        return getPage("getWebRoleFeaturesPage", params, WEB_ROLE_FEATURE_MAPPER);

    }

    /**
     * Get a map of feature access for the provided web roles.
     * 
     * @param roles The web roles to get the feature access for
     * @return {@link Map<String,String>} of the feature access
     */
    @Cacheable(value = "featureAccess", key = "#roles")
    public Map<String, List<Map<String, String>>> getWebRoleFeatureAccess(List<WebRole> roles) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParamTextEnumCollection(WEB_ROLE, roles).build();
        return mapSingleton(getList("getFeatureAccess", params, FEATURE_ACCESS_MAPPER));
    }

    /**
     * Updates the feature enabled flag
     * 
     * @param featureId The feaure id to update
     * @param enabled   The flag to set on the feature
     * @return The updated feature
     */
    @CacheEvict(cacheNames = "featureAccess", allEntries = true)
    public void updateFeatureEnabledFlag(int featureId, boolean enabled) {
        MapSqlParameterSource params = SqlParamBuilder.with().useAllParams()
                .withParam(FEATURE_ID, featureId)
                .withParam(ENABLED, enabled).build();

        update("updateFeatureEnabledFlag", params);
    }

    /**
     * Updates the crud access for the given web role feature
     * 
     * @param webRoleFeature The web role feature update
     * @return The updated web role feature
     */
    @CacheEvict(cacheNames = "featureAccess", allEntries = true)
    public void updateWebRoleFeatureAccess(int featureId, WebRole webRole, CRUD crud) {
        MapSqlParameterSource params = SqlParamBuilder.with().useAllParams()
                .withParam(WEB_ROLE, webRole)
                .withParam(FEATURE_ID, featureId)
                .withParam(CREATE, crud.getCreate())
                .withParam(READ, crud.getRead())
                .withParam(UPDATE, crud.getUpdate())
                .withParam(DELETE, crud.getDelete()).build();

        update("updateWebRoleFeatureAccess", params);
    }

    /**
     * Will assign a web role to a feature.
     * 
     * @param appId The feature id to assign the role too
     * @param role  The role to be assigned
     */
    public void assignWebRoleToFeature(int featureId, WebRole role, boolean enabled) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(WEB_ROLE, role).withParam(FEATURE_ID, featureId)
                .build();
        post("assignWebRoleToFeaure", params);
    }

    /**
     * Create a new feature
     * 
     * @param feature The feature to be created
     * @return The created feature
     */
    public int createNewFeature(String key, int appId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(KEY, key).withParam(APP_ID, appId)
                .build();

        post("createNewFeature", params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Delete the roles from the feature
     * 
     * @param featureId The app id to remove role access from
     */
    public void deleteRolesFromFeature(int featureId) {
        delete("deleteRolesFromFeature", parameterSource(FEATURE_ID, featureId));
    }

    /**
     * Delete the roles from the feature
     * 
     * @param featureId The app id to remove role access from
     */
    public void deleteFeatureById(int featureId) {
        delete("deleteFeatureById", parameterSource(FEATURE_ID, featureId));
    }

    /**
     * Map's the list to a single map
     * 
     * @param data to amp
     * @return new hash map
     */
    private Map<String, List<Map<String, String>>> mapSingleton(List<Feature> data) {
        Map<String, List<Map<String, String>>> dataMap = new HashMap<String, List<Map<String, String>>>();

        for (Feature f : data) {
            Map<String, String> current = new HashMap<String, String>();
            current.put(f.getFeature(), filterAccessString(f.getAccess()));
            if (dataMap.get(f.getApp()) == null) {
                dataMap.put(f.getApp(), new ArrayList<Map<String, String>>());
                dataMap.get(f.getApp()).add(current);
            } else {
                dataMap.get(f.getApp()).add(current);
            }
        }

        return dataMap;
    }

    /**
     * Filter access string and sort into CRUD format
     * 
     * @param access The access to filter and map
     * @return The formatted string.
     */
    private String filterAccessString(String access) {
        String priorityRule = "< c < r < u < d";
        List<String> accessList = Arrays.asList(access.replace(",", "").split("")).stream().distinct()
                .collect(Collectors.toList());

        try {
            Collections.sort(accessList, new RuleBasedCollator(priorityRule));
        } catch (ParseException e) {
            return "";
        }
        return accessList.stream().collect(Collectors.joining());
    }
}
