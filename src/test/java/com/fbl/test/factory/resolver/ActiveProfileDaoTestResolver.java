/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.test.factory.resolver;

import java.util.Map;

import org.springframework.test.context.ActiveProfilesResolver;

import com.fbl.test.factory.globals.GlobalsTest;

/**
 * Resolver method that decides what property file to use when running test for
 * DAO classes.
 * 
 * @author Sam Butler
 * @since April 25, 2022
 */
public class ActiveProfileDaoTestResolver implements ActiveProfilesResolver {

    private static final String DAO_TEST_PROFILE = "test-dao";

    @Override
    public String[] resolve(Class<?> testClass) {
        Map<String, String> env = System.getenv();
        return new String[] { env.containsKey("APP_ENVIRONMENT") ? GlobalsTest.PRODUCTION_TEST : GlobalsTest.LOCAL_TEST,
                DAO_TEST_PROFILE };
    }
}
