package com.awana.app.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awana.app.user.client.domain.User;
import com.awana.app.user.client.domain.request.UserGetRequest;
import com.awana.app.user.dao.UserProfileDAO;
import com.awana.common.jwt.utility.JwtHolder;

/**
 * User Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Service
public class UserProfileService {

	@Autowired
	private JwtHolder jwtHolder;

	@Autowired
	private UserProfileDAO dao;

	/**
	 * Get users based on given request filter
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 * @throws Exception
	 */
	public List<User> getUsers(UserGetRequest request) {
		List<User> users = dao.getUsers(request);
		return users;
	}

	/**
	 * Get the current user from the jwt token.
	 * 
	 * @return User profile object {@link User}
	 * @throws Exception
	 */
	public User getCurrentUser() throws Exception {
		return getUserById(jwtHolder.getUserId());
	}

	/**
	 * Service to get a users profile given the user id
	 * 
	 * @param id of the user
	 * @return User profile object {@link User}
	 * @throws Exception
	 */
	public User getUserById(int id) throws Exception {
		return dao.getUserById(id);
	}
}
