/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.enums;

import java.util.Comparator;
import java.util.List;

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
    LEADER("AWANA_LEADER", 300),
    WORKER("AWANA_WORKER", 200),
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

    /**
     * Checks to see if the accessing user has permission to modify the user passed
     * in.
     * 
     * @param accessingUser The user that wants access
     * @param user          The user to check access against
     * @return Boolean determining if the accessing user has permission to the user
     */
    public static boolean hasPermission(List<WebRole> accessingUser, List<WebRole> user) {
        return highestRoleRank(accessingUser).getRank() > highestRoleRank(user).getRank();
    }

    /**
     * Takes in a list of roles and returns the highest rank of the list.
     * 
     * @param roles The list of roles
     * @return The highest ranking role
     */
    public static WebRole highestRoleRank(List<WebRole> roles) {
        return roles.stream().max(Comparator.comparing(WebRole::getRank)).get();
    }
}