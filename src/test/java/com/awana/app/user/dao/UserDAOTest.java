/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.awana.app.user.client.domain.User;
import com.awana.app.user.client.domain.request.UserGetRequest;
import com.awana.common.enums.WebRole;
import com.awana.common.page.Page;
import com.awana.exception.types.NotFoundException;
import com.awana.test.factory.annotations.AwanaDaoTest;
import com.awana.utility.AwanaDAOTestConfig;
import com.google.common.collect.Sets;

/**
 * Test class for the User DAO.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@Sql("/scripts/user/userDAO/init.sql")
@ContextConfiguration(classes = AwanaDAOTestConfig.class)
@AwanaDaoTest
public class UserDAOTest {

    @Autowired
    private UserDAO dao;

    @Test
    public void testGetUserList() {
        Page<User> user = dao.getUsers(new UserGetRequest());

        assertEquals(3, user.getTotalCount(), "User Size should be 3");
        assertEquals("Bill", user.getList().get(0).getFirstName(), "User 1 first name");
        assertEquals("Fake", user.getList().get(1).getFirstName(), "User 2 first name");
        assertEquals("Test", user.getList().get(2).getFirstName(), "User 3 first name");
    }

    @Test
    public void testGetUserListWithFilter() {
        UserGetRequest request = new UserGetRequest();
        request.setWebRole(Sets.newHashSet(WebRole.USER));
        List<User> user = dao.getUsers(request).getList();

        assertEquals(2, user.size(), "User Size should be 2");
        assertEquals("Bill", user.get(0).getFirstName(), "User 1 first name");
        assertEquals("Test", user.get(1).getFirstName(), "User 2 first name");
    }

    @Test
    public void testGetUserListNoResults() {
        UserGetRequest request = new UserGetRequest();
        request.setEmail(Sets.newHashSet("noUser@mail.com"));

        assertTrue(dao.getUsers(request).getList().isEmpty(), "User list should be empty");
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = dao.getUserById(1);

        assertEquals("Test", user.getFirstName(), "First name");
        assertEquals("User", user.getLastName(), "Last name");
        assertEquals("test@mail.com", user.getEmail(), "Email");
        assertEquals(WebRole.USER, user.getWebRole(), "Web Role");
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        NotFoundException e = assertThrows(NotFoundException.class, () -> dao.getUserById(12));
        assertEquals("User not found for id: '12'", e.getMessage(), "Message should match");
    }

    @Test
    public void testInsertUser() throws Exception {
        List<User> beforeInsertList = dao.getUsers(new UserGetRequest()).getList();

        assertEquals(3, beforeInsertList.size(), "Size should be 3");

        User user = new User();
        user.setFirstName("NewUserInsert");
        user.setLastName("LastName");
        user.setEmail("newEmail@mail.com");
        user.setWebRole(WebRole.ADMIN);

        int newUserId = dao.insertUser(user);
        assertEquals(4, newUserId, "New user Id should be 4");
    }

    @Test
    public void testUpdateUserValues() throws Exception {
        User user = new User();
        assertEquals("Test", dao.getUserById(1).getFirstName());
        user.setFirstName("Randy");
        user.setWebRole(WebRole.USER);

        dao.updateUser(1, user);
    }

    @Test
    public void testUpdateUniqueEmail() {
        User user = new User();
        user.setEmail("Fake123@mail.com");
        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class,
                () -> dao.updateUser(1, user));
        assertTrue(e.getMessage().contains("Duplicate entry 'Fake123@mail.com'"));
    }
}
