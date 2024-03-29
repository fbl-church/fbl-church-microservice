/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.exception.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when exception occurs for jwt.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtTokenException extends ServiceException {
    public JwtTokenException(String msg) {
        super(msg);
    }
}