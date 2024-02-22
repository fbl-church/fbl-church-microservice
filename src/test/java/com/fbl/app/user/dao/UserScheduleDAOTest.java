/*
 * Copyright of FBL Church App. All rights reserved.
*/
package com.fbl.app.user.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.fbl.app.user.client.domain.UserSchedule;
import com.fbl.app.user.client.domain.request.UserScheduleRequest;
import com.fbl.test.factory.annotations.InsiteDaoTest;
import com.fbl.utility.InsiteDAOTestConfig;

/**
 * User Schedule DAO Test
 *
 * @author Sam Butler
 * @since Feb 21, 2024 02
 */
@Sql("/scripts/user/userScheduleDAO/init.sql")
@ContextConfiguration(classes = InsiteDAOTestConfig.class)
@InsiteDaoTest
public class UserScheduleDAOTest {

    @Autowired
    private UserScheduleDAO dao;

    @Test
    void testGetUserSchedules_whenCalled_willReturnListOfUserSchdules() {
        List<UserSchedule> userSchedule = dao.getUserSchedulesById(new UserScheduleRequest());
        assertEquals(3, userSchedule.size(), "Size of List");
    }
}
