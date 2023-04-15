package com.awana.app.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.awana.app.user.client.domain.User;
import com.awana.app.user.client.domain.request.UserGetRequest;
import com.awana.app.user.dao.UserProfileDAO;
import com.awana.common.exception.NotFoundException;
import com.awana.common.jwt.utility.JwtHolder;
import com.awana.common.page.Page;
import com.awana.test.factory.annotations.AwanaServiceTest;
import com.awana.test.factory.data.UserFactoryData;

/**
 * Test class for the User Profile Service.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@AwanaServiceTest
public class UserProfileServiceTest {

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private UserProfileDAO userProfileDAO;

    @InjectMocks
    private UserProfileService service;

    @Test
    public void testGetUsers() {
        User user1 = UserFactoryData.userData();
        User user2 = new User();
        user2.setId(2);

        when(userProfileDAO.getUsers(any(UserGetRequest.class)))
                .thenReturn(new Page<User>(2, Arrays.asList(user1, user2)));

        List<User> returnedUser = service.getUsers(new UserGetRequest()).getList();

        verify(userProfileDAO).getUsers(any(UserGetRequest.class));
        assertEquals(user1.getId(), returnedUser.get(0).getId(), "User 1 id should be 12");
        assertEquals(user2.getId(), returnedUser.get(1).getId(), "User 2 id should be 2");
    }

    @Test
    public void testGetCurrentUser() throws Exception {
        User user = UserFactoryData.userData();
        when(jwtHolder.getUserId()).thenReturn(12);
        when(userProfileDAO.getUserById(anyInt())).thenReturn(user);

        User returnedUser = service.getCurrentUser();

        verify(jwtHolder).getUserId();
        verify(userProfileDAO).getUserById(eq(12));
        assertEquals(user.getId(), returnedUser.getId(), "User id should be 12");
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = UserFactoryData.userData();
        when(userProfileDAO.getUserById(anyInt())).thenReturn(user);

        User returnedUser = service.getUserById(12);

        verify(userProfileDAO).getUserById(eq(12));
        assertEquals(user.getId(), returnedUser.getId(), "User id should be 12");
    }

    @Test
    public void testGetUserByIdInvalidUserId() throws Exception {
        when(userProfileDAO.getUserById(anyInt())).thenThrow(new NotFoundException("User", 100));

        assertThrows(NotFoundException.class, () -> service.getUserById(100));
        verify(userProfileDAO).getUserById(eq(100));
    }
}
