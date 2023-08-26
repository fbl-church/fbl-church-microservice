package com.fbl.app.children.client.domain.request;

import com.fbl.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Defines search fields for a child attendance.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Schema(description = "The child attendance search fields.")
public enum ChildAttendanceSearchFields implements SearchField {
    FIRST_NAME("u.first_name"),
    LAST_NAME("u.last_name");

    private String column;

    ChildAttendanceSearchFields(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
