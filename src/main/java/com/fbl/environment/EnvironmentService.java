/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.environment;

import java.security.Key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentService.class);
    private static final String ACTIVE_PROFILE = "APP_ENVIRONMENT";
    private static final String SIGNING_KEY = "JWT_SIGNING_KEY";

    @Value("${jwt.secret:#{null}}")
    private String LOCAL_SIGNING_KEY;

    @Value("${email.sendgrid-key}")
    private String SENDGRID_API_KEY;

    /**
     * Gets the current active profile environment.
     *
     * @return string of the environment currently running
     */
    public Environment getEnvironment() {
        String env = System.getenv(ACTIVE_PROFILE);
        return env != null ? Environment.valueOf(env) : Environment.LOCAL;
    }

    /**
     * Gets the signing key for jwt tokens.
     * 
     * @return String of the signing key to use.
     */
    public Key getSigningKey() {
        LOGGER.info("LOCAL KEY HAS TEXT: ", StringUtils.hasText(LOCAL_SIGNING_KEY));
        String signingKey = StringUtils.hasText(LOCAL_SIGNING_KEY) ? LOCAL_SIGNING_KEY : System.getenv(SIGNING_KEY);
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(keyBytes);
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