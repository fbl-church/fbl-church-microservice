/**
 * Copyright of Awana App. All rights reserved.
 */
/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.awana.app.user.client.UserCredentialsClient;
import com.awana.app.user.client.domain.User;
import com.awana.app.user.dao.UserDAO;
import com.awana.common.exception.InsufficientPermissionsException;
import com.awana.jwt.utility.JwtHolder;

/**
 * User Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
@Transactional
public class ManageUserService {

	@Autowired
	private UserDAO dao;

	@Autowired
	private UserService userService;

	@Autowired
	private UserCredentialsClient userCredentialsClient;

	@Autowired
	private JwtHolder jwtHolder;

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
	public User addUser(User user) {
		if(jwtHolder.getWebRole().getRank() <= user.getWebRole().getRank()) {
			throw new InsufficientPermissionsException(String
					.format("Your role of '%s' can not create a user of role '%s'", jwtHolder.getWebRole(),
							user.getWebRole()));
		}
		int newUserId = dao.insertUser(user);
		userCredentialsClient.insertUserPassword(newUserId, user.getPassword());
		return userService.getUserById(newUserId);
	}
}
