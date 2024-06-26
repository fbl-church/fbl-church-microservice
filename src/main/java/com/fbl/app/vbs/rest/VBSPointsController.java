/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.vbs.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.vbs.client.domain.VBSPoint;
import com.fbl.app.vbs.openapi.TagVBS;
import com.fbl.app.vbs.service.ManageVBSPointsService;
import com.fbl.app.vbs.service.VBSPointsService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;

import jakarta.validation.Valid;

/**
 * VBS Points Controller for managing endpoints
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@RequestMapping("/api/vbs/points")
@RestApiController
@TagVBS
public class VBSPointsController {

    @Autowired
    private VBSPointsService vbsPointsService;

    @Autowired
    private ManageVBSPointsService manageVBSPointsService;

    /**
     * Creates a list of points configs
     * 
     * @param points The point configs to be created
     * @return page of the created point configs
     */
    @GetMapping("/themes/{id}")
    public Page<VBSPoint> getVBSPointsByThemeId(@PathVariable Integer id) {
        return vbsPointsService.getVbsPointsByThemeId(id);
    }

    /**
     * Checks if a point name exists for a theme id
     * 
     * @param id   The id of the theme to check
     * @param name The name of the point to check
     * @return true if a point name exists for the theme id, false otherwise
     */
    @GetMapping("/themes/{id}/points/name/{name}")
    public boolean doesPointNameExistForThemeId(@PathVariable int id, @PathVariable String name) {
        return vbsPointsService.doesPointNameExistForThemeId(id, name);
    }

    /**
     * Creates a list of points configs
     * 
     * @param points The point configs to be created
     * @return page of the created point configs
     */
    @PostMapping("/themes/{id}")
    @HasAccess(WebRole.VBS_DIRECTOR)
    public List<VBSPoint> createPointsConfigs(@PathVariable Integer id, @RequestBody @Valid List<VBSPoint> points) {
        return manageVBSPointsService.createPointConfigs(id, points);
    }

    /**
     * Update a point config by id.
     * 
     * @param id     The id of the point config to update
     * @param points The point configs to be updated
     */
    @PutMapping("/{id}")
    @HasAccess(WebRole.VBS_DIRECTOR)
    public void updatePointsConfig(@PathVariable int id, @RequestBody @Valid VBSPoint points) {
        manageVBSPointsService.updatePointsConfig(id, points);
    }

    /**
     * Deletes a point config by id.
     * 
     * @param id The id of the point config to delete
     */
    @DeleteMapping("/{id}")
    @HasAccess(WebRole.VBS_DIRECTOR)
    public void deletePointConfigById(@PathVariable Integer id) {
        manageVBSPointsService.deletePointConfigById(id);
    }

    /**
     * Deletes all point configs associated to the theme id
     * 
     * @param id The id of the theme to remove point configs from
     */
    @DeleteMapping("/themes/{id}")
    @HasAccess(WebRole.MODERATOR)
    public void deletePointConfigByThemeId(@PathVariable Integer id) {
        manageVBSPointsService.deletePointConfigByThemeId(id);
    }

}
