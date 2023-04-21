/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.awana.app.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.awana.app.user.client.domain.UserStatus;
import com.awana.common.enums.AccountStatus;
import com.awana.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a User status Object {@link UserStatus}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class UserStatusMapper extends AbstractMapper<UserStatus> {
    public static UserStatusMapper USER_STATUS_MAPPER = new UserStatusMapper();

    public UserStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserStatus userStatus = new UserStatus();

        userStatus.setUserId(rs.getInt(USER_ID));
        userStatus.setAccountStatus(AccountStatus.valueOf(rs.getString(ACCOUNT_STATUS)));
        userStatus.setAppAccess(rs.getBoolean(APP_ACCESS));
        userStatus.setUpdatedUserId(rs.getInt(UPDATE_USER_ID));

        return userStatus;
    }
}
