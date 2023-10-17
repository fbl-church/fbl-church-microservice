/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.guardian.client.domain.request;

import com.fbl.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Defines search fields for a guardian.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Schema(description = "The guardian profile search fields.")
public enum GuardianSearchFields implements SearchField {
    FIRST_NAME("u.first_name"),
    LAST_NAME("u.last_name"),
    EMAIL("u.email"),
    PHONE("g.phone");

    private String column;

    GuardianSearchFields(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
