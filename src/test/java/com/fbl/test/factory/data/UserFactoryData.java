/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.test.factory.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    /**
     * Gets a list of users
     * 
     * @return List of {@link User} object for testing.
     */
    public static List<User> userDataList() {
        User u1 = new User();
        User u2 = new User();
        User u3 = new User();
        u1.setId(1);
        u1.setFirstName("User1");
        u1.setLastName("Last1");
        u1.setEmail("test1@user.com");
        u2.setId(2);
        u2.setFirstName("User2");
        u2.setLastName("Last2");
        u2.setEmail("test2@user.com");
        u3.setId(3);
        u3.setFirstName("User3");
        u3.setLastName("Last3");
        u3.setEmail("test3@user.com");
        return new ArrayList<>(Arrays.asList(u1, u2, u3));
    }
}
