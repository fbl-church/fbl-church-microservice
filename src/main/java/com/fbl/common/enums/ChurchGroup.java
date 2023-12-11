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
    AWANA("AWANA"),
    CUBBIES("CUBBIES"),
    SPARKS("SPARKS"),
    TNT_GIRLS("TNT_GIRLS"),
    TNT_BOYS("TNT_BOYS"),
    CROSS_CHECK("CROSS_CHECK"),
    JUNIOR_CHURCH("JUNIOR_CHURCH"),
    NURSERY("NURSERY"),
    VBS_PRE_PRIMARY("VBS_PRE_PRIMARY"),
    VBS_PRIMARY("VBS_PRIMARY"),
    VBS_MIDDLER("VBS_MIDDLER"),
    VBS_JUNIOR("VBS_JUNIOR");

    private String textId;

    ChurchGroup(String textId) {
        this.textId = textId;
    }

    @Override
    public String getTextId() {
        return textId;
    }
}
