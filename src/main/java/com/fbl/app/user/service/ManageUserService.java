/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fbl.app.email.client.EmailClient;
import com.fbl.app.user.client.UserCredentialsClient;
import com.fbl.app.user.client.UserStatusClient;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.UserStatus;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.app.user.dao.UserDAO;
import com.fbl.common.enums.AccountStatus;
import com.fbl.common.enums.WebRole;
import com.fbl.common.util.CommonUtil;
import com.fbl.exception.types.InsufficientPermissionsException;
import com.fbl.jwt.utility.JwtHolder;

/**
 * User Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Service
public class ManageUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManageUserService.class);

	@Autowired
	private JwtHolder jwtHolder;

	@Autowired
	private UserDAO dao;

	@Autowired
	private UserService userService;

	@Autowired
	private UserCredentialsClient userCredentialsClient;

	@Autowired
	private UserStatusClient UserStatusClient;

	@Autowired
	private EmailClient emailClient;

	/**
	 * Create a new user. This is an account created by someone other the user
	 * accessing the account.
	 * 
	 * @param user The user object to be created.
	 * @return The new user that was created.
	 */
	public User createUser(User user, boolean sendEmail) {
		if (!WebRole.hasPermission(jwtHolder.getWebRole(), user.getWebRole())) {
			throw new InsufficientPermissionsException(String
					.format("Insufficient permission to create a user of role '%s'", jwtHolder.getWebRole(),
							user.getWebRole()));
		}

		int newUserId = dao.insertUser(user);
		assignUserRoles(newUserId, user.getWebRole());
		userCredentialsClient.insertUserPassword(newUserId, String.valueOf(CommonUtil.generateRandomNumber()));
		UserStatusClient.insertUserStatus(new UserStatus(newUserId, AccountStatus.ACTIVE, true, null));
		User createdUser = userService.getUserById(newUserId);
		if (sendEmail) {
			emailClient.sendNewUserEmail(createdUser);
		}
		return createdUser;
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
		userCredentialsClient.insertUserPassword(newUserId, String.valueOf(CommonUtil.generateRandomNumber()));
		UserStatusClient.insertUserStatus(new UserStatus(newUserId, AccountStatus.PENDING, false, null));
		User createdUser = userService.getUserById(newUserId);
		emailClient.sendNewUserEmail(createdUser);
		return createdUser;
	}

	/**
	 * Update the user for the given user object.
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information.
	 */
	public User updateUser(User user) {
		return updateUser(jwtHolder.getUserId(), user);
	}

	/**
	 * Updates a user for the given id.
	 * 
	 * @param id of the user
	 * @return user associated to that id with the updated information.
	 */
	public User updateUserById(int id, User user) {
		User updatingUser = userService.getUserById(id);
		if (id != updatingUser.getId() && !WebRole.hasPermission(jwtHolder.getWebRole(), updatingUser.getWebRole())) {
			throw new InsufficientPermissionsException(String
					.format("Insufficient permission to update a user of role '%s'", jwtHolder.getWebRole(),
							updatingUser.getWebRole()));
		}

		return updateUser(id, user);
	}

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
	 * Update the user's information such as email, first name, and last name
	 * 
	 * @param id    The user id to be updated with the new roles
	 * @param roles List of roles to be updated
	 * @return The updated user information
	 */
	public User updateUserRoles(int id, List<WebRole> roles) {
		assignUserRoles(id, roles);
		return userService.getUserById(id);
	}

	/**
	 * Add a web role to a list of users
	 * 
	 * @param webRole The web role to add to the users
	 * @param userIds The list of user ids to udpate
	 * @return The list of users that were updated
	 */
	public List<User> addWebRoleToUsers(WebRole webRole, List<Integer> userIds) {
		UserGetRequest request = new UserGetRequest();

		List<User> filteredUsers = Collections.emptyList();
		if (!CollectionUtils.isEmpty(userIds)) {
			request.setId(userIds.stream().collect(Collectors.toSet()));
			filteredUsers = userService.getUsers(request).getList();
		}

		for (User u : filteredUsers) {
			try {
				dao.insertUserRole(u.getId(), webRole);
			} catch (Exception e) {
				LOGGER.error("User id '{}' already has web role '{}'", u.getId(), webRole);
			}
		}

		return filteredUsers;
	}

	/**
	 * Delete user by id
	 * 
	 * @param userId The user Id to be deleted
	 */
	public void deleteUser(int userId) {
		dao.deleteUser(userId);
	}

	/**
	 * Update the user for the given user object.
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information.
	 */
	private User updateUser(int userId, User user) {
		dao.updateUser(userId, user);
		assignUserRoles(userId, user.getWebRole());
		return userService.getUserById(userId);
	}

	/**
	 * Adds the list of web roles to the given user id.
	 * 
	 * @param userId The user id
	 * @param roles  The roles to assign to the user id
	 */
	private void assignUserRoles(int userId, List<WebRole> roles) {
		dao.deleteUserRoles(userId);

		if (roles == null) {
			roles = new ArrayList<>();
		} else {
			roles.removeIf(r -> r.equals(WebRole.USER));
		}

		roles.add(WebRole.USER);
		for (WebRole r : roles) {
			dao.insertUserRole(userId, r);
		}
	}
}
