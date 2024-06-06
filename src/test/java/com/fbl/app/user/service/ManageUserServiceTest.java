/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
import com.fbl.exception.types.InsufficientPermissionsException;
import com.fbl.exception.types.ServiceException;
import com.fbl.jwt.utility.JwtHolder;
import com.fbl.test.factory.annotations.InsiteServiceTest;
import com.fbl.test.factory.config.LoggerListener;
import com.fbl.test.factory.config.domain.LogMessage;
import com.fbl.test.factory.data.UserFactoryData;

import ch.qos.logback.classic.Level;

/**
 * Test class for the Manage User Service.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@InsiteServiceTest
public class ManageUserServiceTest {

    @InjectMocks
    private ManageUserService service;

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private UserDAO dao;

    @Mock
    private UserClient userClient;

    @Mock
    private UserCredentialsClient userCredentialsClient;

    @Mock
    private UserStatusClient userStatusClient;

    @Mock
    private EmailClient emailClient;

    @Captor
    private ArgumentCaptor<WebRole> webRoleCaptor;

    @Captor
    private ArgumentCaptor<UserStatus> userStatusCaptor;

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    void testCreateUser_whenCalled_willCreateTheNewUser(boolean sendEmail) {
        when(jwtHolder.getWebRole()).thenReturn(List.of(WebRole.ADMINISTRATOR));
        when(dao.insertUser(any(User.class))).thenReturn(12);
        when(userClient.getUserById(anyInt())).thenReturn(UserFactoryData.userData());

        User createdUser = service.createUser(UserFactoryData.userData(), true, true);

        verify(dao).insertUser(any(User.class));
        verify(dao).deleteUserRoles(12);
        verify(dao, times(2)).insertUserRole(eq(12), webRoleCaptor.capture());
        verify(userCredentialsClient).insertUserPassword(eq(12), argThat(s -> s.toString().length() == 32));
        verify(userStatusClient).insertUserStatus(userStatusCaptor.capture());
        verify(userClient).getUserById(12);
        verify(emailClient).sendNewUserEmail(any(User.class));

        assertNotNull(createdUser, "Created User is not null");
        assertEquals(12, createdUser.getId(), "User Id");

        UserStatus userStatus = userStatusCaptor.getValue();
        assertEquals(12, userStatus.getUserId(), "User Id");
        assertEquals(AccountStatus.ACTIVE, userStatus.getAccountStatus(), "Account Status");
        assertTrue(userStatus.getAppAccess(), "App Access");
        assertNull(userStatus.getUpdatedUserId(), "Updated User Id");
        assertTrue(webRoleCaptor.getAllValues().containsAll(List.of(WebRole.SITE_ADMINISTRATOR, WebRole.USER)),
                "User Roles");

    }

    @Test
    void testCreateUser_whenCalledWithSendEmailFalse_willCreateTheNewUserAndNotSendEmail() {
        when(jwtHolder.getWebRole()).thenReturn(List.of(WebRole.ADMINISTRATOR));
        when(dao.insertUser(any(User.class))).thenReturn(12);
        when(userClient.getUserById(anyInt())).thenReturn(UserFactoryData.userData());

        User createdUser = service.createUser(UserFactoryData.userData(), true, false);

        verify(dao).insertUser(any(User.class));
        verify(dao).deleteUserRoles(12);
        verify(dao, times(2)).insertUserRole(eq(12), webRoleCaptor.capture());
        verify(userCredentialsClient).insertUserPassword(eq(12), anyString());
        verify(userStatusClient).insertUserStatus(userStatusCaptor.capture());
        verify(userClient).getUserById(12);
        verify(emailClient, never()).sendNewUserEmail(any());

        assertNotNull(createdUser, "Created User is not null");
        assertEquals(12, createdUser.getId(), "User Id");

        UserStatus userStatus = userStatusCaptor.getValue();
        assertEquals(12, userStatus.getUserId(), "User Id");
        assertEquals(AccountStatus.ACTIVE, userStatus.getAccountStatus(), "Account Status");
        assertTrue(userStatus.getAppAccess(), "App Access");
        assertNull(userStatus.getUpdatedUserId(), "Updated User Id");
        assertTrue(webRoleCaptor.getAllValues().containsAll(List.of(WebRole.SITE_ADMINISTRATOR, WebRole.USER)),
                "User Roles");
    }

    @Test
    void testCreateUser_whenCalledWithInsufficientPermissions_willNotCreateTheUser() {
        when(jwtHolder.getUserId()).thenReturn(10);
        when(jwtHolder.getWebRole()).thenReturn(List.of(WebRole.USER));

        InsufficientPermissionsException e = assertThrows(InsufficientPermissionsException.class,
                () -> service.createUser(UserFactoryData.userData(), true, false));

        assertEquals("Insufficient permission for user '10' to create a user of role 'SITE_ADMINISTRATOR'",
                e.getMessage(), "Exception Message");
    }

    @Test
    void testRegisterUser_whenCalled_willCreateTheUserWithPendingAccountAndSendEmail() {
        when(dao.insertUser(any(User.class))).thenReturn(12);
        when(userClient.getUserById(anyInt())).thenReturn(UserFactoryData.userData());

        User registeredUser = service.registerUser(UserFactoryData.userData());

        verify(dao).insertUser(any(User.class));
        verify(dao).deleteUserRoles(12);
        verify(dao, times(2)).insertUserRole(eq(12), webRoleCaptor.capture());
        verify(userCredentialsClient).insertUserPassword(eq(12), argThat(s -> s.toString().length() == 32));
        verify(userStatusClient).insertUserStatus(userStatusCaptor.capture());
        verify(userClient).getUserById(12);
        verify(emailClient).sendNewUserEmail(any(User.class));

        assertNotNull(registeredUser, "Created User is not null");
        assertEquals(12, registeredUser.getId(), "User Id");

        UserStatus userStatus = userStatusCaptor.getValue();
        assertEquals(12, userStatus.getUserId(), "User Id");
        assertEquals(AccountStatus.PENDING, userStatus.getAccountStatus(), "Account Status");
        assertFalse(userStatus.getAppAccess(), "App Access");
        assertTrue(webRoleCaptor.getAllValues().containsAll(List.of(WebRole.SITE_ADMINISTRATOR, WebRole.USER)),
                "User Roles");
    }

    @Test
    void testUpdateUser_whenCalled_willUpdateTheUserInformation() {
        when(jwtHolder.getUserId()).thenReturn(12);
        when(userClient.getUserById(anyInt())).thenReturn(UserFactoryData.userData());

        User updatedUser = service.updateUser(UserFactoryData.userData());

        verify(dao).updateUser(eq(12), any(User.class));
        verify(dao, never()).deleteUserRoles(anyInt());
        verify(dao, never()).insertUserRole(anyInt(), any());
        verify(userClient).getUserById(12);

        assertNotNull(updatedUser, "Created User is not null");
        assertEquals(12, updatedUser.getId(), "User Id");
    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    void testUpdateUserById_whenCalled_willUpdateTheUserByIdAndTheirRoles(boolean roleUpdate) {
        when(jwtHolder.getWebRole()).thenReturn(List.of(WebRole.ADMINISTRATOR));
        when(userClient.getUserById(anyInt())).thenReturn(UserFactoryData.userData());

        User updatedUser = service.updateUserById(12, UserFactoryData.userData(), true, roleUpdate);

        verify(dao).updateUser(eq(12), any(User.class));
        verify(dao, roleUpdate ? times(1) : never()).deleteUserRoles(12);
        verify(dao, roleUpdate ? times(2) : never()).insertUserRole(eq(12), webRoleCaptor.capture());
        verify(userClient, times(2)).getUserById(12);

        assertNotNull(updatedUser, "Created User is not null");
        assertEquals(12, updatedUser.getId(), "User Id");

        if (roleUpdate) {
            assertTrue(webRoleCaptor.getAllValues().containsAll(List.of(WebRole.SITE_ADMINISTRATOR, WebRole.USER)),
                    "User Roles");
        }
    }

    @Test
    void testUpdateUserById_whenCalledWithINsufficentRole_willThrowException() {
        when(jwtHolder.getUserId()).thenReturn(13);
        when(jwtHolder.getWebRole()).thenReturn(List.of(WebRole.USER));
        when(userClient.getUserById(anyInt())).thenReturn(UserFactoryData.userData());

        InsufficientPermissionsException e = assertThrows(InsufficientPermissionsException.class,
                () -> service.updateUserById(12, UserFactoryData.userData(), true, false));

        assertEquals("Insufficient permission for user '13' to update a user of role 'SITE_ADMINISTRATOR'",
                e.getMessage(), "Exception Message");
    }

    @Test
    void testUpdateUserLastLoginToNow_whenCalled_willUpdateUserLastLogin() {
        when(userClient.getUserById(anyInt())).thenReturn(UserFactoryData.userData());

        User updatedUser = service.updateUserLastLoginToNow(12);

        verify(dao).updateUserLastLoginToNow(12);
        verify(userClient).getUserById(12);
        assertNotNull(updatedUser, "Updated User not Null");
    }

    @Test
    void testUpdateUserRoles_whenCalled_willUpdateUserRoles() {
        when(userClient.getUserById(anyInt())).thenReturn(UserFactoryData.userData());

        User updatedUser = service.updateUserRoles(12,
                new ArrayList<WebRole>(Arrays.asList(WebRole.SITE_ADMINISTRATOR, WebRole.AWANA_DIRECTOR)));

        verify(dao).deleteUserRoles(12);
        verify(dao, times(3)).insertUserRole(eq(12), webRoleCaptor.capture());
        verify(userClient).getUserById(12);

        assertNotNull(updatedUser, "Updated User not Null");
        assertEquals(List.of(WebRole.SITE_ADMINISTRATOR, WebRole.AWANA_DIRECTOR, WebRole.USER),
                webRoleCaptor.getAllValues(), "Roles Added");
    }

    @Test
    void testUpdateUserRoles_whenCalledWithNoRoles_willOnlyAddTheUserRoleToTheUser() {
        when(userClient.getUserById(anyInt())).thenReturn(UserFactoryData.userData());

        User updatedUser = service.updateUserRoles(12, null);

        verify(dao).deleteUserRoles(12);
        verify(dao).insertUserRole(eq(12), webRoleCaptor.capture());
        verify(userClient).getUserById(12);

        assertNotNull(updatedUser, "Updated User not Null");
        assertEquals(List.of(WebRole.USER), webRoleCaptor.getAllValues(), "Roles Added");
    }

    @Test
    void testAddWebRoleToUsers_whenCalled_willAddTheRoleToAllTheUsers() {
        when(userClient.getUsers(any(UserGetRequest.class))).thenReturn(UserFactoryData.userDataList());

        List<User> users = service.addWebRoleToUsers(WebRole.AWANA_WORKER, List.of(1, 2, 3));

        verify(userClient).getUsers(any(UserGetRequest.class));
        verify(dao).insertUserRole(1, WebRole.AWANA_WORKER);
        verify(dao).insertUserRole(2, WebRole.AWANA_WORKER);
        verify(dao).insertUserRole(3, WebRole.AWANA_WORKER);

        assertFalse(users.isEmpty(), "Not Empty");
        assertEquals(3, users.size(), "Size");
    }

    @Test
    void testAddWebRoleToUsers_whenCalledWithUserWithRole_willAddTheRoleToTheOtherUsersAndLogAWarningForTheOtherUser() {
        LoggerListener logListener = LoggerListener.start(ManageUserService.class);

        when(userClient.getUsers(any(UserGetRequest.class))).thenReturn(UserFactoryData.userDataList());
        doThrow(new ServiceException("Upload Error")).when(dao).insertUserRole(eq(1), any(WebRole.class));

        List<User> users = service.addWebRoleToUsers(WebRole.AWANA_WORKER, List.of(1, 2, 3));

        verify(userClient).getUsers(any(UserGetRequest.class));
        verify(dao).insertUserRole(2, WebRole.AWANA_WORKER);
        verify(dao).insertUserRole(3, WebRole.AWANA_WORKER);

        List<LogMessage> logs = logListener.getLogs();
        assertFalse(users.isEmpty(), "Not Empty");
        assertEquals(3, users.size(), "Size");
        assertEquals(Level.WARN, logs.get(0).getLevel(), "Level");
        assertEquals("User id '1' already has web role 'AWANA_WORKER'", logs.get(0).getMessage(), "Log Message");
    }

    @Test
    void testDeleteUser_whenCalled_willDeleteUser() {
        service.deleteUser(12);
        verify(userStatusClient).updateUserStatusByUserId(eq(12), userStatusCaptor.capture());

        assertEquals(AccountStatus.INACTIVE, userStatusCaptor.getValue().getAccountStatus(), "Account Status");
        assertFalse(userStatusCaptor.getValue().getAppAccess(), "App Access");
    }
}
