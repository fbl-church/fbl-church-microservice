/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.app.user.openapi.TagUser;
import com.fbl.app.user.service.ManageUserService;
import com.fbl.app.user.service.UserService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RequestMapping("/api/users")
@RestController
@RestApiController

@TagUser
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ManageUserService manageUserService;

	/**
	 * Gets a list of users based of the request filter
	 * 
	 * @param request to filter on
	 * @return list of user objects
	 */
	@Operation(summary = "Get a list of users.", description = "Given a User Get Request, it will return a list of users that match the request.")
	@GetMapping
	public Page<User> getUsers(UserGetRequest request) {
		return userService.getUsers(request);
	}

	/**
	 * Gets the current logged in user information.
	 * 
	 * @return The user currently logged in.
	 */
	@Operation(summary = "Gets current user of the session call.", description = "Will return the current user based on the active session jwt holder.")
	@GetMapping("/current-user")
	public User getCurrentUser() {
		return userService.getCurrentUser();
	}

	/**
	 * Get user object for the given Id
	 * 
	 * @param id of the user
	 * @return user associated to that id
	 */
	@Operation(summary = "Gets a user by id.", description = "For the given id value, it will return the corresponding user.")
	@GetMapping("/{id}")
	public User getUserById(@PathVariable int id) {
		return userService.getUserById(id);
	}

	/**
	 * End point to a get a list of users apps that they have access too
	 * 
	 * @param id The user id to get applications for
	 * @return List of Applications
	 */
	@Operation(summary = "Get a list of user applications", description = "Returns the given apps that the user has access too.")
	@GetMapping("/{id}/applications")
	@HasAccess(WebRole.SITE_ADMINISTRATOR)
	public List<String> getUserAppsById(@PathVariable int id) {
		return userService.getUserAppsById(id);
	}

	/**
	 * Create a new user. This is an account created by someone other the user
	 * accessing the account.
	 * 
	 * @param user The user object to be created.
	 * @return The new user that was created.
	 */
	@Operation(summary = "Add a new user", description = "Add a new user. Called for someone creating an account for someone else.")
	@PostMapping("/create")
	@HasAccess(WebRole.SITE_ADMINISTRATOR)
	public User createUser(@RequestBody @Valid User user) {
		return manageUserService.createUser(user, true);
	}

	/**
	 * Register a new user account. Gets called when a user is creating an account
	 * for themselves.
	 * 
	 * @param user The user object to be created.
	 * @return The new user that was created.
	 */
	@Operation(summary = "Register user", description = "Registering a user will create an account for the requesting user.")
	@PostMapping("/register")
	public User registerUser(@RequestBody @Valid User user) {
		return manageUserService.registerUser(user);
	}

	/**
	 * Update the user's information such as email, first name, and last name
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 */
	@Operation(summary = "Update User Information", description = "Will update the given user information.")
	@PutMapping
	public User updateUser(@RequestBody User user) {
		return manageUserService.updateUser(user);
	}

	/**
	 * Update the user's information such as email, first name, and last name
	 * 
	 * @param id    The user id to be updated with the new roles
	 * @param roles List of roles to be updated
	 * @return The updated user information
	 */
	@Operation(summary = "Update User Roles", description = "Will update the given user roles by id.")
	@PutMapping("/{id}/roles")
	public User updateUserRoles(@PathVariable int id, @RequestBody List<WebRole> roles) {
		return manageUserService.updateUserRoles(id, roles);
	}

	/**
	 * Add a web role to a list of users
	 * 
	 * @param webRole The web role to add to the users
	 * @param userIds The list of user ids to udpate
	 * @return The list of users that were updated
	 */
	@Operation(summary = "Add Web Role to User List", description = "Will add the given web role to the list of users.")
	@PutMapping("/roles/{webRole}")
	public List<User> addWebRoleToUsers(@PathVariable WebRole webRole, @RequestBody List<Integer> userIds) {
		return manageUserService.addWebRoleToUsers(webRole, userIds);
	}

	/**
	 * Updates a user for the given id.
	 * 
	 * @param id   of the user to update
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 */
	@Operation(summary = "Update User Information By Id", description = "Will update the given user information for the provided user id.")
	@PutMapping("/{id}")
	@HasAccess(WebRole.SITE_ADMINISTRATOR)
	public User updateUserById(@PathVariable int id, @RequestBody User user) {
		return manageUserService.updateUserById(id, user);
	}

	/**
	 * Delete user by id
	 * 
	 * @param userId The user Id to be deleted
	 */
	@Operation(summary = "Delete a user", description = "Delete a user for the given id.")
	@DeleteMapping("/{userId}")
	@HasAccess(WebRole.SITE_ADMINISTRATOR)
	public void deleteUser(@PathVariable int userId) {
		manageUserService.deleteUser(userId);
	}
}