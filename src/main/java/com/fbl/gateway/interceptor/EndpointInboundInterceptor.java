/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.gateway.interceptor;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fbl.gateway.validator.EndpointInboundValidator;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

/**
 * All endpoint request will be filtered through this class. It determines if
 * the request is allowed to be made or not.
 * 
 * @author Sam butler
 * @since Aug 6, 2021
 */
@WebFilter(urlPatterns = "/api/*")
public class EndpointInboundInterceptor extends CommonInterceptor {

    @Autowired
    private EndpointInboundValidator validator;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        performFilter(validator, req, res, chain);
    }
}