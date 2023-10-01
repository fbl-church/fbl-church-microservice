/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.cloudinary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.fbl.environment.EnvironmentService;

/**
 * Cloudinary Service
 *
 * @author Sam Butler
 * @since 8/2/2020
 */
@Service
public class CloudinaryService {

    @Autowired
    private EnvironmentService environmentService;

    public void get() throws Exception {
        Cloudinary cloud = new Cloudinary(environmentService.getCloudinaryUrl());
        ApiResponse res = cloud.api().resource("cld-sample-5", ObjectUtils.emptyMap());
        int test = 1;
    }

}
