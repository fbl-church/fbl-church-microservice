/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.awana.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.awana.app.user.client.domain.User;
import com.awana.app.user.dao.UserProfileDAO;

/**
 * User Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class ManageUserProfileService {

	@Autowired
	private UserProfileDAO dao;

	@Autowired
	private UserProfileService userProfileService;

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 */
	public User updateUserLastLoginToNow(int userId) {
		dao.updateUserLastLoginToNow(userId);
		return userProfileService.getUserById(userId);
	}
}
