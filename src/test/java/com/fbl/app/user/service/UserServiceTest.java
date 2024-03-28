/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public void testGetUsers_whenCalledWithRequest_returnsListOfUsers() {
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
    public void testGetCurrentUser_whenCalled_returnsTheCurrentUser() {
        when(jwtHolder.getUserId()).thenReturn(100);
        when(userDAO.getUserById(anyInt())).thenReturn(Optional.of(new User()));

        User currentUser = service.getCurrentUser();

        assertNotNull(currentUser, "Current user not null");
        verify(userDAO).getUserById(100);
        verify(userDAO).getUserRolesById(eq(100), webroleListCaptor.capture());
        assertEquals(List.of(WebRole.USER), webroleListCaptor.getValue(), "Web Role Exclude");
    }

    @Test
    public void testGetUserById_whenCalledWithExistingUserId_returnsTheFoundUserId() {
        when(userDAO.getUserById(anyInt())).thenReturn(Optional.of(new User()));

        User user = service.getUserById(10);

        assertNotNull(user, "User not null");
        verify(userDAO).getUserById(10);
        verify(userDAO).getUserRolesById(eq(10), webroleListCaptor.capture());
        assertEquals(List.of(WebRole.USER), webroleListCaptor.getValue(), "Web Role Exclude");
    }

    @Test
    public void testGetUserById_whenCalledWithInvalidUserId_thenThrowsNotFoundException() {
        when(userDAO.getUserById(anyInt())).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getUserById(10));

        verify(userDAO).getUserById(10);
        assertEquals("User not found for id: '10'", ex.getMessage(), "Exception Message");
    }

    @Test
    public void testGetUserAppsById_whenCalledWithRealUserId_returnsTheUserAppsForThatId() {
        when(userDAO.getUserById(anyInt())).thenReturn(Optional.of(new User()));
        when(userDAO.getUserApps(anyInt())).thenReturn(List.of("Users"));

        List<String> userApps = service.getUserAppsById(10);

        verify(userDAO).getUserById(10);
        verify(userDAO).getUserApps(10);
        assertEquals(List.of("Users"), userApps, "App List Matches");
    }

    @Test
    public void testGetUserAppsById_whenCalledWithInvalidId_returnsTheUserAppsForThatId() {
        when(userDAO.getUserById(anyInt())).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getUserAppsById(10));

        verify(userDAO).getUserById(10);
        verify(userDAO, never()).getUserApps(anyInt());
        assertEquals("User not found for id: '10'", ex.getMessage(), "Exception Message");
    }

    @Test
    public void testGetUserRolesById_whenCalledWithRealUserId_returnsUserRoleForThatId() {
        when(userDAO.getUserById(anyInt())).thenReturn(Optional.of(new User()));
        when(userDAO.getUserRolesById(anyInt(), any())).thenReturn(List.of(WebRole.AWANA_LEADER));

        List<WebRole> roles = service.getUserRolesById(10, null);

        verify(userDAO).getUserRolesById(10, null);
        assertEquals(List.of(WebRole.AWANA_LEADER), roles, "Roles match");
    }

    @Test
    public void testGetUserRolesById_whenCalledWithInvalidId_returnsUserRoleForThatId() {
        when(userDAO.getUserById(anyInt())).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getUserRolesById(10, null));

        verify(userDAO).getUserById(10);
        verify(userDAO, never()).getUserRolesById(anyInt(), any());
        assertEquals("User not found for id: '10'", ex.getMessage(), "Exception Message");
    }

}
