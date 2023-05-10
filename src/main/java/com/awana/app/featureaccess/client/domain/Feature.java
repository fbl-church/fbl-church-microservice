/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.featureaccess.client.domain;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Feature object to map
 * 
 * @author Sam Butler
 * @since May 20, 2021
 */
@Schema(description = "Feature access object for a user.")
public class Feature {

    @Schema(description = "The application name.")
    private String app;

    @Schema(description = "The feature name of the application.")
    private String feature;

    @Schema(description = "Access the user has to the feature.")
    private String access;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
