/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.awana.app.user.client.domain.User;
import com.awana.app.user.client.domain.request.UserGetRequest;
import com.awana.app.user.dao.UserDAO;
import com.awana.common.page.Page;
import com.awana.jwt.utility.JwtHolder;

/**
 * User Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class UserService {

	@Autowired
	private JwtHolder jwtHolder;

	@Autowired
	private UserDAO dao;

	/**
	 * Get users based on given request filter
	 * 
	 * @param request of the user
	 * @return User object {@link User}
	 */
	public Page<User> getUsers(UserGetRequest request) {
		return dao.getUsers(request);
	}

	/**
	 * Get the current user from the jwt token.
	 * 
	 * @return User object {@link User}
	 */
	public User getCurrentUser() {
		return getUserById(jwtHolder.getUserId());
	}

	/**
	 * Service to get a users given the user id
	 * 
	 * @param id of the user
	 * @return User object {@link User}
	 */
	public User getUserById(int id) {
		return dao.getUserById(id);
	}
}
