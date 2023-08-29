/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.authentication.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fbl.app.authentication.client.domain.AuthToken;
import com.fbl.app.authentication.client.domain.request.AuthenticationRequest;
import com.fbl.app.authentication.dao.AuthenticationDAO;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.exception.types.InvalidCredentialsException;
import com.fbl.exception.types.NotFoundException;
import com.fbl.jwt.utility.JwtHolder;
import com.fbl.jwt.utility.JwtTokenUtil;
import com.fbl.test.factory.annotations.InsiteServiceTest;

/**
 * Test class for the Authenticate Service.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@InsiteServiceTest
public class AuthenticationServiceTest {

    @Mock
    private AuthenticationDAO authenticationDAO;

    @Mock
    private UserClient userClient;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private JwtHolder jwtHolder;

    @InjectMocks
    private AuthenticationService service;

    @Test
    public void testAuthenticateUser() throws Exception {
        User userLoggingIn = new User();
        userLoggingIn.setId(1);

        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setEmail("fake@mail.com");
        authRequest.setPassword("testPassword");

        when(authenticationDAO.getUserAuthPassword(anyString()))
                .thenReturn(Optional.of("$2a$10$KusdNWjdceySzNAG3EH8a.5HuIOMWH4hl4Ke64Daqaeqivy1y0Rd."));
        when(userClient.getUsers(any(UserGetRequest.class))).thenReturn(Arrays.asList(userLoggingIn));
        when(userClient.updateUserLastLoginToNow(anyInt())).thenReturn(userLoggingIn);

        AuthToken authToken = service.authenticate(authRequest);

        verify(authenticationDAO).getUserAuthPassword(anyString());
        verify(userClient).getUsers(any(UserGetRequest.class));
        verify(jwtTokenUtil).generateToken(userLoggingIn);
        assertNotNull(authToken, "Auth Token is valid");
    }

    @Test
    public void testAuthenticateUserInvalidCredentials() throws Exception {
        User userLoggingIn = new User();
        userLoggingIn.setId(1);

        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setEmail("fake@mail.com");
        authRequest.setPassword("WrongPassword!");

        when(authenticationDAO.getUserAuthPassword(anyString()))
                .thenReturn(Optional.of("$2a$10$KusdNWjdceySzNAG3EH8a.5HuIOMWH4hl4Ke64Daqaeqivy1y0Rd."));

        InvalidCredentialsException e = assertThrows(InvalidCredentialsException.class,
                () -> service.authenticate(authRequest));

        assertEquals("Invalid Credentials for user email: 'fake@mail.com'", e.getMessage(), "Exception Message");
        verify(authenticationDAO).getUserAuthPassword(anyString());
        verify(userClient, never()).getUsers(any(UserGetRequest.class));
        verify(jwtTokenUtil, never()).generateToken(userLoggingIn);
    }

    @Test
    public void testReAuthenticateUser() throws Exception {
        User userLoggingIn = new User();
        userLoggingIn.setId(1);

        when(userClient.getUserById(anyInt())).thenReturn(userLoggingIn);
        when(jwtHolder.getUserId()).thenReturn(1);

        AuthToken authToken = service.reauthenticate();

        verify(authenticationDAO, never()).getUserAuthPassword(any());
        verify(userClient).getUserById(anyInt());
        verify(jwtTokenUtil).generateToken(userLoggingIn);
        assertNotNull(authToken, "Auth Token is valid");
    }

    @Test
    public void testReAuthenticateUserDoesNotExist() throws Exception {
        User userLoggingIn = new User();
        userLoggingIn.setId(1);

        when(userClient.getUserById(anyInt())).thenThrow(NotFoundException.class);
        when(jwtHolder.getUserId()).thenReturn(1);

        assertThrows(NotFoundException.class, () -> service.reauthenticate());
        verify(authenticationDAO, never()).getUserAuthPassword(any());
        verify(jwtTokenUtil, never()).generateToken(userLoggingIn);
        verify(userClient).getUserById(anyInt());
    }
}
