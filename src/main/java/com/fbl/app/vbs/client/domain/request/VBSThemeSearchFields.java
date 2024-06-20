/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.vbs.client.domain.request;

import com.fbl.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Defines search fields for a vbs theme.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Schema(description = "The VBS theme search fields.")
public enum VBSThemeSearchFields implements SearchField {
    NAME("vt.name");

    private String column;

    VBSThemeSearchFields(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
