/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.fbl.test.factory.annotations.InsiteDaoTest;
import com.fbl.utility.InsiteDAOTestConfig;

/**
 * Test class for the User Credentials DAO.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@Sql("/scripts/user/userCredentialsDAO/init.sql")
@ContextConfiguration(classes = InsiteDAOTestConfig.class)
@InsiteDaoTest
public class UserCredentialsDAOTest {

        @Autowired
        private UserCredentialsDAO dao;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Test
        public void testInsertUserPasswordValidUserId() throws Exception {
                assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "user_credentials"),
                                "Row count should be 1 before insert");

                dao.insertUserPassword(2, BCrypt.hashpw("TestPassword!", BCrypt.gensalt()));

                assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "user_credentials"),
                                "Row count should be 2 after insert");
        }

        @Test
        public void testInsertUserPasswordInvalidUserId() {
                DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class, () -> dao
                                .insertUserPassword(200, BCrypt.hashpw("TestPassword!", BCrypt.gensalt())));

                assertTrue(e.getLocalizedMessage().contains("foreign key constraint fails"), "Exception Message");
        }

        @Test
        public void testUpdateUserPassword() {
                String newPassword = BCrypt.hashpw("TestPasswordUpdate!", BCrypt.gensalt());

                assertEquals(0,
                                JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "user_credentials",
                                                String.format("password='%s'", newPassword)),
                                "Password should not exist");

                dao.updateUserPassword(1, newPassword);

                assertEquals(1,
                                JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "user_credentials",
                                                String.format("password='%s'", newPassword)),
                                "Password should be updated");
        }
}
