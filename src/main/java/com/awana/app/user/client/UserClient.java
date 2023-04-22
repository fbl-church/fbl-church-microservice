/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.awana.app.user.client.domain.User;
import com.awana.app.user.client.domain.request.UserGetRequest;
import com.awana.app.user.service.ManageUserService;
import com.awana.app.user.service.UserService;
import com.awana.common.annotations.interfaces.Client;

/**
 * This class exposes the user endpoint's to other app's to pull data across the
 * platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class UserClient {

	@Autowired
	private UserService userService;

	@Autowired
	private ManageUserService manageUserService;

	/**
	 * Get users based on given request filter.
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 */
	public List<User> getUsers(UserGetRequest request) {
		return userService.getUsers(request).getList();
	}

	/**
	 * Gets the current logged in user information.
	 * 
	 * @return The user currently logged in.
	 */
	public User getCurrentUser() {
		return userService.getCurrentUser();
	}

	/**
	 * Client method to get the user given a user id
	 * 
	 * @param id of the user
	 * @return User profile object
	 */
	public User getUserById(int id) {
		return userService.getUserById(id);
	}

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 */
	public User updateUserLastLoginToNow(int id) {
		return manageUserService.updateUserLastLoginToNow(id);
	}
}