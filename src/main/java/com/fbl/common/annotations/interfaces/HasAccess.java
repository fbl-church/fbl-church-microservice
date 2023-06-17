/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.annotations.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fbl.common.enums.WebRole;

/**
 * Annotation for checking if user has access to an endpoint.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HasAccess {
    WebRole value() default WebRole.USER;
}
