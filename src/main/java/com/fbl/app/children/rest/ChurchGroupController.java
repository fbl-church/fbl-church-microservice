package com.fbl.app.children.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.children.client.domain.request.ChurchGroupGetRequest;
import com.fbl.app.children.service.ChurchGroupService;
import com.fbl.app.user.openapi.TagUser;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.common.page.Page;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/church-groups")
@RestApiController
@TagUser
public class ChurchGroupController {

    @Autowired
    private ChurchGroupService churchGroupService;

    /**
     * Gets a list Church Groups based on the request
     * 
     * @param request to filter on
     * @return list of webroles objects
     */
    @Operation(summary = "Get a list of church groups.", description = "Given a Church Group Get Request, it will return a list of church group that match the request.")
    @GetMapping
    public Page<ChurchGroup> getChurchGroups(ChurchGroupGetRequest request) {
        return churchGroupService.getChurchGroups(request);
    }
}
