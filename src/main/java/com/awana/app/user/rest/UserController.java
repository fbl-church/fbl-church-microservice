/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.rest;

import static org.springframework.http.MediaType.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awana.app.user.client.domain.User;
import com.awana.app.user.client.domain.request.UserGetRequest;
import com.awana.app.user.openapi.TagUser;
import com.awana.app.user.service.ManageUserService;
import com.awana.app.user.service.UserService;
import com.awana.common.annotations.interfaces.HasAccess;
import com.awana.common.enums.WebRole;
import com.awana.common.page.Page;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/users")
@RestController
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
	 * @throws Exception
	 */
	@Operation(summary = "Get a list of users.", description = "Given a User Get Request, it will return a list of users that match the request.")
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public Page<User> getUsers(UserGetRequest request) {
		return userService.getUsers(request);
	}

	/**
	 * Gets the current logged in user information.
	 * 
	 * @return The user currently logged in.
	 */
	@Operation(summary = "Gets current user of the session call.", description = "Will return the current user based on the active session jwt holder.")
	@GetMapping(path = "/current-user", produces = APPLICATION_JSON_VALUE)
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
	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	@HasAccess(WebRole.ADMIN)
	public User getUserById(@PathVariable int id) {
		return userService.getUserById(id);
	}

	/**
	 * Create a new user. This is an account created by someone other the user
	 * accessing the account.
	 * 
	 * @param user The user object to be created.
	 * @return The new user that was created.
	 */
	@Operation(summary = "Add a new user", description = "Add a new user. Called for someone creating an account for someone else.")
	@PostMapping(path = "/create", produces = APPLICATION_JSON_VALUE)
	@HasAccess(WebRole.SITE_ADMIN)
	public User createUser(@RequestBody @Valid User user) {
		return manageUserService.createUser(user);
	}

	/**
	 * Register a new user account. Gets called when a user is creating an account
	 * for themselves.
	 * 
	 * @param user The user object to be created.
	 * @return The new user that was created.
	 */
	@Operation(summary = "Register user", description = "Registering a user will create an account for the requesting user.")
	@PostMapping(path = "/register", produces = APPLICATION_JSON_VALUE)
	public User registerUser(@RequestBody @Valid User user) {
		return manageUserService.registerUser(user);
	}

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 */
	@PutMapping(path = "/{id}/last-login", produces = APPLICATION_JSON_VALUE)
	public User updateUserLastLoginToNow(@PathVariable int id) {
		return manageUserService.updateUserLastLoginToNow(id);
	}
}