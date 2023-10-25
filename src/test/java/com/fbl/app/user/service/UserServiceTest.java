/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.app.user.dao.UserDAO;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;
import com.fbl.exception.types.NotFoundException;
import com.fbl.jwt.utility.JwtHolder;
import com.fbl.test.factory.annotations.InsiteServiceTest;
import com.fbl.test.factory.data.UserFactoryData;
import com.google.common.collect.Sets;

/**
 * Test class for the User Service.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@InsiteServiceTest
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private UserDAO userDAO;

    @Captor
    private ArgumentCaptor<UserGetRequest> userGetRequestCaptor;

    @Captor
    private ArgumentCaptor<List<WebRole>> webroleListCaptor;

    @Test
    public void testGetUsers() {
        User user1 = UserFactoryData.userData();
        User user2 = new User();
        user2.setId(2);

        when(userDAO.getUsers(any(UserGetRequest.class))).thenReturn(Page.of(2, Arrays.asList(user1, user2)));

        List<User> returnedUser = service.getUsers(new UserGetRequest()).getList();

        verify(userDAO).getUsers(any(UserGetRequest.class));
        assertEquals(user1.getId(), returnedUser.get(0).getId(), "User 1 id should be 12");
        assertEquals(user2.getId(), returnedUser.get(1).getId(), "User 2 id should be 2");
    }

    @Test
    public void testGetCurrentUser() {
        when(jwtHolder.getUserId()).thenReturn(100);
        when(userDAO.getUsers(any(UserGetRequest.class))).thenReturn(Page.of(List.of(new User())));

        User currentUser = service.getCurrentUser();

        assertNotNull(currentUser, "Current user not null");
        verify(userDAO).getUsers(userGetRequestCaptor.capture());
        verify(userDAO).getUserRolesById(eq(100), webroleListCaptor.capture());
        assertEquals(Sets.newHashSet(100),userGetRequestCaptor.getValue().getId(), "User id");
        assertEquals(List.of(WebRole.USER), webroleListCaptor.getValue(), "Web Role Exclude");
    }

    @Test
    public void testGetUserById() {
        when(userDAO.getUsers(any(UserGetRequest.class))).thenReturn(Page.of(List.of(new User())));

        User user = service.getUserById(10);

        assertNotNull(user, "User not null");
        verify(userDAO).getUsers(userGetRequestCaptor.capture());
        verify(userDAO).getUserRolesById(eq(10), webroleListCaptor.capture());
        assertEquals(Sets.newHashSet(10),userGetRequestCaptor.getValue().getId(), "User id");
        assertEquals(List.of(WebRole.USER), webroleListCaptor.getValue(), "Web Role Exclude");
    }

    @Test
    public void testGetUserByIdNotFound() {
        when(userDAO.getUsers(any(UserGetRequest.class))).thenReturn(Page.of(Collections.emptyList()));

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getUserById(10));

        verify(userDAO).getUsers(userGetRequestCaptor.capture());
        assertEquals("User not found for id: '10'", ex.getMessage(), "Exception Message");
    }

    @Test
    public void testGetUserAppsById() {
        when(userDAO.getUserApps(anyInt())).thenReturn(List.of("Users"));

        List<String> userApps = service.getUserAppsById(10);

        verify(userDAO).getUserApps(10);
        assertEquals(List.of("Users"), userApps, "App List Matches");
    }

    @Test
    public void testGetUserRolesById() {
        when(userDAO.getUserRolesById(anyInt(), any())).thenReturn(List.of(WebRole.AWANA_LEADER));

        List<WebRole> roles = service.getUserRolesById(10);

        verify(userDAO).getUserRolesById(10, null);
        assertEquals(List.of(WebRole.AWANA_LEADER), roles, "Roles match");
    }

}
