/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.client.domain;

import com.fbl.common.enums.WebRole;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Web Role Feature object to map
 * 
 * @author Sam Butler
 * @since May 20, 2021
 */
@Getter
@Setter
@Schema(description = "Web Role Feature access object.")
public class WebRoleFeature extends CRUD {

    @Schema(description = "The web role of the feature")
    private WebRole webRole;

    @Schema(description = "The id of the feature")
    private Integer featureId;

    @Schema(description = "The application name.")
    private String app;

    @Schema(description = "The feature name of the application.")
    private String feature;
}
