package com.awana.app.user.rest;

import static org.springframework.http.MediaType.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awana.app.user.client.domain.User;
import com.awana.app.user.client.domain.WebRole;
import com.awana.app.user.client.domain.request.UserGetRequest;
import com.awana.app.user.openapi.TagUser;
import com.awana.app.user.service.UserProfileService;
import com.awana.common.annotations.interfaces.HasAccess;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/user-app/profile")
@RestController
@TagUser
public class UserProfileController {

	@Autowired
	private UserProfileService userProfileService;

	/**
	 * Gets a list of users based of the request filter
	 * 
	 * @param request to filter on
	 * @return list of user objects
	 * @throws Exception
	 */
	@Operation(summary = "Get a list of users.", description = "Given a User Get Request, it will return a list of users that match the request.")
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public List<User> getUsers(UserGetRequest request) throws Exception {
		return userProfileService.getUsers(request);
	}

	/**
	 * Gets the current logged in user information.
	 * 
	 * @return The user currently logged in.
	 * @throws Exception
	 */
	@Operation(summary = "Gets current user of the session call.", description = "Will return the current user based on the active session jwt holder.")
	@GetMapping(path = "/current-user", produces = APPLICATION_JSON_VALUE)
	public User getCurrentUser() throws Exception {
		return userProfileService.getCurrentUser();
	}

	/**
	 * Get user object for the given Id
	 * 
	 * @param id of the user
	 * @return user associated to that id
	 * @throws Exception
	 */
	@Operation(summary = "Gets a user by id.", description = "For the given id value, it will return the corresponding user.")
	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	@HasAccess(WebRole.ADMIN)
	public User getUserById(@PathVariable int id) throws Exception {
		return userProfileService.getUserById(id);
	}
}