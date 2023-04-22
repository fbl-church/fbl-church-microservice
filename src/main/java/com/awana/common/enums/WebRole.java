/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.common.enums;

/**
 * Enums for all the possible user roles.
 * 
 * @author Sam Butler
 * @since September 6, 2021
 */
public enum WebRole implements TextEnum {
    USER("USER", 100),
    LEADER("LEADER", 300),
    HELPER("HELPER", 200),
    TNT_LEADER("TNT_LEADER", 300),
    TNT_HELPER("TNT_HELPER", 200),
    SPARKS_LEADER("SPARKS_LEADER", 300),
    SPARKS_HELPER("SPARKS_HELPER", 200),
    CUBBIES_LEADER("CUBBIES_LEADER", 300),
    CUBBIES_HELPER("CUBBIES_HELPER", 200),
    CROSS_CHECK_LEADER("CROSS_CHECK_LEADER", 300),
    CROSS_CHECK_HELPER("CROSS_CHECK_HELPER", 200),
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