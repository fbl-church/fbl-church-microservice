/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
	private UserService userProfileService;

	@Autowired
	private ManageUserService manageUserProfileService;

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
		return userProfileService.getUsers(request);
	}

	/**
	 * Gets the current logged in user information.
	 * 
	 * @return The user currently logged in.
	 */
	@Operation(summary = "Gets current user of the session call.", description = "Will return the current user based on the active session jwt holder.")
	@GetMapping(path = "/current-user", produces = APPLICATION_JSON_VALUE)
	public User getCurrentUser() {
		return userProfileService.getCurrentUser();
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
		return userProfileService.getUserById(id);
	}

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 */
	@PutMapping(path = "/{id}/last-login", produces = APPLICATION_JSON_VALUE)
	public User updateUserLastLoginToNow(@PathVariable int id) {
		return manageUserProfileService.updateUserLastLoginToNow(id);
	}
}