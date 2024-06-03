/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.app.user.dao.UserDAO;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;
import com.fbl.exception.types.NotFoundException;
import com.fbl.jwt.utility.JwtHolder;
import com.google.common.collect.Sets;

/**
 * User Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Service
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
		User foundUser = dao.getUserById(id).orElseThrow(() -> new NotFoundException("User", id));
		foundUser.setWebRole(dao.getUserRolesById(id, List.of(WebRole.USER)));
		return foundUser;
	}

	/**
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @param id The user id to get applications for
	 * @return List of Applications
	 */
	public List<String> getUserAppsById(int id) {
		getUserById(id);
		return dao.getUserApps(id);
	}

	/**
	 * Gets a list of user roles by user id.
	 * 
	 * @param id The user id to get roles for
	 * @return List of Web Roles
	 */
	public List<WebRole> getUserRolesById(int id, List<WebRole> excludedRoles) {
		getUserById(id);
		return dao.getUserRolesById(id, excludedRoles);
	}

	/**
	 * This will check to see if the email exists. If it does then it will return
	 * true, otherwise false.
	 * 
	 * @param email The email to check
	 * @return {@link Boolean} to see if the email exists
	 */
	public boolean doesEmailExist(String email) {
		UserGetRequest request = new UserGetRequest();
		request.setEmail(Sets.newHashSet(email));
		List<User> users = getUsers(request).getList();

		return users.size() > 0;
	}
}
