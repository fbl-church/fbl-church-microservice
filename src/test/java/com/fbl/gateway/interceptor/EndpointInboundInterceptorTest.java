package com.fbl.gateway.interceptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fbl.gateway.validator.EndpointInboundValidator;
import com.fbl.jwt.utility.JwtHolder;
import com.fbl.test.factory.abstracts.BaseControllerTest;
import com.fbl.test.factory.annotations.InsiteRestTest;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@InsiteRestTest
public class EndpointInboundInterceptorTest extends BaseControllerTest {
    private final String BASE_PATH = "/api";

    @MockBean
    private EndpointInboundValidator validator;

    @MockBean
    private JwtHolder jwtHolder;

    @MockBean
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Test
    void testInterceptorIsCalled() throws Exception {
        this.mockMvc.perform(get(this.BASE_PATH));

        verify(validator).validateRequest(any(HttpServletRequest.class));
        verify(jwtHolder).clearToken();
    }

    @Test
    void testInterceptorHandlesException() throws Exception {
        doThrow(new JwtException("ERROR")).when(validator).validateRequest(any(HttpServletRequest.class));
        this.mockMvc.perform(get(this.BASE_PATH));

        verify(resolver).resolveException(any(HttpServletRequest.class), any(HttpServletResponse.class), eq(null),
                any(Exception.class));
        verify(jwtHolder).clearToken();
    }

    @Test
    void testInterceptorNotCalledWithoutApiPrefix() throws Exception {
        NoResourceFoundException ex = assertThrows(NoResourceFoundException.class,
                () -> this.mockMvc.perform(get("/invalid")));

        verify(validator, never()).validateRequest(any(HttpServletRequest.class));
        verify(jwtHolder, never()).clearToken();
        assertEquals("No static resource invalid.", ex.getMessage(), "Exception Message");
    }
}
