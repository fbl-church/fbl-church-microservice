/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.exception.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when user has invalid credentials.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidCredentialsException extends ServiceException {
    public InvalidCredentialsException(String email) {
        super(String.format("Invalid Credentials for user email: '%s'", email));
    }
}