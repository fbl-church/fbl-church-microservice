/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.dao;

import static com.fbl.app.accessmanager.mapper.FeatureAccessMapper.*;
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

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fbl.app.accessmanager.client.domain.Feature;
import com.fbl.app.accessmanager.client.domain.WebRoleFeature;
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
public class FeatureAccessDAO extends BaseDao {

    public FeatureAccessDAO(DataSource source) {
        super(source);
    }

    /**
     * Get a page of web role feature access
     * 
     * @param request The request to filter on.
     * @return {@link Page} of the feature access
     */
    public Page<WebRoleFeature> getPageOfWebRoleFeatures(WebRoleFeatureGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams()
                .withParamTextEnumCollection(WEB_ROLE, request.getWebRole()).build();

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
