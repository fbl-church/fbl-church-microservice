/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.jwt.utility;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awana.app.featureaccess.client.FeatureAccessClient;
import com.awana.app.user.client.UserClient;
import com.awana.app.user.client.domain.Application;
import com.awana.app.user.client.domain.User;
import com.awana.environment.AppEnvironmentService;
import com.awana.jwt.domain.AwanaJwtClaims;
import com.awana.jwt.domain.JwtType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
    private AppEnvironmentService appEnvironmentService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private FeatureAccessClient featureAccessClient;

    /**
     * Pulls the expiration date from a given token
     * 
     * @param token The token being inspected
     * @return A Date object
     */
    public LocalDateTime getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration).toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * Get Specfic claims from a token based on the passed in resolver
     * 
     * @param <T>            Object type
     * @param token          Token to be inspected
     * @param claimsResolver Claims resolver
     * @return The generic type passed in of the claims
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Pulls all the claims off of a given token
     * 
     * @param token The token to inspect and pull the claims from
     * @return Claims object is returned
     */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(appEnvironmentService.getSigningKey()).parseClaimsJws(token).getBody();
    }

    /**
     * Checks if the given token is expired
     * 
     * @param token - Token to pull the expiration date from
     * @return Returns a boolean object of true, false, or null
     */
    public Boolean isTokenExpired(String token) {
        final LocalDateTime expiration = getExpirationDateFromToken(token);
        return expiration.isBefore(LocalDateTime.now());
    }

    /**
     * Generate user web token. Token is used to gain access to the web application
     * and to the exposed API's
     * 
     * @param user User info to be added to the token
     * @return String of the new JWT token
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AwanaJwtClaims.USER_ID, user.getId());
        claims.put(AwanaJwtClaims.FIRST_NAME, user.getFirstName());
        claims.put(AwanaJwtClaims.LAST_NAME, user.getLastName());
        claims.put(AwanaJwtClaims.EMAIL, user.getEmail());
        claims.put(AwanaJwtClaims.WEB_ROLE, user.getWebRole());
        claims.put(AwanaJwtClaims.ENVIRONMENT, appEnvironmentService.getEnvironment());
        claims.put(AwanaJwtClaims.JWT_TYPE, JwtType.WEB);
        claims.put(AwanaJwtClaims.APPS, userClient.getUserAppsById(user.getId()).stream()
                .filter(v -> (v.isAccess() && v.isEnabled())).map(Application::getName).collect(Collectors.toList()));
        claims.put(AwanaJwtClaims.ACCESS, featureAccessClient.getFeatureAccess(user.getWebRole()));
        return buildTokenClaims(claims, JWT_TOKEN_USER_VALIDITY);
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
                .signWith(SignatureAlgorithm.HS512, appEnvironmentService.getSigningKey()).compact();
    }
}
