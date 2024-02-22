/*
 * Copyright of FBL Church App. All rights reserved.
*/
package com.fbl.app.user.dao;

import static com.fbl.app.user.mapper.UserScheduleMapper.*;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fbl.app.user.client.domain.UserSchedule;
import com.fbl.app.user.client.domain.request.UserScheduleRequest;
import com.fbl.common.page.Page;
import com.fbl.sql.abstracts.BaseDao;
import com.fbl.sql.builder.SqlParamBuilder;

/**
 * User Schedule DAO
 *
 * @author Sam Butler
 * @since Feb 14, 2024 02
 */
@Repository
public class UserScheduleDAO extends BaseDao {

    public UserScheduleDAO(DataSource source) {
        super(source);
    }

    /**
     * Gets a list of user scheduled based on the user id
     * 
     * @param userId The id of the user to get schedules for
     * @return list of user schedule objects
     */
    public Page<UserSchedule> getUserSchedulesById(UserScheduleRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with().useAllParams().withParam(USER_ID, request.getUserId())
                .withParam(MONTH, request.getMonths())
                .build();
        return getPage("getUserSchedulePage", params, USER_SCHEDULE_MAPPER);
    }
}