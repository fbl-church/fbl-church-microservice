/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.awana.app.user.dao;

import static com.awana.app.user.mapper.UserStatusMapper.*;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.awana.app.user.client.domain.UserStatus;
import com.awana.sql.abstracts.BaseDao;
import com.awana.sql.builder.SqlParamBuilder;

/**
 * Class for handling the dao calls for a user status
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
@Repository
public class UserStatusDAO extends BaseDao {

    public UserStatusDAO(DataSource source) {
        super(source);
    }

    /**
     * Gets the status for the given user id.
     * 
     * @param userId The id of the user to get the status for.
     * @return {@link UserStatus} object
     */
    public UserStatus getUserStatusById(int userId) {
        return get("getUserStatusById", parameterSource(USER_ID, userId), USER_STATUS_MAPPER);
    }

    /**
     * Inserts the given user status object into the db
     * 
     * @param userStatus     Object to be inserted.
     * @param updatingUserId The user that has updated the user account status last.
     * @return {@link UserStatus} object
     */
    public void insertUserStatus(UserStatus userStatus, Integer updatingUserId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, userStatus.getUserId())
                .withParam(ACCOUNT_STATUS, userStatus.getAccountStatus())
                .withParam(APP_ACCESS, userStatus.isAppAccess()).withParam(UPDATE_USER_ID, updatingUserId).build();

        post("insertUserStatus", params);
    }

    /**
     * Updates a users status for the given user id
     * 
     * @param id The id of the user to get the status for.
     * @return {@link UserStatus} object
     */
    public void updateUserStatusByUserId(int id, UserStatus userStatus) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, id)
                .withParam(ACCOUNT_STATUS, userStatus.getAccountStatus())
                .withParam(APP_ACCESS, userStatus.isAppAccess())
                .withParam(UPDATE_USER_ID, userStatus.getUpdatedUserId()).build();

        update("updateUserStatus", params);
    }
}
