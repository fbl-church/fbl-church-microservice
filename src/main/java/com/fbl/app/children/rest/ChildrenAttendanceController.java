package com.fbl.app.children.rest;

import static org.springframework.http.MediaType.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fbl.app.children.client.domain.ChildAttendance;
import com.fbl.app.children.client.domain.request.ChildAttendanceGetRequest;
import com.fbl.app.children.openapi.TagChildren;
import com.fbl.app.children.service.ChildrenAttendanceService;
import com.fbl.common.page.Page;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

/**
 * Children Attendance Controller for managing endpoints
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@RequestMapping("/api/children/attendance")
@RestController
@TagChildren
@RequiredArgsConstructor
public class ChildrenAttendanceController {

    private final ChildrenAttendanceService childrenAttendanceService;

    /**
     * Get a list of children attendance by id.
     * 
     * @param request The filters for the data.
     * @param id      The attendance id to search for.
     * @return Page of children attendance objects.
     */
    @Operation(summary = "Get a list of children attendance by id.", description = "Given the attendance id, it will return a page of the children and who attended.")
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Page<ChildAttendance> getChildrenAttedanceById(ChildAttendanceGetRequest request,
            @PathVariable int id) {
        return childrenAttendanceService.getChildrenAttedanceById(request, id);
    }
}
