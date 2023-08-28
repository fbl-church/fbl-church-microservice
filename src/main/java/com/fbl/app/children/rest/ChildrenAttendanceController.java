package com.fbl.app.children.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.children.client.domain.ChildAttendance;
import com.fbl.app.children.client.domain.request.ChildAttendanceGetRequest;
import com.fbl.app.children.openapi.TagChildren;
import com.fbl.app.children.service.ChildrenAttendanceService;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.page.Page;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Children Attendance Controller for managing endpoints
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@RequestMapping("/api/children/attendance")
@RestApiController
@TagChildren
public class ChildrenAttendanceController {

    @Autowired
    private ChildrenAttendanceService childrenAttendanceService;

    /**
     * Get a list of children attendance by id.
     * 
     * @param request The filters for the data.
     * @param id      The attendance id to search for.
     * @return Page of children attendance objects.
     */
    @Operation(summary = "Get a list of children attendance by id.", description = "Given the attendance id, it will return a page of the children and who attended.")
    @GetMapping("/{id}")
    public Page<ChildAttendance> getChildrenAttedanceById(ChildAttendanceGetRequest request,
            @PathVariable int id) {
        return childrenAttendanceService.getChildrenAttedanceById(request, id);
    }
}
