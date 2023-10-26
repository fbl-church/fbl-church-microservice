/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.email.openapi;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Tag Email
 *
 * @author Sam Butler
 * @since July 19, 2022
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD, ANNOTATION_TYPE })
@Inherited
@Tag(name = "Email Controller")
public @interface TagEmail {
}