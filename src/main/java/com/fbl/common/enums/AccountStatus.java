/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.enums;

/**
 * Enum to handle the status of a users account.
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
public enum AccountStatus implements TextEnum {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    DENIED("DENIED"),
    INACTIVE("INACTIVE");

    private String textId;

    AccountStatus(String textId) {
        this.textId = textId;
    }

    @Override
    public String getTextId() {
        return textId;
    }
}
