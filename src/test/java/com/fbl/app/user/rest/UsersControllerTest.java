/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import com.fbl.FBLChurchApplication;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.app.user.service.UserService;
import com.fbl.common.annotations.interfaces.ControllerJwt;
import com.fbl.common.page.Page;
import com.fbl.test.factory.abstracts.BaseControllerTest;
import com.fbl.test.factory.annotations.InsiteRestTest;
import com.google.common.collect.Sets;

/**
 * Test class for the User Controller Test.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@ContextConfiguration(classes = FBLChurchApplication.class)
@InsiteRestTest
@ControllerJwt
public class UsersControllerTest extends BaseControllerTest {

    private static final String USERS_PATH = "/api/users";

    @MockBean
    private UserService service;

    @Captor
    private ArgumentCaptor<UserGetRequest> getUsersCaptor;

    ParameterizedTypeReference<Page<User>> pageRef = new ParameterizedTypeReference<Page<User>>() {
    };

    @Test
    public void testGetListOfUsers() throws Exception {
        when(service.getUsers(any(UserGetRequest.class))).thenReturn(new Page<User>(1, Arrays.asList(new User())));
        check(get(USERS_PATH, User[].class), serializedList(HttpStatus.OK));

        verify(service).getUsers(any(UserGetRequest.class));
    }

    @Test
    public void testGetListOfUsersWithRequestParams() throws Exception {
        when(service.getUsers(any(UserGetRequest.class))).thenReturn(new Page<User>(1, Arrays.asList(new User())));
        check(get(USERS_PATH + "?firstName=test&id=1,2", User[].class), serializedList(HttpStatus.OK));

        verify(service).getUsers(getUsersCaptor.capture());

        UserGetRequest params = getUsersCaptor.getValue();
        assertEquals(Sets.newHashSet("test"), params.getFirstName(), "First Name");
        assertEquals(Sets.newHashSet(1, 2), params.getId(), "ID");
    }

    @Test
    public void testGetCurrentUser() throws Exception {
        when(service.getCurrentUser()).thenReturn(new User());
        check(get(USERS_PATH + "/current-user", User.class), serializedNonNull(HttpStatus.OK));

        verify(service).getCurrentUser();
    }

    @Test
    public void testGetUserById() throws Exception {
        when(service.getUserById(anyInt())).thenReturn(new User());
        check(get(USERS_PATH + "/3", User.class), serializedNonNull(HttpStatus.OK));

        verify(service).getUserById(3);
    }
}
