/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.client.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Class to create a User Application object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Getter
@Setter
@Schema(description = "The application object.")
public class Application {

    @Schema(description = "The unique id of the application.")
    private int id;

    @Schema(description = "The application key.")
    @NotNull(message = "Invalid key: Can not be null")
    private String key;

    @Schema(description = "The application display name.")
    @NotNull(message = "Invalid name: Can not be null")
    private String displayName;

    @Schema(description = "The enabled status of the application.")
    private boolean enabled;
}
