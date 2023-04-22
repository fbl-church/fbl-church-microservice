/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awana.app.user.client.UserCredentialsClient;
import com.awana.app.user.client.domain.User;
import com.awana.app.user.client.domain.UserStatus;
import com.awana.app.user.dao.UserDAO;
import com.awana.common.enums.AccountStatus;
import com.awana.exception.types.InsufficientPermissionsException;
import com.awana.jwt.utility.JwtHolder;

/**
 * User Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Service
public class ManageUserService {

	@Autowired
	private JwtHolder jwtHolder;

	@Autowired
	private UserDAO dao;

	@Autowired
	private UserService userService;

	@Autowired
	private UserCredentialsClient userCredentialsClient;

	@Autowired
	private ManageUserStatusService manageUserStatusService;

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 */
	public User updateUserLastLoginToNow(int userId) {
		dao.updateUserLastLoginToNow(userId);
		return userService.getUserById(userId);
	}

	/**
	 * Create a new user. This is an account created by someone other the user
	 * accessing the account.
	 * 
	 * @param user The user object to be created.
	 * @return The new user that was created.
	 */
	public User createUser(User user) {
		if(jwtHolder.getWebRole().getRank() <= user.getWebRole().getRank()) {
			throw new InsufficientPermissionsException(String
					.format("Your role of '%s' can not create a user of role '%s'", jwtHolder.getWebRole(),
							user.getWebRole()));
		}
		int newUserId = dao.insertUser(user);
		userCredentialsClient.insertUserPassword(newUserId, user.getPassword());
		manageUserStatusService.insertUserStatus(new UserStatus(newUserId, AccountStatus.APPROVED, true));
		return userService.getUserById(newUserId);
	}

	/**
	 * Register a new user account. Gets called when a user is creating an account
	 * for themselves.
	 * 
	 * @param user The user object to be created.
	 * @return The new user that was created.
	 */
	public User registerUser(User user) {
		int newUserId = dao.insertUser(user);
		userCredentialsClient.insertUserPassword(newUserId, user.getPassword());
		manageUserStatusService.insertUserStatus(new UserStatus(newUserId, AccountStatus.PENDING, false));
		return userService.getUserById(newUserId);
	}
}