/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.test.factory.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import com.awana.test.factory.resolver.ActiveProfileRestTestResolver;

/**
 * Annotation id for test that deal with dao classes.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(resolver = ActiveProfileRestTestResolver.class)
public @interface AwanaRestTest {
}