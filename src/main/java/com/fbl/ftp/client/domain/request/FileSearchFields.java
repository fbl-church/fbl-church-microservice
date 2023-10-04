/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.ftp.client.domain.request;

import com.fbl.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Defines search fields for a file.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Schema(description = "The file search fields.")
public enum FileSearchFields implements SearchField {
    NAME("name");

    private String column;

    FileSearchFields(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}