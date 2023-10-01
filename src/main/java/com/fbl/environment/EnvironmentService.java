/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fbl.common.enums.Environment;

/**
 * Information about the application environment.
 * 
 * @author Sam Butler
 * @since July 25, 2022
 */
@Service
public class EnvironmentService {
    private static final String ACTIVE_PROFILE = "APP_ENVIRONMENT";
    private static final String SIGNING_KEY = "JWT_SIGNING_KEY";

    @Value("${jwt.secret:#{null}}")
    private String LOCAL_SIGNING_KEY;

    @Value("${email.sendgrid-key}")
    private String SENDGRID_API_KEY;

    @Value("${cloudinary.url}")
    private String CLOUDINARY_URL;

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
    public String getSigningKey() {
        return LOCAL_SIGNING_KEY != null ? LOCAL_SIGNING_KEY : System.getenv(SIGNING_KEY);
    }

    /**
     * Gets the signing key property for sending emails
     * 
     * @return {@link String} of the key
     */
    public String getSendGridSigningKey() {
        return SENDGRID_API_KEY;
    }

    public String getCloudinaryUrl() {
        return CLOUDINARY_URL;
    }
}