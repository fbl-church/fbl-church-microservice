/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.cloudinary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fbl.app.cloudinary.service.CloudinaryService;
import com.fbl.common.annotations.interfaces.RestApiController;

/**
 * Cloudinary Controller
 *
 * @author Sam Butler
 * @since 8/3/2020
 */
@RequestMapping("/api/cloud")
@RestApiController
public class CloudinaryController {

    @Autowired
    private CloudinaryService service;

    /**
     * Gets a page of applications
     * 
     * @param request The request to filter applications
     * @return A page of applications
     * @throws Exception
     */
    @PostMapping
    public void get(@RequestParam("file") MultipartFile file) throws Exception {
        service.store(file);
    }
}
