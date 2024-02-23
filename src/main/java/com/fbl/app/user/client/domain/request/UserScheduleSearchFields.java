/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.client.domain.request;

import com.fbl.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Defines search fields for a user schedule.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Schema(description = "The user schedule search fields.")
public enum UserScheduleSearchFields implements SearchField {
    NAME("ar.name"),
    STATUS("ar.status"),
    TYPE("ar.type");

    private String column;

    UserScheduleSearchFields(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
