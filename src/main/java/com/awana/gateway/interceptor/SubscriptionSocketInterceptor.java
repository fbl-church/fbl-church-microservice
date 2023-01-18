package com.awana.gateway.interceptor;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.beans.factory.annotation.Autowired;

import com.awana.gateway.domain.abstracts.CommonInterceptor;
import com.awana.gateway.validator.SubscriptionSocketValidator;

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
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        performFilter(validator, req, res, chain);
    }
}
