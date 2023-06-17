/**
 * Copyright of FBL Church App. All rights reserved.
 */
/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.exception.types;

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
