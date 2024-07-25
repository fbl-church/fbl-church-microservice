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

import com.fbl.app.vbs.client.domain.VBSPointDivision;
import com.fbl.app.vbs.openapi.TagVBS;
import com.fbl.app.vbs.service.ManageVBSPointDivisionService;
import com.fbl.app.vbs.service.VBSPointDivisionService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;

import jakarta.validation.Valid;

/**
 * VBS Point Division Controller for managing endpoints
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@RequestMapping("/api/vbs/points/division")
@RestApiController
@TagVBS
public class VBSPointDivisionController {

    @Autowired
    private VBSPointDivisionService vbsPointDivisionService;

    @Autowired
    private ManageVBSPointDivisionService manageVBSPointDivisionService;

    /**
     * Gets a page of vbs theme point divisions by theme id
     * 
     * @param id The id of the theme to filter on
     * @return page of vbs point divisions
     */
    @GetMapping("/themes/{id}")
    public Page<VBSPointDivision> getVBSPointDivisionsByThemeId(@PathVariable Integer id) {
        return vbsPointDivisionService.getVBSPointDivisionsByThemeId(id);
    }

    /**
     * Checks to see if the given value is within the range of any existing point
     * 
     * @param id    The id of the theme to check
     * @param value The value to check
     * @return true if the value is within the range of any existing point, false
     *         otherwise
     */
    @GetMapping("/themes/{id}/value/{value}/range-exist")
    public boolean isRangeValueWithinExistingRangeForThemeId(@PathVariable int id, @PathVariable int value) {
        return vbsPointDivisionService.isRangeValueWithinExistingRangeForThemeId(id, value);
    }

    /**
     * Creates a list of points divisions for a theme
     * 
     * @param points The point divisions to be created
     * @return page of the created point divisions
     */
    @PostMapping("/themes/{id}")
    @HasAccess(WebRole.VBS_DIRECTOR)
    public List<VBSPointDivision> createPointDivisions(@PathVariable Integer id,
            @RequestBody @Valid List<VBSPointDivision> pointDivisions) {
        return manageVBSPointDivisionService.createPointDivisions(id, pointDivisions);
    }

    /**
     * Update a point division by id.
     * 
     * @param id     The id of the point division to update
     * @param points The point divisions to be updated
     */
    @PutMapping("/{id}")
    @HasAccess(WebRole.VBS_DIRECTOR)
    public void updatePointDivision(@PathVariable int id, @RequestBody @Valid VBSPointDivision pointDivision) {
        manageVBSPointDivisionService.updatePointsDivision(id, pointDivision);
    }

    /**
     * Deletes a point division by id.
     * 
     * @param id The id of the point division to delete
     */
    @DeleteMapping("/{id}")
    @HasAccess(WebRole.VBS_DIRECTOR)
    public void deletePointDivisionById(@PathVariable Integer id) {
        manageVBSPointDivisionService.deletePointDivisionById(id);
    }

    /**
     * Deletes all point divisions associated to the theme id
     * 
     * @param id The id of the theme to remove point divisions from
     */
    @DeleteMapping("/themes/{id}")
    @HasAccess(WebRole.MODERATOR)
    public void deletePointDivisionsByThemeId(@PathVariable Integer id) {
        manageVBSPointDivisionService.deletePointDivisionByThemeId(id);
    }

}
