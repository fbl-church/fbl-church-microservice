/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.gateway.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.fbl.exception.types.JwtTokenException;
import com.google.common.net.HttpHeaders;

import jakarta.servlet.http.HttpServletRequest;

/**
 * JWT token validator for confirming a token on a request header.
 *
 * @author Sam butler
 * @since Dec 5, 2020
 */
@Component
public class EndpointInboundValidator extends CommonTokenValidator {

    /**
     * Checks to see if the token on the request is valid. If it is not valid then
     * it wil throw an exception, otherwise it wil continue. It will confirm that
     * the token is in the right environment, check that it has the correct fields,
     * that it is not expired, and the token signature is valid.
     *
     * @param request The request that is being made to the endpint
     * @throws JwtTokenException If the jwt token is not valid.
     */
    public void validateRequest(HttpServletRequest request) throws JwtTokenException {
        if (shouldFilterRequest(request, excludedMatchers())) {
            final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            runTokenValidation(token, true);
            storeToken(token);
        }
    }

    /**
     * Defined filtered matchers that do not need authentication.
     * 
     * @return List of {@link AntPathRequestMatcher} matchers.
     */
    private List<AntPathRequestMatcher> excludedMatchers() {
        List<AntPathRequestMatcher> matchers = new ArrayList<>();
        matchers.add(new AntPathRequestMatcher("/api/authenticate", HttpMethod.POST.name()));
        matchers.add(new AntPathRequestMatcher("/api/users/check-email", HttpMethod.GET.name()));
        matchers.add(new AntPathRequestMatcher("/api/users/register", HttpMethod.POST.name()));
        matchers.add(new AntPathRequestMatcher("/api/mail/forgot-password", HttpMethod.POST.name()));
        matchers.add(new AntPathRequestMatcher("/**", HttpMethod.OPTIONS.name()));
        return matchers;
    }
}
