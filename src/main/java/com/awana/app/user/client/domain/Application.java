/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.client.domain;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a User Application object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "The application object.")
public class Application {

    @Schema(description = "The unique id of the application.")
    private int id;

    @Schema(description = "The application name.")
    private String name;

    @Schema(description = "The application access.")
    private boolean access;

    @Schema(description = "The enabled status of the application.")
    private boolean enabled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
