/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.client.domain.request;

import com.fbl.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Defines search fields for an attendance record.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Schema(description = "The attendance record search fields.")
public enum AttendanceRecordSearchFields implements SearchField {
    FIRST_NAME("ar.name");

    private String column;

    AttendanceRecordSearchFields(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
