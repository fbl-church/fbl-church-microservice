/*
 * Copyright of FBL Church App. All rights reserved.
*/
package com.fbl.app.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.user.client.domain.UserSchedule;
import com.fbl.app.user.client.domain.request.UserScheduleRequest;
import com.fbl.app.user.dao.UserScheduleDAO;

/**
 * Description
 *
 * @author Sam Butler
 * @since Feb 14, 2024 02
 */
@Service
public class UserScheduleService {

    @Autowired
    private UserScheduleDAO dao;

    /**
     * Gets a list of user scheduled based on the user id
     * 
     * @param userId The id of the user to get schedules for
     * @return list of user schedule objects
     */
    public List<UserSchedule> getUserSchedulesById(UserScheduleRequest request) {
        return dao.getUserSchedulesById(request);
    }
}
