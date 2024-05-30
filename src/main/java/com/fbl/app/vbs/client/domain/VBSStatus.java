/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.vbs.client.domain;

import com.fbl.common.enums.TextEnum;

/**
 * Enum to handle the status of a vbs theme.
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
public enum VBSStatus implements TextEnum {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    CLOSED("COMPLETE");

    private String textId;

    VBSStatus(String textId) {
        this.textId = textId;
    }

    @Override
    public String getTextId() {
        return textId;
    }
}
