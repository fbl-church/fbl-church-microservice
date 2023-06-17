/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.subscription.stomp;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.server.ServerHttpRequest;

import com.fbl.jwt.utility.JwtHolder;
import com.fbl.test.factory.annotations.InsiteServiceTest;

/**
 * Test class for the Subscription Handshake Handler.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@InsiteServiceTest
public class SubscriptionHandshakeHandlerTest {

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private ServerHttpRequest request;

    @InjectMocks
    private SubscriptionHandshakeHandler handler;

}
