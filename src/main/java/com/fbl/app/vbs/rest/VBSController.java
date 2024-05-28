/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.vbs.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.children.client.domain.request.ChildGetRequest;
import com.fbl.app.guardian.openapi.TagGuardian;
import com.fbl.app.vbs.client.domain.VBSRegistration;
import com.fbl.app.vbs.client.domain.request.VBSGuardianChildrenGetRequest;
import com.fbl.app.vbs.service.ManageVBSService;
import com.fbl.app.vbs.service.VBSService;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.page.Page;

import jakarta.validation.Valid;

/**
 * VBS Controller for managing endpoints
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@RequestMapping("/api/vbs")
@RestApiController

@TagGuardian
public class VBSController {

    @Autowired
    private VBSService vbsService;

    @Autowired
    private ManageVBSService manageVBSService;

    /**
     * Gets a list of vbs guardian children based of the request filter
     * 
     * @param request to filter on
     * @return page of guardian children
     */
    @GetMapping("/guardian/children")
    public Page<Child> getGuardianVbsChildren(VBSGuardianChildrenGetRequest request) {
        return vbsService.getGuardianVbsChildren(request);
    }

    /**
     * Gets a a vbs child by id
     * 
     * @param id The id of the child to get
     * @return page of guardian children
     */
    @GetMapping("/children")
    public List<Child> getVbsChildren(ChildGetRequest request) {
        return vbsService.getVbsChildren(request);
    }

    /**
     * Takes in a list of children to register for VBS
     * 
     * @param registration The vbs registration
     */
    @PostMapping("/register")
    public void registerChildren(@RequestBody @Valid VBSRegistration registration) {
        manageVBSService.registerChildren(registration);
    }

}
