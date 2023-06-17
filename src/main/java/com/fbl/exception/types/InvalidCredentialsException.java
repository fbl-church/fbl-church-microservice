/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.exception.types;

/**
 * Exception thrown when user has invalid credentials.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public class InvalidCredentialsException extends BaseException {
    public InvalidCredentialsException(String email) {
        super(String.format("Invalid Credentials for user email: '%s'", email));
    }
}