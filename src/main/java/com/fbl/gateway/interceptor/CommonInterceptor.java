/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.gateway.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fbl.gateway.domain.interfaces.BaseRequestValidator;
import com.fbl.jwt.utility.JwtHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Common interceptor to extend common functionality for API's.
 * 
 * @author Sam butler
 * @since Aug 6, 2021
 */
@Component
public abstract class CommonInterceptor extends OncePerRequestFilter {

    @Autowired
    private JwtHolder jwtHolder;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    /**
     * Performs a validate request on the given params and decides if it will
     * perform the filter chain. This request will be used to validate that it can
     * access the endpoint and it has the correct permissions.
     *
     * @param v     The validator to perform.
     * @param req   The request to pass to the function.
     * @param res   The response to parse.
     * @param chain The filter chain to perform on the request.
     */
    protected void performFilter(BaseRequestValidator v, HttpServletRequest req, HttpServletResponse res,
            FilterChain chain) {
        try {
            v.validateRequest((HttpServletRequest) req);
            chain.doFilter(req, res);

        } catch (Exception e) {
            clearThreadToken();
            resolveException(req, res, e);
        } finally {
            clearThreadToken();
        }
    }

    /**
     * Clears the current process token stored on the local thread of the request
     * instance.
     */
    private void clearThreadToken() {
        jwtHolder.clearToken();
    }

    /**
     * Method to resolve exceptions that occur when filtering the request.
     * 
     * @param req The request that is passed.
     * @param res The response to validate.
     * @param e   The exception that was thrown.
     */
    private void resolveException(HttpServletRequest req, HttpServletResponse res, Exception e) {
        resolver.resolveException(req, getAllowedOriginResponse(res), null, e);
    }

    /**
     * Appends the allowed origins to the response.
     * 
     * @param res The response to modify.
     * @return The updated response.
     */
    private HttpServletResponse getAllowedOriginResponse(HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Origin", "*");
        return res;
    }
}