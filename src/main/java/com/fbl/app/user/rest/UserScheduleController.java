/*
 * Copyright of FBL Church App. All rights reserved.
*/
package com.fbl.app.user.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.user.client.domain.UserSchedule;
import com.fbl.app.user.client.domain.request.UserScheduleRequest;
import com.fbl.app.user.openapi.TagUser;
import com.fbl.app.user.service.UserScheduleService;
import com.fbl.common.annotations.interfaces.RestApiController;

import io.swagger.v3.oas.annotations.Operation;

/**
 * User Schedule Controller
 *
 * @author Sam Butler
 * @since Feb 14, 2024 02
 */
@RequestMapping("/api/users/schedule")
@RestApiController
@TagUser
public class UserScheduleController {

    @Autowired
    private UserScheduleService service;

    /**
     * Gets a list of user scheduled based on the user id
     * 
     * @param request The request to filter on
     * @return list of user schedule objects
     */
    @Operation(summary = "Get a list of schedules for a user", description = "Given a user id. It will return a list of schedules for that user.")
    @GetMapping
    public List<UserSchedule> getUserSchedulesById(UserScheduleRequest request) {
        return service.getUserSchedulesById(request);
    }
}
