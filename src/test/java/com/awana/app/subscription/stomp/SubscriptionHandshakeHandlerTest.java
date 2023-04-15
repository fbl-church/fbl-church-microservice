package com.awana.app.subscription.stomp;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.server.ServerHttpRequest;

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

}
