package com.fbl.gateway.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpHeaders;
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
public class EndpointInboundValidatorTest {

    @Spy
    @InjectMocks
    private EndpointInboundValidator endpointInboundValidator;

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
        request.setServletPath("/api/users");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + testToken);
    }

    @Test
    void testValidateRequest() {
        when(environmentService.getEnvironment()).thenReturn(Environment.TEST);
        when(environmentService.getSigningKey()).thenReturn(JwtTestFactoryData.testSigningKey());

        endpointInboundValidator.validateRequest(request);

        verify(jwtHolder).setToken(testToken);
        verify(endpointInboundValidator).shouldFilterRequest(eq(request), antMatcherListCaptor.capture());
        assertEquals(endpointAntMatchers(), antMatcherListCaptor.getValue(), "Ant Matchers List should match");
    }

    @Test
    void testValidateRequestInvalidToken() {
        request.removeHeader(HttpHeaders.AUTHORIZATION);
        request.addHeader(HttpHeaders.AUTHORIZATION, testToken);

        JwtTokenException ex = assertThrows(JwtTokenException.class,
                () -> endpointInboundValidator.validateRequest(request));

        verify(jwtHolder, never()).setToken(anyString());
        assertEquals("JWT Token does not begin with 'Bearer'", ex.getMessage(), "Exception Message");
    }

    @Test
    void testValidateRequestMissingToken() {
        request.removeHeader(HttpHeaders.AUTHORIZATION);

        JwtTokenException ex = assertThrows(JwtTokenException.class,
                () -> endpointInboundValidator.validateRequest(request));

        verify(jwtHolder, never()).setToken(anyString());
        assertEquals("Missing JWT Token", ex.getMessage(), "Exception Message");
    }

    private List<AntPathRequestMatcher> endpointAntMatchers() {
        List<AntPathRequestMatcher> antMatchers = new ArrayList<>();
        antMatchers.add(new AntPathRequestMatcher("/api/authenticate", HttpMethod.POST.name()));
        antMatchers.add(new AntPathRequestMatcher("/api/users/check-email", HttpMethod.GET.name()));
        antMatchers.add(new AntPathRequestMatcher("/api/users/register", HttpMethod.POST.name()));
        antMatchers.add(new AntPathRequestMatcher("/api/mail/forgot-password", HttpMethod.POST.name()));
        antMatchers.add(new AntPathRequestMatcher("/api/vbs/guardian/children", HttpMethod.GET.name()));
        antMatchers.add(new AntPathRequestMatcher("/api/vbs/children", HttpMethod.GET.name()));
        antMatchers.add(new AntPathRequestMatcher("/api/vbs/register", HttpMethod.POST.name()));
        antMatchers.add(new AntPathRequestMatcher("/**", HttpMethod.OPTIONS.name()));
        return antMatchers;
    }
}
