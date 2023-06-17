/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fbl.app.user.client.domain.Application;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.app.user.dao.UserDAO;
import com.fbl.common.page.Page;
import com.fbl.jwt.utility.JwtHolder;

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

	/**
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @param id The user id to get applications for
	 * @return List of Application objects {@link Application}
	 */
	public List<Application> getUserAppsById(int id) {
		return dao.getUserApps(id);
	}
}