/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.gateway.interceptor;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fbl.gateway.validator.SubscriptionSocketValidator;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * All Request involving the subscription endpoints will get filtered through
 * here.
 * 
 * @author Sam butler
 * @since Aug 6, 2021
 */
@WebFilter(urlPatterns = "/subscription/socket")
public class SubscriptionSocketInterceptor extends CommonInterceptor {

    @Autowired
    private SubscriptionSocketValidator validator;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        performFilter(validator, request, response, filterChain);
    }
}
