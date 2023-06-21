/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.exception.types;

import java.util.List;

import com.fbl.common.enums.WebRole;

/**
 * Exception thrown when user does not have the permissions to access certion
 * data.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public class InsufficientPermissionsException extends BaseException {

    /**
     * Base Constructor for adding a custom message on a
     * {@link InsufficientPermissionsException} object
     * 
     * @param message The message to be used.
     */
    public InsufficientPermissionsException(String message) {
        super(message);
    }

    /**
     * Throws {@link InsufficientPermissionsException} for the role.
     * 
     * @param role The role to that has insufficent permissions.
     */
    public InsufficientPermissionsException(WebRole role) {
        super(String.format("Insufficient Permissions for role '%s'", role));
    }

    /**
     * Throws {@link InsufficientPermissionsException} for the role.
     * 
     * @param roles The roles the user had insufficent permissions for
     */
    public InsufficientPermissionsException(List<WebRole> roles) {
        this(WebRole.highestRoleRank(roles));
    }
}
