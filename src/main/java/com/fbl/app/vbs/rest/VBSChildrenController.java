package com.fbl.app.vbs.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.vbs.client.domain.VBSChildPoint;
import com.fbl.app.vbs.client.domain.request.VBSChildrenPointsGetRequest;
import com.fbl.app.vbs.openapi.TagVBS;
import com.fbl.app.vbs.service.ManageVBSChildrenService;
import com.fbl.app.vbs.service.VBSChildrenService;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.page.Page;

import jakarta.validation.Valid;

/**
 * VBS Children Controller
 * 
 * @author Sam Butler
 * @since Jun 27, 2024
 */
@RequestMapping("/api/vbs/children")
@RestApiController
@TagVBS
public class VBSChildrenController {

    @Autowired
    private VBSChildrenService vbsChildrenService;

    @Autowired
    private ManageVBSChildrenService manageVBSChildrenService;

    /**
     * Get child points by the children points get request
     * 
     * @param childId The child id
     * @param request The children points get request
     * @return The child points
     */
    @GetMapping("/{childId}/points")
    public Page<VBSChildPoint> getChildPointsById(@PathVariable int childId, VBSChildrenPointsGetRequest request) {
        return vbsChildrenService.getChildPoints(childId, request);
    }

    /**
     * Add points to a child
     * 
     * @param points The points to add to the child
     * @return The child points
     */
    @PutMapping("/{childId}/points")
    public void updateChildPoints(@PathVariable int childId, @RequestBody @Valid List<VBSChildPoint> points) {
        manageVBSChildrenService.updateChildPoints(childId, points);
    }
}
