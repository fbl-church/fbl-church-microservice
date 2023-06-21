/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.enums;

/**
 * Enums for all the possible user roles.
 * 
 * @author Sam Butler
 * @since September 6, 2021
 */
public enum WebRole implements TextEnum {
    USER("USER", 100),
    NURSERY_DIRECTOR("NURSERY_DIRECTOR", 300),
    NURSERY_WORKER("NURSERY_WORKER", 200),
    JUNIOR_CHURCH_DIRECTOR("JUNIOR_CHURCH_DIRECTOR", 300),
    JUNIOR_CHURCH_WORKER("JUNIOR_CHURCH_WORKER", 200),
    VBS_DIRECTOR("VBS_DIRECTOR", 300),
    VBS_WORKER("VBS_WORKER", 200),
    AWANA_DIRECTOR("AWANA_DIRECTOR", 400),
    AWANA_LEADER("AWANA_LEADER", 300),
    AWANA_WORKER("AWANA_WORKER", 200),
    TNT_LEADER("TNT_LEADER", 300),
    TNT_WORKER("TNT_WORKER", 200),
    SPARKS_LEADER("SPARKS_LEADER", 300),
    SPARKS_WORKER("SPARKS_WORKER", 200),
    CUBBIES_LEADER("CUBBIES_LEADER", 300),
    CUBBIES_WORKER("CUBBIES_WORKER", 200),
    CROSS_CHECK_LEADER("CROSS_CHECK_LEADER", 300),
    CROSS_CHECK_WORKER("CROSS_CHECK_WORKER", 200),
    SITE_ADMIN("SITE_ADMIN", 500),
    ADMIN("ADMIN", 1000);

    private String textId;
    private int rank;

    WebRole(String textId, int rank) {
        this.rank = rank;
        this.textId = textId;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String getTextId() {
        return textId;
    }
}