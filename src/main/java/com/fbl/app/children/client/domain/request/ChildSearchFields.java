/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.children.client.domain.request;

import com.fbl.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Defines search fields for a child.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Schema(description = "The child search fields.")
public enum ChildSearchFields implements SearchField {
    FIRST_NAME("c.first_name"),
    LAST_NAME("c.last_name");

    private String column;

    ChildSearchFields(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
