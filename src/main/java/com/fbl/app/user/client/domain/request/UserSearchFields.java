/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.client.domain.request;

import com.fbl.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Defines search fields for a user.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Schema(description = "The user profile search fields.")
public enum UserSearchFields implements SearchField {
    FIRST_NAME("u.first_name"),
    LAST_NAME("u.last_name"),
    EMAIL("u.email");

    private String column;

    UserSearchFields(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
