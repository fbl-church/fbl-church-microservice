/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.user.client.domain.User;
import com.fbl.common.enums.AccountStatus;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a User Profile Object {@link User}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class UserMapper extends AbstractMapper<User> {
	public static UserMapper USER_MAPPER = new UserMapper();

	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(ID));
		user.setFirstName(rs.getString(FIRST_NAME));
		user.setLastName(rs.getString(LAST_NAME));
		user.setEmail(rs.getString(EMAIL));
		user.setAccountStatus(AccountStatus.valueOf(rs.getString(ACCOUNT_STATUS)));
		user.setAppAccess(rs.getBoolean(APP_ACCESS));

		try {
			user.setPassword(rs.getString(PASSWORD));
		} catch (Exception e) {
			user.setPassword(null);
		}

		user.setLastLoginDate(rs.getTimestamp(LAST_LOGIN_DATE).toLocalDateTime());
		user.setInsertDate(rs.getTimestamp(INSERT_DATE).toLocalDateTime());
		return user;
	}
}
