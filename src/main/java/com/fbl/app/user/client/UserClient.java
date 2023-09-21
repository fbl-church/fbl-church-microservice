/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.guardian.client.domain.Guardian;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.app.user.service.ManageUserService;
import com.fbl.app.user.service.UserService;
import com.fbl.common.annotations.interfaces.Client;
import com.fbl.common.enums.WebRole;

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
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @param id The user id to get applications for
	 * @return List of Applications
	 */
	public List<String> getUserAppsById(int id) {
		return userService.getUserAppsById(id);
	}

	/**
	 * Gets a list of user roles by user id.
	 * 
	 * @param id The user id to get roles for
	 * @return List of Web Roles
	 */
	public List<WebRole> getUserRolesById(int id) {
		return userService.getUserRolesById(id);
	}

	/**
	 * Create a new user. This is an account created by someone other the user
	 * accessing the account.
	 * 
	 * @param user The user object to be created.
	 * @return The new user that was created.
	 */
	public User createUser(User user) {
		return manageUserService.createUser(user);
	}

	/**
	 * Update the user's information such as email, first name, and last name
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 */
	public User updateUser(User user) {
		return manageUserService.updateUser(user);
	}

	/**
	 * Update the user's information such as email, first name, and last name by
	 * user id.
	 * 
	 * @param userId The id of the user to be updated
	 * @param user   what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 */
	public User updateUserById(int userId, User user) {
		return manageUserService.updateUserById(userId, user);
	}

	/**
	 * Updates the guardian information of a user. Will not update guardian user
	 * roles they currently have.
	 * 
	 * @param userId   The id of the user to be updated
	 * @param guardian what information on the guardian needs to be updated.
	 * @return user associated to that id with the updated information
	 */
	public User updateGuardianUserById(int userId, Guardian guardian) {
		User u = guardian;
		u.setWebRole(getUserRolesById(userId));
		return manageUserService.updateUserById(userId, u);
	}

	/**
	 * Updates the child information of a user. Will not update child user
	 * roles they currently have.
	 * 
	 * @param userId The id of the user to be updated
	 * @param child  what information on the child needs to be updated.
	 * @return user associated to that id with the updated information
	 */
	public User updateChildUserById(int userId, Child child) {
		User u = child;
		u.setWebRole(getUserRolesById(userId));
		return manageUserService.updateUserById(userId, u);
	}

	/**
	 * Update the user's information such as email, first name, and last name
	 * 
	 * @param id    The user id to be updated with the new roles
	 * @param roles List of roles to be updated
	 * @return The updated user information
	 */
	public User updateUserRoles(int id, List<WebRole> roles) {
		return manageUserService.updateUserRoles(id, roles);
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

	/**
	 * Delete user by id
	 * 
	 * @param userId The user Id to be deleted
	 */
	public void deleteUser(@PathVariable int userId) {
		manageUserService.deleteUser(userId);
	}
}
