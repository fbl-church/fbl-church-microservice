package com.fbl.app.accessmanager.client.domain.request;

import com.fbl.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Defines search fields for a application.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Getter
@Schema(description = "The user profile search fields.")
public enum ApplicationSearchFields implements SearchField {
    APP_NAME("a.app_name");

    private String column;

    ApplicationSearchFields(String column) {
        this.column = column;
    }
}
