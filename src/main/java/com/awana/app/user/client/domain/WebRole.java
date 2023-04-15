package com.awana.app.user.client.domain;

import com.awana.common.dictionary.enums.TextEnum;

/**
 * Enums for all the possible user roles.
 * 
 * @author Sam Butler
 * @since September 6, 2021
 */
public enum WebRole implements TextEnum {
    USER(1, "USER", 100),
    TNT_LEADER(2, "TNT_LEADER", 300),
    TNT_HELPER(3, "TNT_HELPER", 200),
    SPARKS_LEADER(4, "SPARKS_LEADER", 300),
    SPARKS_HELPER(5, "SPARKS_HELPER", 200),
    CUBBIES_LEADER(6, "CUBBIES_LEADER", 300),
    CUBBIES_HELPER(7, "CUBBIES_HELPER", 200),
    SITE_ADMIN(8, "SITE_ADMIN", 500),
    ADMIN(9, "ADMIN", 1000);

    private int id;
    private String textId;
    private int rank;

    WebRole(int id, String textId, int rank) {
        this.id = id;
        this.rank = rank;
        this.textId = textId;
    }

    public static WebRole getRole(int id) {
        for(WebRole w : WebRole.values())
            if(w.id == id) return w;
        return USER;
    }

    public int getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String getTextId() {
        return textId;
    }
}