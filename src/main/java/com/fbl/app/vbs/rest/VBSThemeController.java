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

import com.fbl.app.vbs.client.domain.VBSStatus;
import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.client.domain.VBSThemeGroup;
import com.fbl.app.vbs.client.domain.request.VBSThemeGetRequest;
import com.fbl.app.vbs.openapi.TagVBS;
import com.fbl.app.vbs.service.ManageVBSThemeService;
import com.fbl.app.vbs.service.VBSThemeService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;

import jakarta.validation.Valid;

/**
 * VBS Theme Controller for managing endpoints
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@RequestMapping("/api/vbs/themes")
@RestApiController
@TagVBS
public class VBSThemeController {

    @Autowired
    private VBSThemeService vbsThemeService;

    @Autowired
    private ManageVBSThemeService manageVBSThemeService;

    /**
     * Gets a page of vbs themes
     * 
     * @param request The request to fitler by
     * @return The page of vbs themes
     */
    @GetMapping
    public Page<VBSTheme> getThemes(VBSThemeGetRequest request) {
        return vbsThemeService.getThemes(request);
    }

    /**
     * The theme to be created
     * 
     * @param theme The theme to create
     */
    @GetMapping("/{id}")
    public VBSTheme getThemeById(@PathVariable int id) {
        return vbsThemeService.getThemeById(id);
    }

    /**
     * Gets the latest active theme. If there is no active theme, then it will
     * return the latest theme.
     * 
     * @return The latest active theme
     */
    @GetMapping("/active")
    public VBSTheme getLatestActiveTheme() {
        return vbsThemeService.getLatestActiveTheme();
    }

    /**
     * Gets the list of vbs theme groups by theme id.
     * 
     * @param id The id of the theme
     */
    @GetMapping("/{id}/groups")
    public List<VBSThemeGroup> getThemeGroupsById(@PathVariable int id) {
        return vbsThemeService.getThemeGroupsById(id);
    }

    /**
     * Update a theme
     * 
     * @param id    The id of the theme
     * @param theme The theme to update
     * @return The updated theme
     */
    @PutMapping("/{id}")
    public VBSTheme updateThemeById(@PathVariable int id, @RequestBody VBSTheme theme) {
        return manageVBSThemeService.updateThemeById(id, theme);
    }

    /**
     * Update the status of a theme
     * 
     * @param id     The id of the theme
     * @param status The status to update with
     * @return The updated theme
     */
    @PutMapping("/{id}/status/{status}")
    @HasAccess(WebRole.VBS_DIRECTOR)
    public VBSTheme updateThemeStatus(@PathVariable int id, @PathVariable VBSStatus status) {
        return manageVBSThemeService.updateThemeStatus(id, status);
    }

    /**
     * Re-Open a closed theme
     * 
     * @param id The id of the theme
     * @return The updated theme
     */
    @PutMapping("/{id}/reopen")
    @HasAccess(WebRole.ADMINISTRATOR)
    public VBSTheme reopenTheme(@PathVariable int id) {
        return manageVBSThemeService.reopenTheme(id);
    }

    /**
     * Updates the group by theme id
     * 
     * @param id    The id of the theme
     * @param group The group to update
     */
    @PutMapping("/{id}/groups")
    public void updateGroupByThemeId(@PathVariable int id, @RequestBody @Valid VBSThemeGroup group) {
        manageVBSThemeService.updateGroupByThemeId(id, group);
    }

    /**
     * The theme to be created
     * 
     * @param theme The theme to create
     */
    @PostMapping
    public VBSTheme createTheme(@RequestBody @Valid VBSTheme theme) {
        return manageVBSThemeService.createTheme(theme);
    }

    /**
     * The theme to be deleted
     * 
     * @param id The id of the vbs theme to delete
     */
    @DeleteMapping("/{id}")
    public void deleteTheme(@PathVariable int id) {
        manageVBSThemeService.deleteTheme(id);
    }
}
