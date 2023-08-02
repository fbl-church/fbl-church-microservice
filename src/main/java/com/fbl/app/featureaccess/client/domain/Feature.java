/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.featureaccess.client.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Feature object to map
 * 
 * @author Sam Butler
 * @since May 20, 2021
 */
@Getter
@Setter
@Schema(description = "Feature access object for a user.")
public class Feature {

    @Schema(description = "The application name.")
    private String app;

    @Schema(description = "The feature name of the application.")
    private String feature;

    @Schema(description = "Access the user has to the feature.")
    private String access;
}
