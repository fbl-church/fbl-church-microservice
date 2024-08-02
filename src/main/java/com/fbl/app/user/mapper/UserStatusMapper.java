/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.user.client.domain.UserStatus;
import com.fbl.common.enums.AccountStatus;
import com.fbl.sql.abstracts.AbstractMapper;

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

        userStatus.setUserId(rs.getInt(ID));
        userStatus.setAccountStatus(AccountStatus.valueOf(rs.getString(ACCOUNT_STATUS)));
        userStatus.setAppAccess(rs.getBoolean(APP_ACCESS));

        return userStatus;
    }
}
