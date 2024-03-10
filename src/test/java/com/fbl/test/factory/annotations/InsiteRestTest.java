/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.test.factory.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.fbl.environment.EnvironmentService;
import com.fbl.test.factory.resolver.ActiveProfileRestTestResolver;

/**
 * Annotation id for test that deal with dao classes.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(resolver = ActiveProfileRestTestResolver.class)
@TestPropertySource(properties = { "spring.config.name=" + EnvironmentService.APP_CONFIG_NAME })
public @interface InsiteRestTest {
}