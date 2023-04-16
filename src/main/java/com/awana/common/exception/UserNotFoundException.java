/**
 * Copyright of awana App. All rights reserved.
 */
/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.common.exception;

/**
 * Exception thrown when user can not be found or updated.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
