/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.authentication.rest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.fbl.FBLChurchApplication;
import com.fbl.app.authentication.client.domain.AuthToken;
import com.fbl.app.authentication.client.domain.request.AuthenticationRequest;
import com.fbl.app.authentication.service.AuthenticationService;
import com.fbl.common.annotations.interfaces.ControllerJwt;
import com.fbl.test.factory.abstracts.BaseControllerTest;
import com.fbl.test.factory.annotations.InsiteRestTest;

/**
 * Test class for the Authenticate Controller.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@ContextConfiguration(classes = FBLChurchApplication.class)
@InsiteRestTest
public class AuthenticationControllerTest extends BaseControllerTest {

    @MockBean
    private AuthenticationService service;

    @Test
    public void testAuthenticate() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("test@mail.com", "testPassword");
        when(service.authenticate(any(AuthenticationRequest.class))).thenReturn(new AuthToken());

        this.mockMvc
                .perform(post("/api/authenticate", request))
                .andExpect(status().isOk());
    }

    @Test
    @ControllerJwt
    public void testReAuthenticate() throws Exception {
        when(service.reauthenticate()).thenReturn(new AuthToken());
        this.mockMvc
                .perform(post("/api/reauthenticate"))
                .andExpect(status().isOk());
    }

    @Test
    public void testReAuthenticateNoToken() throws Exception {
        when(service.reauthenticate()).thenReturn(new AuthToken());
        this.mockMvc
                .perform(post("/api/reauthenticate"))
                .andExpect(status().isUnauthorized());
    }
}
