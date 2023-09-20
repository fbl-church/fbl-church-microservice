/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.client.domain;

import com.fbl.common.enums.WebRole;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Web Role App object to map
 * 
 * @author Sam Butler
 * @since May 20, 2021
 */
@Getter
@Setter
@Schema(description = "Web Role App access object.")
public class WebRoleApp {

    @Schema(description = "The web role of the app")
    private WebRole webRole;

    @Schema(description = "The application id.")
    private int appId;

    @Schema(description = "The access the web role has to the application")
    private boolean access;
}
