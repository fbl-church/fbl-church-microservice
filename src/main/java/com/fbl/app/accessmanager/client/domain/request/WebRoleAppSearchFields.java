/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.client.domain.request;

import com.fbl.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Defines search fields for a web role app.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Getter
@Schema(description = "The web role app search fields.")
public enum WebRoleAppSearchFields implements SearchField {
    WEB_ROLE("wraa.web_role"),
    APP_KEY("a.key"),
    APP_DISPLAY_NAME("a.display_name");

    private String column;

    WebRoleAppSearchFields(String column) {
        this.column = column;
    }
}
