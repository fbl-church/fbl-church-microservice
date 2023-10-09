/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.environment;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fbl.common.enums.Environment;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Information about the application environment.
 * 
 * @author Sam Butler
 * @since July 25, 2022
 */
@Service
public class EnvironmentService {

    @Value("${jwt.secret:#{null}}")
    private String JWT_SIGNING_KEY;

    @Value("${email.sendgrid-key}")
    private String SENDGRID_API_KEY;

    /**
     * Gets the current active profile environment.
     *
     * @return string of the environment currently running
     */
    public Environment getEnvironment() {
        String env = System.getenv("APP_ENVIRONMENT");
        return env != null ? Environment.valueOf(env) : Environment.LOCAL;
    }

    /**
     * Gets the signing key for jwt tokens.
     * 
     * @return String of the signing key to use.
     */
    public Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SIGNING_KEY));
    }

    /**
     * Gets the signing key property for sending emails
     * 
     * @return {@link String} of the key
     */
    public String getSendGridSigningKey() {
        return SENDGRID_API_KEY;
    }
}