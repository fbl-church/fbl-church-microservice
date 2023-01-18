package com.awana.app.user.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.awana.app.user.client.domain.User;
import com.awana.app.user.client.domain.request.UserGetRequest;
import com.awana.app.user.service.UserProfileService;
import com.awana.common.annotations.interfaces.Client;

/**
 * This class exposes the user endpoint's to other app's to pull data across the
 * platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class UserProfileClient {

	@Lazy
	@Autowired
	private UserProfileService userProfileService;

	/**
	 * Get users based on given request filter.
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 * @throws Exception
	 */
	public List<User> getUsers(UserGetRequest request) throws Exception {
		return userProfileService.getUsers(request);
	}

	/**
	 * Gets the current logged in user information.
	 * 
	 * @return The user currently logged in.
	 * @throws Exception
	 */
	public User getCurrentUser() throws Exception {
		return userProfileService.getCurrentUser();
	}

	/**
	 * Client method to get the user given a user id
	 * 
	 * @param id of the user
	 * @return User profile object
	 * @throws Exception
	 */
	public User getUserById(int id) throws Exception {
		return userProfileService.getUserById(id);
	}
}
