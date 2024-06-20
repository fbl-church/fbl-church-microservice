/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.vbs.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.client.domain.request.VBSThemeGetRequest;
import com.fbl.app.vbs.openapi.TagVBS;
import com.fbl.app.vbs.service.ManageVBSThemeService;
import com.fbl.app.vbs.service.VBSThemeService;
import com.fbl.common.annotations.interfaces.RestApiController;
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
     * The theme to be created
     * 
     * @param theme The theme to create
     */
    @PostMapping
    public VBSTheme createTheme(@RequestBody @Valid VBSTheme theme) {
        return manageVBSThemeService.createTheme(theme);
    }
}
