/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fbl.app.email.client.EmailClient;
import com.fbl.app.user.client.UserClient;
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

import lombok.extern.slf4j.Slf4j;

/**
 * User Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Service
@Slf4j
public class ManageUserService {

	@Autowired
	private JwtHolder jwtHolder;

	@Autowired
	private UserDAO dao;

	@Autowired
	private UserClient userClient;

	@Autowired
	private UserCredentialsClient userCredentialsClient;

	@Autowired
	private UserStatusClient userStatusClient;

	@Autowired
	private EmailClient emailClient;

	/**
	 * Create a new user. This is an account created by someone other the user
	 * accessing the account.
	 * 
	 * @param user      The user object to be created.
	 * @param sendEmail To determine if it should send an email to the new user
	 * @return The new user that was created.
	 */
	public User createUser(User user, boolean sendEmail) {
		if (!WebRole.hasPermission(jwtHolder.getWebRole(), user.getWebRole())) {
			throw new InsufficientPermissionsException(
					String.format("Insufficient permission for user '%d' to create a user of role '%s'",
							jwtHolder.getUserId(),
							WebRole.highestRoleRank(user.getWebRole())));
		}

		return createUser(user, UserStatus.builder().accountStatus(AccountStatus.ACTIVE).appAccess(true), sendEmail);
	}

	/**
	 * Register a new user account. Gets called when a user is creating an account
	 * for themselves.
	 * 
	 * @param user The user object to be created.
	 * @return The new user that was created.
	 */
	public User registerUser(User user) {
		return createUser(user, UserStatus.builder().accountStatus(AccountStatus.PENDING).appAccess(false), true);
	}

	/**
	 * Update the user for the given user object. This is only called when a user is
	 * updating their own personal information from the profile page.
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information.
	 */
	public User updateUser(User user) {
		return updateUser(jwtHolder.getUserId(), user, false);
	}

	/**
	 * Updates a user for the given id.
	 * 
	 * @param id          of the user
	 * @param user        The user object to update with
	 * @param updateRoles Boolean if it should update the user roles or not.
	 * @return user associated to that id with the updated information.
	 */
	public User updateUserById(int id, User user, boolean updateRoles) {
		User updatingUser = userClient.getUserById(id);
		if (!WebRole.hasPermission(jwtHolder.getWebRole(), updatingUser.getWebRole())) {
			throw new InsufficientPermissionsException(String
					.format("Insufficient permission for user '%d' to update a user of role '%s'",
							jwtHolder.getUserId(),
							WebRole.highestRoleRank(updatingUser.getWebRole())));
		}

		return updateUser(id, user, updateRoles);
	}

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 */
	public User updateUserLastLoginToNow(int userId) {
		dao.updateUserLastLoginToNow(userId);
		return userClient.getUserById(userId);
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
		return userClient.getUserById(id);
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
			filteredUsers = userClient.getUsers(request);
		}

		for (User u : filteredUsers) {
			try {
				dao.insertUserRole(u.getId(), webRole);
			} catch (Exception e) {
				log.warn("User id '{}' already has web role '{}'", u.getId(), webRole);
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
		UserStatus deleteStatus = new UserStatus();
		deleteStatus.setAccountStatus(AccountStatus.INACTIVE);
		deleteStatus.setAppAccess(false);
		userStatusClient.updateUserStatusByUserId(userId, deleteStatus);
	}

	/**
	 * Base create user method.
	 * 
	 * @param user       The user to be created
	 * @param userStatus The status to set for the user
	 * @param sendEmail  If it should send the user an email or not
	 * @return The created User
	 */
	private User createUser(User user, UserStatus.UserStatusBuilder userStatusBuilder, boolean sendEmail) {
		int newUserId = dao.insertUser(user);
		assignUserRoles(newUserId, user.getWebRole());
		userCredentialsClient.insertUserPassword(newUserId, CommonUtil.generateRandomString(32));
		userStatusClient.insertUserStatus(userStatusBuilder.userId(newUserId).build());
		User createdUser = userClient.getUserById(newUserId);
		if (sendEmail) {
			emailClient.sendNewUserEmail(createdUser);
		}
		return createdUser;
	}

	/**
	 * Update the user for the given user object.
	 * 
	 * @param userId      The id of the user to be updated
	 * @param user        what information on the user needs to be updated.
	 * @param updateRoles Boolean if it should update the user roles or not.
	 * @return user associated to that id with the updated information.
	 */
	private User updateUser(int userId, User user, boolean updateRoles) {
		dao.updateUser(userId, user);
		if (updateRoles) {
			assignUserRoles(userId, user.getWebRole());
		}

		return userClient.getUserById(userId);
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
