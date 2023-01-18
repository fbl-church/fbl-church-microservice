package com.awana.app.authentication.rest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import com.awana.InsiteMicroserviceApplication;
import com.awana.app.authentication.client.domain.AuthToken;
import com.awana.app.authentication.client.domain.request.AuthenticationRequest;
import com.awana.app.authentication.service.AuthenticationService;
import com.awana.common.annotations.interfaces.ControllerJwt;
import com.awana.test.factory.abstracts.BaseControllerTest;
import com.awana.test.factory.annotations.AwanaRestTest;

/**
 * Test class for the Authenticate Controller.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@ContextConfiguration(classes = InsiteMicroserviceApplication.class)
@AwanaRestTest
public class AuthenticationControllerTest extends BaseControllerTest {

    @MockBean
    private AuthenticationService service;

    @Test
    public void testAuthenticate() throws Exception {
        when(service.authenticate(any(AuthenticationRequest.class))).thenReturn(new AuthToken());
        AuthenticationRequest request = new AuthenticationRequest("test@mail.com", "testPassword");
        check(post("/api/authenticate", request, AuthToken.class), serializedNonNull());
    }

    @Test
    @ControllerJwt
    public void testReAuthenticate() throws Exception {
        when(service.reauthenticate()).thenReturn(new AuthToken());
        check(post("/api/reauthenticate", AuthToken.class), serializedNonNull());
    }

    @Test
    public void testReAuthenticateNoToken() {
        check(post("/api/reauthenticate"), error(HttpStatus.UNAUTHORIZED, "Missing JWT Token."));
    }
}
