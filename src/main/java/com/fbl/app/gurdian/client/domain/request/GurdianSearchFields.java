/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.gurdian.client.domain.request;

import com.fbl.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Defines search fields for a gurdian.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Schema(description = "The gurdian search fields.")
public enum GurdianSearchFields implements SearchField {
    FIRST_NAME("g.first_name"),
    LAST_NAME("g.last_name"),
    EMAIL("g.email");

    private String column;

    GurdianSearchFields(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
