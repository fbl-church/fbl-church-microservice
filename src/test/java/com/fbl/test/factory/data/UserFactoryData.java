/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.test.factory.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import com.fbl.app.user.client.domain.User;
import com.fbl.common.enums.WebRole;

/**
 * Class for holding common test data to be used in test classes.
 * 
 * @author Sam Butler
 * @since May 26, 2022
 */
public class UserFactoryData {

    /**
     * Gets a default user object.
     * 
     * @return {@link User} object for testing.
     */
    public static User userData() {
        User u = new User();
        u.setId(12);
        u.setFirstName("Test");
        u.setLastName("User");
        u.setEmail("test@user.com");
        u.setWebRole(new ArrayList<WebRole>(Arrays.asList(WebRole.SITE_ADMINISTRATOR)));
        u.setLastLoginDate(LocalDateTime.now());
        return u;
    }
}
