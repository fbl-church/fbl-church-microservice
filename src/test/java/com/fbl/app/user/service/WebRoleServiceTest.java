/*
 * Copyright of FBL Church App. All rights reserved.
*/
package com.fbl.app.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fbl.app.user.client.domain.request.WebRoleGetRequest;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;
import com.fbl.jwt.utility.JwtHolder;
import com.fbl.test.factory.annotations.InsiteServiceTest;

/**
 * Web Role Service Test
 *
 * @author Sam Butler
 * @since Jan 31, 2024 01
 */
@InsiteServiceTest
public class WebRoleServiceTest {
    private final List<WebRole> FILTERED_ROLES = List.of(WebRole.LEADER, WebRole.WORKER, WebRole.USER,
            WebRole.GUARDIAN, WebRole.CHILD);

    @InjectMocks
    private WebRoleService service;

    @Mock
    private JwtHolder jwtHolder;

    @Test
    public void testGetRoles_whenCalledWithRequest_returnsPagedRoles() {
        when(jwtHolder.getWebRole()).thenReturn(List.of(WebRole.ADMINISTRATOR));

        Page<WebRole> roles = service.getRoles(WebRoleGetRequest.builder().rowOffset(0).pageSize(100).build());

        verify(jwtHolder).getWebRole();
        assertEquals(32, roles.size(), "Size of the page");
    }

    @Test
    public void testGetRoles_whenCalledWithNullRequest_returnsListOfAllRoles() {
        when(jwtHolder.getWebRole()).thenReturn(List.of(WebRole.ADMINISTRATOR));

        Page<WebRole> roles = service.getRoles(null);

        verify(jwtHolder).getWebRole();
        assertEquals(32, roles.size(), "Size of the page");
    }

    @Test
    public void testGetRoles_whenCalledWithRequest_returnsRolesBasedOnAccess() {
        when(jwtHolder.getWebRole()).thenReturn(List.of(WebRole.NURSERY_WORKER))
                .thenReturn(List.of(WebRole.NURSERY_SUPERVISOR)).thenReturn(List.of(WebRole.NURSERY_DIRECTOR));

        Page<WebRole> workerRoles = service.getRoles(WebRoleGetRequest.builder().rowOffset(0).pageSize(100).build());
        Page<WebRole> supervisorRoles = service
                .getRoles(WebRoleGetRequest.builder().rowOffset(0).pageSize(100).build());
        Page<WebRole> directorRoles = service.getRoles(WebRoleGetRequest.builder().rowOffset(0).pageSize(100).build());

        verify(jwtHolder, times(3)).getWebRole();
        assertEquals(17, workerRoles.size(), "Size of Worker Role Access");
        assertEquals(26, supervisorRoles.size(), "Size of Supervisor Role Access");
        assertEquals(29, directorRoles.size(), "Size of Directory Role Access");
    }

    @Test
    public void testGetRoles_whenCalled_returnsListOfFilteredOutFoods() {
        when(jwtHolder.getWebRole()).thenReturn(List.of(WebRole.ADMINISTRATOR));

        Page<WebRole> roles = service.getRoles(WebRoleGetRequest.builder().rowOffset(0).pageSize(100).build());

        verify(jwtHolder).getWebRole();
        assertFalse(roles.getList().stream().anyMatch(FILTERED_ROLES::contains),
                "Should not contain filtered roles");
    }

    @Test
    public void testGetRoles_whenCalledWithPagedRequest_returnsPagedRoles() {
        when(jwtHolder.getWebRole()).thenReturn(List.of(WebRole.ADMINISTRATOR));

        Page<WebRole> roles = service.getRoles(WebRoleGetRequest.builder().rowOffset(0).pageSize(10).build());

        verify(jwtHolder).getWebRole();
        assertEquals(10, roles.size(), "Size of the page");
    }

    @Test
    public void testGetRoles_whenCalledWithSearch_returnsFilteredRolesBySearch() {
        when(jwtHolder.getWebRole()).thenReturn(List.of(WebRole.ADMINISTRATOR));

        Page<WebRole> roles = service
                .getRoles(WebRoleGetRequest.builder().rowOffset(0).pageSize(100).search(Set.of("supervisor")).build());

        verify(jwtHolder).getWebRole();
        assertEquals(2, roles.size(), "Size of the page");
        assertEquals(roles.getList().get(0), WebRole.JUNIOR_CHURCH_SUPERVISOR);
        assertEquals(roles.getList().get(1), WebRole.NURSERY_SUPERVISOR);
    }
}
