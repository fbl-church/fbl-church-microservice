/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.featureaccess.dao;

import static com.awana.app.featureaccess.mapper.FeatureAccessMapper.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.awana.app.featureaccess.client.domain.Feature;
import com.awana.common.enums.WebRole;
import com.awana.sql.abstracts.BaseDao;
import com.awana.sql.builder.SqlParamBuilder;

/**
 * DAO for handing feature access for a user
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class FeatureAccessDao extends BaseDao {

    public FeatureAccessDao(DataSource source) {
        super(source);
    }

    /**
     * Get the feature access for the user
     * 
     * @param app to filter features on
     * @return boolean if that user has feature access for the application
     */
    public Map<String, List<Map<String, String>>> getFeatureAccess(WebRole role) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(WEB_ROLE, role).build();
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

        for(Feature f : data) {
            Map<String, String> current = new HashMap<String, String>();
            current.put(f.getFeature(), f.getAccess());
            if(dataMap.get(f.getApp()) == null) {
                dataMap.put(f.getApp(), new ArrayList<Map<String, String>>());
                dataMap.get(f.getApp()).add(current);
            }else {
                List<Map<String, String>> temp = dataMap.get(f.getApp());
                temp.add(current);
            }
        }

        return dataMap;
    }
}
