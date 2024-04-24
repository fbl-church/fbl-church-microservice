/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.vbs.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.guardian.openapi.TagGuardian;
import com.fbl.app.vbs.client.domain.request.VBSGuardianChildrenGetRequest;
import com.fbl.app.vbs.service.VBSService;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.page.Page;

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

}
