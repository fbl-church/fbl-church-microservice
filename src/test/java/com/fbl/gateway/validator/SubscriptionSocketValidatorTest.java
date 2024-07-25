package com.fbl.gateway.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fbl.common.enums.Environment;
import com.fbl.environment.EnvironmentService;
import com.fbl.exception.types.JwtTokenException;
import com.fbl.jwt.utility.JwtHolder;
import com.fbl.test.factory.annotations.InsiteServiceTest;
import com.fbl.test.factory.data.JwtTestFactoryData;

@InsiteServiceTest
public class SubscriptionSocketValidatorTest {

    @Spy
    @InjectMocks
    private SubscriptionSocketValidator subscriptionSocketValidator;

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private EnvironmentService environmentService;

    @Captor
    private ArgumentCaptor<List<AntPathRequestMatcher>> antMatcherListCaptor;

    private MockHttpServletRequest request;

    private String testToken;

    @BeforeEach
    void setup() {
        testToken = JwtTestFactoryData.testToken();
        request = new MockHttpServletRequest();
        request.setMethod(HttpMethod.GET.name());
        request.setServletPath("/subscription/socket");
        request.setQueryString(testToken);
    }

    @Test
    void testValidateRequest() {
        when(environmentService.getEnvironment()).thenReturn(Environment.TEST);
        when(environmentService.getSigningKey()).thenReturn(JwtTestFactoryData.testSigningKey());

        subscriptionSocketValidator.validateRequest(request);

        verify(jwtHolder).setToken(testToken);
    }

    @Test
    void testValidateRequestInvalidToken() {
        request.setQueryString("not-valid");
        when(environmentService.getSigningKey()).thenReturn(JwtTestFactoryData.testSigningKey());

        JwtTokenException ex = assertThrows(JwtTokenException.class,
                () -> subscriptionSocketValidator.validateRequest(request));

        verify(jwtHolder, never()).setToken(anyString());
        assertEquals("JWT strings must contain exactly 2 period characters. Found: 0", ex.getMessage(),
                "Exception Message");
    }

    @Test
    void testValidateRequestMissingToken() {
        request.setQueryString(null);

        JwtTokenException ex = assertThrows(JwtTokenException.class,
                () -> subscriptionSocketValidator.validateRequest(request));

        verify(jwtHolder, never()).setToken(anyString());
        assertEquals("Missing JWT Token", ex.getMessage(), "Exception Message");
    }
}
