package com.awana.app.subscription.stomp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.server.ServerHttpRequest;

import com.awana.app.subscription.client.domain.UserPrincipal;
import com.awana.app.user.client.domain.User;
import com.awana.app.user.client.domain.WebRole;
import com.awana.common.jwt.utility.JwtHolder;
import com.awana.test.factory.annotations.AwanaServiceTest;

/**
 * Test class for the Subscription Handshake Handler.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@AwanaServiceTest
public class SubscriptionHandshakeHandlerTest {

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private ServerHttpRequest request;

    @InjectMocks
    private SubscriptionHandshakeHandler handler;

    @Test
    public void testDetermineUser() throws URISyntaxException {
        User currentUser = new User();
        currentUser.setId(12);
        currentUser.setWebRole(WebRole.DEVELOPER);

        when(jwtHolder.getUser()).thenReturn(currentUser);

        UserPrincipal u = (UserPrincipal) handler.determineUser(request, null, null);

        assertNotNull(u.getName(), "Random UUID");
        assertEquals(12, u.getUser().getId(), "User id");
        assertEquals(WebRole.DEVELOPER, u.getUser().getWebRole(), "User Role");
    }
}
