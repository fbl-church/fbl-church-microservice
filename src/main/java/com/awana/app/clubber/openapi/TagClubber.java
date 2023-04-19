/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.clubber.openapi;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Tag Clubber
 *
 * @author Sam Butler
 * @since July 19, 2022
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD, ANNOTATION_TYPE})
@Inherited
@Tag(name = "Clubber")
public @interface TagClubber {}