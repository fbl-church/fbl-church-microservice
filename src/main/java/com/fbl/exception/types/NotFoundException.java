/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.exception.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a object can not be found.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends ServiceException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String field, Object value) {
        super(String.format("%s not found for id: '%s'", field, value));
    }
}
