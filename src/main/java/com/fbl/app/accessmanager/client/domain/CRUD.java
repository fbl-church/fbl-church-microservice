/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.client.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * CRUD object
 * 
 * @author Sam Butler
 * @since May 20, 2021
 */
@Getter
@Setter
@Schema(description = "CRUD object.")
public class CRUD {
    @Schema(description = "The create flag")
    private Boolean create;

    @Schema(description = "The read flag")
    private Boolean read;

    @Schema(description = "The update flag")
    private Boolean update;

    @Schema(description = "The delete flag")
    private Boolean delete;
}
