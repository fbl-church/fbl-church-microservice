/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.enums;

/**
 * Enum to handle the status of a attendance records.
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
public enum AttendanceStatus implements TextEnum {
    ACTIVE("PENDING"),
    CLOSED("APPROVED"),
    FINALIZED("DENIED");

    private String textId;

    AttendanceStatus(String textId) {
        this.textId = textId;
    }

    @Override
    public String getTextId() {
        return textId;
    }
}
