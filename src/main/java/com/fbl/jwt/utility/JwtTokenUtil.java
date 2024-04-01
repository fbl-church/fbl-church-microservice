/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.jwt.utility;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.user.client.domain.User;
import com.fbl.environment.EnvironmentService;
import com.fbl.jwt.domain.JwtClaims;
import com.fbl.jwt.domain.JwtPair;

import io.jsonwebtoken.Jwts;

/**
 * Token util to create and manage jwt tokens.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Service
public class JwtTokenUtil implements Serializable {

    public static final long JWT_TOKEN_USER_VALIDITY = 18000000; // 5 hours

    @Autowired
    private EnvironmentService environmentService;

    /**
     * Generate user web token. Token is used to gain access to the web application
     * and to the exposed API's
     * 
     * @param user User info to be added to the token
     * @return {@link JwtPair} of the token and claim set
     */
    public JwtPair generateToken(User user) {
        return generateToken(user, false);
    }

    /**
     * Generate user web token. Token is used to gain access to the web application
     * and to the exposed API's
     * 
     * @param user  User info to be added to the token
     * @param reset The reset password status of the token.
     * @return {@link JwtPair} of the token and claim set
     */
    public JwtPair generateToken(User user, boolean reset) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaims.USER_ID, user.getId());
        claims.put(JwtClaims.FIRST_NAME, user.getFirstName());
        claims.put(JwtClaims.LAST_NAME, user.getLastName());
        claims.put(JwtClaims.EMAIL, user.getEmail());
        claims.put(JwtClaims.WEB_ROLE, user.getWebRole());
        claims.put(JwtClaims.THEME, user.getTheme());
        claims.put(JwtClaims.ENVIRONMENT, environmentService.getEnvironment());
        claims.put(JwtClaims.PASSWORD_RESET, reset);

        String token = buildTokenClaims(claims, JWT_TOKEN_USER_VALIDITY);
        return new JwtPair(token, environmentService.getSigningKey());
    }

    /**
     * Generate a token based on the given Claims and subject
     * 
     * @param claims   The claims/fields to be added to the token
     * @param validity How long in milliseconds the token is good for
     * @return String of the generated JWT token
     */
    public String buildTokenClaims(Map<String, Object> claims, long validity) {
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(environmentService.getSigningKey()).compact();
    }
}
