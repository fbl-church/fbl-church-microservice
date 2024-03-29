/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.test.factory.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.fbl.environment.EnvironmentService;
import com.fbl.test.factory.config.DataSourceTestConfiguration;
import com.fbl.test.factory.resolver.ActiveProfileDaoTestResolver;

/**
 * Annotation id for test that deal with dao classes.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { DataSourceTestConfiguration.class })
@ActiveProfiles(resolver = ActiveProfileDaoTestResolver.class)
@TestPropertySource(properties = { "spring.config.name=" + EnvironmentService.APP_CONFIG_NAME })
public @interface InsiteDaoTest {
}