/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;

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
@InsiteRestTest
@ControllerJwt
public class UsersControllerTest extends BaseControllerTest {

    private static final String USERS_PATH = "/api/users";

    @MockBean
    private UserService service;

    @Captor
    private ArgumentCaptor<UserGetRequest> getUsersCaptor;

    @Test
    public void testGetListOfUsers() throws Exception {
        when(service.getUsers(any(UserGetRequest.class))).thenReturn(Page.of(1, Arrays.asList(new User())));
        this.mockMvc.perform(get(USERS_PATH)).andExpect(status().isOk())
                .andExpect(header().longValue("total-count", 1));
        verify(service).getUsers(any(UserGetRequest.class));
    }

    @Test
    public void testGetListOfUsersWithRequestParams() throws Exception {
        when(service.getUsers(any(UserGetRequest.class))).thenReturn(Page.of(1, Arrays.asList(new User())));
        this.mockMvc.perform(get(USERS_PATH).param("firstName", "test").param("id", "1,2"))
                .andExpect(status().isOk())
                .andExpect(header().longValue("total-count", 1));
        verify(service).getUsers(getUsersCaptor.capture());

        UserGetRequest params = getUsersCaptor.getValue();
        assertEquals(Sets.newHashSet("test"), params.getFirstName(), "First Name");
        assertEquals(Sets.newHashSet(1, 2), params.getId(), "ID");
    }

    @Test
    public void testGetCurrentUser() throws Exception {
        when(service.getCurrentUser()).thenReturn(new User());
        this.mockMvc.perform(get(USERS_PATH + "/current-user")).andExpect(status().isOk());
        verify(service).getCurrentUser();
    }

    @Test
    public void testGetUserById() throws Exception {
        when(service.getUserById(anyInt())).thenReturn(new User());
        this.mockMvc.perform(get(USERS_PATH + "/3")).andExpect(status().isOk());
        verify(service).getUserById(3);
    }
}
