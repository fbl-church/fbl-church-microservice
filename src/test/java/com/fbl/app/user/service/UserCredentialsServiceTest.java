/*
 * Copyright of FBL Church App. All rights reserved.
*/
package com.fbl.app.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fbl.app.authentication.client.AuthenticationClient;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.dao.UserCredentialsDAO;
import com.fbl.jwt.utility.JwtHolder;
import com.fbl.test.factory.annotations.InsiteServiceTest;

/**
 * User Credentials Service Test
 *
 * @author Sam Butler
 * @since Jan 31, 2024 01
 */
@InsiteServiceTest
public class UserCredentialsServiceTest {
    @InjectMocks
    private UserCredentialsService service;

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private UserCredentialsDAO dao;

    @Mock
    private UserClient userClient;

    @Mock
    private AuthenticationClient authClient;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Test
    void testInsertUserPassword_whenCalledWithValidInfo_insertsUserPassword() {
        service.insertUserPassword(1, "testPassword!");
        verify(dao).insertUserPassword(eq(1), stringCaptor.capture());
        assertEquals(60, stringCaptor.getValue().length(), "Password Hashed");
    }

    @ParameterizedTest(name = "[{index}] pass = \"{0}\"")
    @ValueSource(strings = { "", "   " })
    @NullSource
    void testInsertUserPassword_whenCalledInvalid_throwsException(String pass) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.insertUserPassword(1, pass));
        verify(dao, never()).insertUserPassword(anyInt(), anyString());
        assertEquals("Password must not be null or empty", ex.getMessage(), "Exception Message");
    }
}
