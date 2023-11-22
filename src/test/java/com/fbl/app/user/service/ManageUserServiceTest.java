/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
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
import com.fbl.app.user.dao.UserDAO;
import com.fbl.common.enums.AccountStatus;
import com.fbl.common.enums.WebRole;
import com.fbl.jwt.utility.JwtHolder;
import com.fbl.test.factory.annotations.InsiteServiceTest;
import com.fbl.test.factory.data.UserFactoryData;

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

    @Test
    void testCreateUser_whenCalled_willCreateTheNewUserAndSendEmail() {
        when(jwtHolder.getWebRole()).thenReturn(List.of(WebRole.ADMINISTRATOR));
        when(dao.insertUser(any(User.class))).thenReturn(12);
        when(userClient.getUserById(anyInt())).thenReturn(UserFactoryData.userData());

        User createdUser = service.createUser(UserFactoryData.userData(), true);

        verify(dao).insertUser(any(User.class));
        verify(dao).deleteUserRoles(12);
        verify(dao, times(2)).insertUserRole(eq(12), webRoleCaptor.capture());
        verify(userCredentialsClient).insertUserPassword(eq(12), anyString());
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
}
