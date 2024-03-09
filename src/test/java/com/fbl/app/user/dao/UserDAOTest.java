/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.fbl.app.user.client.domain.User;
import com.fbl.app.user.client.domain.request.UserGetRequest;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;
import com.fbl.test.factory.annotations.InsiteDaoTest;
import com.fbl.utility.InsiteDAOTestConfig;
import com.google.common.collect.Sets;

/**
 * Test class for the User DAO.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@Sql("/scripts/user/userDAO/init.sql")
@ContextConfiguration(classes = InsiteDAOTestConfig.class)
@InsiteDaoTest
public class UserDAOTest {

    @Autowired
    private UserDAO dao;

    @Test
    void testGetUsers_whenCalled_willReturnListOfUsers() {
        Page<User> user = dao.getUsers(new UserGetRequest());

        assertEquals(3, user.getTotalCount(), "User Size should be 3");
        assertEquals("Bill", user.getList().get(0).getFirstName(), "User 1 first name");
        assertEquals("Fake", user.getList().get(1).getFirstName(), "User 2 first name");
        assertEquals("Test", user.getList().get(2).getFirstName(), "User 3 first name");
    }

    @Test
    void testGetUsers_whenCalledWithRequest_willFilterTheUsersReturned() {
        UserGetRequest request = new UserGetRequest();
        request.setWebRole(Sets.newHashSet(WebRole.USER));
        List<User> user = dao.getUsers(request).getList();

        assertEquals(2, user.size(), "User Size should be 2");
        assertEquals("Bill", user.get(0).getFirstName(), "User 1 first name");
        assertEquals("Test", user.get(1).getFirstName(), "User 2 first name");
    }

    @Test
    void testGetUsers_whenCalledWithNoMatching_willReturnEmptyList() {
        UserGetRequest request = new UserGetRequest();
        request.setEmail(Sets.newHashSet("noUser@mail.com"));

        assertTrue(dao.getUsers(request).getList().isEmpty(), "User list should be empty");
    }

    @Test
    void testGetUserById_whenCalled_willReturnTheUserWithThatId() {
        Optional<User> foundUser = dao.getUserById(1);

        assertTrue(foundUser.isPresent(), "User exists");
        assertEquals(1, foundUser.get().getId(), "User Id");
        assertEquals("Test", foundUser.get().getFirstName(), "User First Name");
        assertEquals("User", foundUser.get().getLastName(), "User Last Name");
    }

    @Test
    void testInsertUser_whenCalled_willCreateTheNewUser() throws Exception {
        List<User> beforeInsertList = dao.getUsers(new UserGetRequest()).getList();

        assertEquals(3, beforeInsertList.size(), "Size should be 3");

        User user = new User();
        user.setFirstName("NewUserInsert");
        user.setLastName("LastName");
        user.setEmail("newEmail@mail.com");
        user.setWebRole(List.of(WebRole.SITE_ADMINISTRATOR));

        int newUserId = dao.insertUser(user);
        assertEquals(4, newUserId, "New user Id should be 4");
    }

    @Test
    void testUpdateUser_whenCalledWithDuplicateEmail_willThrowException() {
        User user = new User();
        user.setEmail("Fake123@mail.com");
        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class,
                () -> dao.updateUser(1, user));
        assertTrue(e.getMessage().contains("Duplicate entry 'Fake123@mail.com'"));
    }
}
