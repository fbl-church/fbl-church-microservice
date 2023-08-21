/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.dao;

import static com.fbl.app.user.mapper.UserMapper.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fbl.app.user.client.domain.Application;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.common.date.TimeZoneUtil;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;
import com.fbl.exception.types.NotFoundException;
import com.fbl.sql.abstracts.BaseDao;
import com.fbl.sql.builder.SqlParamBuilder;
import com.google.common.collect.Sets;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class UserDAO extends BaseDao {

	public UserDAO(DataSource source) {
		super(source);
	}

	/**
	 * Get users based on given request filter
	 * 
	 * @param request of the user
	 * @return User object {@link User}
	 * @throws Exception
	 */
	public Page<User> getUsers(UserGetRequest request) {
		MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams().withParam(ID, request.getId())
				.withParam(EMAIL, request.getEmail()).withParam(FIRST_NAME, request.getFirstName())
				.withParam(LAST_NAME, request.getLastName()).withParamTextEnumCollection(WEB_ROLE, request.getWebRole())
				.withParamTextEnumCollection(NOT_WEB_ROLE, request.getNotWebRole())
				.build();

		return getPage("getUsersPage", params, USER_MAPPER);
	}

	/**
	 * This method returns a user object containing type information about the user
	 * 
	 * @param id of the user
	 * @return User object {@link User}
	 */
	public User getUserById(int id) {
		try {
			UserGetRequest request = new UserGetRequest();
			request.setId(Sets.newHashSet(id));
			return getUsers(request).getList().get(0);
		} catch (Exception e) {
			throw new NotFoundException("User", id);
		}
	}

	/**
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @param userId The user id to get the apps for.
	 * @return List of Application objects {@link Application}
	 */
	public List<String> getUserApps(int userId) {
		MapSqlParameterSource params = parameterSource(USER_ID, userId);
		return getList("getApplications", params, String.class);
	}

	/**
	 * Get a list of user roles by id.
	 * 
	 * @param userId The user id
	 * @return List of Webroles
	 */
	public List<WebRole> getUserRolesById(int userId) {
		MapSqlParameterSource params = parameterSource(USER_ID, userId);
		return getList("getUserRoles", params, WebRole.class);
	}

	/**
	 * Creates a new user for the given user object.
	 * 
	 * @param user The user to create.
	 * @return {@link Integer} auto increment id of the new user.
	 */
	public int insertUser(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(FIRST_NAME, user.getFirstName())
				.withParam(LAST_NAME, user.getLastName()).withParam(EMAIL, user.getEmail()).build();

		post("insertUser", params, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * Inserts a user role
	 * 
	 * @param userId  The user to get the role
	 * @param webRole The role to assign
	 */
	public void insertUserRole(int userId, WebRole webRole) {
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, userId).withParam(WEB_ROLE, webRole)
				.build();
		post("insertUserRole", params);
	}

	/**
	 * Update the user for the given user object. Null out password field so that it
	 * is not returned on the {@link User} object
	 * 
	 * @param userId Id of the usre being updated.
	 * @param user   what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 */
	public void updateUser(int userId, User user) {
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(FIRST_NAME, user.getFirstName())
				.withParam(LAST_NAME, user.getLastName()).withParam(EMAIL, user.getEmail()).withParam(ID, userId)
				.build();

		update("updateUser", params);
	}

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 */
	public void updateUserLastLoginToNow(int userId) {
		MapSqlParameterSource params = SqlParamBuilder.with()
				.withParam(LAST_LOGIN_DATE, LocalDateTime.now(TimeZoneUtil.SYSTEM_ZONE)).withParam(ID, userId).build();
		update("updateUserLastLoginToNow", params);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 */
	public void deleteUser(int id) {
		delete("deleteUser", parameterSource(ID, id));
	}

	/**
	 * Delete the user roles for the given id.
	 * 
	 * @param userId The user id to remove the roles from.
	 */
	public void deleteUserRoles(int userId) {
		delete("deleteUserRoles", parameterSource(USER_ID, userId));
	}
}
