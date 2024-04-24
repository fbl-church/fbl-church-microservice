/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.vbs.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fbl.app.vbs.client.domain.request.VBSGuardianChildrenGetRequest;
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
     * Gets a list of guardian children ids based of the request filter
     * 
     * @param request to filter on
     * @return list of v objects
     */
    public List<Integer> getGuardianChildrenIds(VBSGuardianChildrenGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams().build();
        return getList("getGuardianChildrenIds", params, Integer.class);
    }
}
