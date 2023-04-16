/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.subscription.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.security.Principal;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;

import com.awana.app.user.client.domain.User;
import com.awana.jwt.utility.JwtHolder;
import com.awana.subscription.client.domain.Notification;
import com.awana.subscription.client.domain.UserPrincipal;
import com.awana.subscription.notification.UserNotification;
import com.awana.test.factory.annotations.AwanaServiceTest;
import com.google.common.collect.Sets;

/**
 * Test class for the Subscription Service.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@AwanaServiceTest
public class SubscriptionServiceTest {

    @Mock
    private WebNotifierService webNotifierService;

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private SimpUserRegistry userRegistry;

    @InjectMocks
    private SubscriptionNotifierService service;

    @Captor
    private ArgumentCaptor<Notification> sendNotificationCaptor;

    @Captor
    private ArgumentCaptor<String> sessionCaptor;

    @BeforeEach
    public void setup() {
        SimpUser su = new SimpUser() {
            @Override
            public Principal getPrincipal() {
                User u = new User();
                u.setId(12);
                return new UserPrincipal("fake-uuid", u);
            }

            public String getName() {
                return null;
            }

            public boolean hasSessions() {
                return false;
            }

            public SimpSession getSession(String sessionId) {
                return null;
            }

            public Set<SimpSession> getSessions() {
                return null;
            }
        };
        lenient().when(userRegistry.getUsers()).thenReturn(Sets.newHashSet(su));
    }

    @Test
    public void testPushWithJustBody() {
        UserNotification userSub = new UserNotification();
        userSub.setName("Test User");
        userSub.setUserId(5);

        service.send(userSub);

        verify(webNotifierService).send(sendNotificationCaptor.capture());

        Notification body = sendNotificationCaptor.getValue();
        assertEquals(body.getClass(), UserNotification.class, "Should be UserNotification class");

        UserNotification u = (UserNotification) body;

        assertEquals(u.getName(), "Test User", "User Name");
        assertEquals(u.getUserId(), 5, "User Id");
        assertEquals("/topic/general/notification", u.getDestination(), "Notification Destination");
    }

    @Test
    public void testPushWithBodyAndNotificationAction() {
        service.sendToUser(new UserNotification(), 12);

        verify(webNotifierService).send(sendNotificationCaptor.capture(), sessionCaptor.capture());

        Notification body = sendNotificationCaptor.getValue();
        assertEquals(body.getClass(), UserNotification.class, "Should be UserSubscription class");
        assertEquals("/queue/user/notification", body.getDestination(), "Notification Destination");
        assertEquals("fake-uuid", sessionCaptor.getValue(), "Session UUID");
    }
}
