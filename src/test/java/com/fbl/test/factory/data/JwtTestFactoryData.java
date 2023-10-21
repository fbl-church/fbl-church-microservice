package com.fbl.test.factory.data;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fbl.common.enums.Environment;
import com.fbl.common.enums.WebRole;
import com.fbl.jwt.domain.JwtClaims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Class for holding common test data to be used in test classes.
 * 
 * @author Sam Butler
 * @since May 26, 2022
 */
public class JwtTestFactoryData {
    private static final long TEST_TOKEN_VALIDITY = 600000L;
    private static final String TEST_SIGNING_KEY = "yfjtUX1PHn4ypdjvkzmCBY4b731bmkIhx3OY1sd4utUY55FvH80c9M3VGxKQ540a";

    /**
     * Generate a test token,
     * 
     * @return String of the generated JWT token
     */
    public static String testToken() {
        return testToken(testClaims());
    }

    /**
     * Generate a test token with the provided claims
     * 
     * @return String of the generated JWT token
     */
    public static String testToken(Map<String, Object> claims) {
        return testToken(claims, TEST_TOKEN_VALIDITY);
    }

    /**
     * Generate a test token with the provided claims and validity
     * 
     * @return String of the generated JWT token
     */
    public static String testToken(Long validity) {
        return testToken(testClaims(), validity);
    }

    /**
     * Generate a test token with the provided claims and validity
     * 
     * @return String of the generated JWT token
     */
    public static String testToken(Map<String, Object> claims, Long validity) {
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(testSigningKey()).compact();
    }

    /**
     * Gets the test signing key being used
     * 
     * @return The signing key.
     */
    public static Key testSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(TEST_SIGNING_KEY));
    }

    /**
     * Test Claimns
     * 
     * @return The map of test user claims
     */
    public static Map<String, Object> testClaims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaims.USER_ID, 1);
        claims.put(JwtClaims.FIRST_NAME, "Test");
        claims.put(JwtClaims.LAST_NAME, "User");
        claims.put(JwtClaims.EMAIL, "test.user@fbl.com");
        claims.put(JwtClaims.WEB_ROLE, WebRole.USER);
        claims.put(JwtClaims.ENVIRONMENT, Environment.TEST);
        claims.put(JwtClaims.PASSWORD_RESET, false);
        return claims;
    }
}
