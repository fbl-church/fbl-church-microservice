/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.vbs.client.domain.request;

import com.fbl.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Defines search fields for a vbs guardian children.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Schema(description = "The VBS guardian children search fields.")
public enum VBSGuardianChildrenSearchFields implements SearchField {
    EMAIL("gu.email"),
    PHONE("g.phone");

    private String column;

    VBSGuardianChildrenSearchFields(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
