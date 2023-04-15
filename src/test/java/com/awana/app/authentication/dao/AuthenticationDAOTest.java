package com.awana.app.authentication.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.awana.test.factory.annotations.AwanaDaoTest;
import com.awana.utility.AwanaDAOTestConfig;

/**
 * Test class for the Authenticate DAO.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@Sql("/scripts/auth/authenticationDAO/init.sql")
@ContextConfiguration(classes = AwanaDAOTestConfig.class)
@AwanaDaoTest
public class AuthenticationDAOTest {

    @Autowired
    private AuthenticationDAO dao;

    @Test
    public void testGetUserAuthPasswordValidEmail() throws Exception {
        String hashedPass = dao.getUserAuthPassword("test@mail.com").get();

        assertNotNull(hashedPass, "Hashed Password not null");
        assertTrue(BCrypt.checkpw("testPassword", hashedPass), "Passwords should match");
    }

    @Test
    public void testGetUserAuthPasswordEmailNotFound() {
        Optional<String> optionalHash = dao.getUserAuthPassword("notFound@mail.com");
        assertFalse(optionalHash.isPresent(), "No Result");
    }
}
