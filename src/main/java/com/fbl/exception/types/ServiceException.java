/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.exception.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Base Exception class. Will omit stack trace and only display exception.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}