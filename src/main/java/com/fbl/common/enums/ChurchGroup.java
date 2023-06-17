/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.enums;

/**
 * Enums for all the possible church groups.
 * 
 * @author Sam Butler
 * @since September 6, 2021
 */
public enum ChurchGroup implements TextEnum {
    CUBBIES("CUBBIES"),
    SPARKS("SPARKS"),
    TNT_GIRLS("TNT_GIRLS"),
    TNT_BOYS("TNT_BOYS"),
    CROSS_CHECK("CROSS_CHECK");

    private String textId;

    ChurchGroup(String textId) {
        this.textId = textId;
    }

    @Override
    public String getTextId() {
        return textId;
    }
}
