/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.client.domain;

import java.time.LocalDateTime;

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
@Schema(description = "Feature access object.")
public class Feature {

    @Schema(description = "The feature id.")
    private int id;

    @Schema(description = "The application name.")
    private String app;

    @Schema(description = "The feature name of the application.")
    private String feature;

    @Schema(description = "Access the role has to the feature.")
    private String access;

    @Schema(description = "When the feature was created")
    private LocalDateTime insertDate;
}
