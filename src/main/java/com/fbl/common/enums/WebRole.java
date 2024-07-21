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
    ADMINISTRATOR("ADMINISTRATOR", 1000),
    AWANA_DIRECTOR("AWANA_DIRECTOR", 400),
    AWANA_LEADER("AWANA_LEADER", 300),
    AWANA_REGISTRATION("AWANA_REGISTRATION", 300),
    AWANA_WORKER("AWANA_WORKER", 200),
    CHILD("CHILD", 100),
    CROSS_CHECK_LEADER("CROSS_CHECK_LEADER", 300),
    CROSS_CHECK_WORKER("CROSS_CHECK_WORKER", 200),
    CUBBIES_LEADER("CUBBIES_LEADER", 300),
    CUBBIES_WORKER("CUBBIES_WORKER", 200),
    GUARDIAN("GUARDIAN", 100),
    JUNIOR_CHURCH_DIRECTOR("JUNIOR_CHURCH_DIRECTOR", 400),
    JUNIOR_CHURCH_SUPERVISOR("JUNIOR_CHURCH_SUPERVISOR", 300),
    JUNIOR_CHURCH_WORKER("JUNIOR_CHURCH_WORKER", 200),
    LEADER("LEADER", 300),
    MODERATOR("MODERATOR", 500),
    NURSERY_DIRECTOR("NURSERY_DIRECTOR", 400),
    NURSERY_SUPERVISOR("NURSERY_SUPERVISOR", 300),
    NURSERY_WORKER("NURSERY_WORKER", 200),
    PASTOR("PASTOR", 100),
    SITE_ADMINISTRATOR("SITE_ADMINISTRATOR", 800),
    SPARKS_LEADER("SPARKS_LEADER", 300),
    SPARKS_WORKER("SPARKS_WORKER", 200),
    TNT_LEADER("TNT_LEADER", 300),
    TNT_WORKER("TNT_WORKER", 200),
    USER("USER", 100),
    VBS_CRAFTS("VBS_CRAFTS", 200),
    VBS_DIRECTOR("VBS_DIRECTOR", 300),
    VBS_GAMES("VBS_GAMES", 200),
    VBS_JUNIOR("VBS_JUNIOR", 200),
    VBS_MIDDLER("VBS_MIDDLER", 200),
    VBS_PRE_PRIMARY("VBS_PRE_PRIMARY", 200),
    VBS_PRIMARY("VBS_PRIMARY", 200),
    VBS_REGISTRATION("VBS_REGISTRATION", 300),
    VBS_SNACKS("VBS_SNACKS", 200),
    VBS_WORKER("VBS_WORKER", 200),
    WORKER("WORKER", 200);

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
     * Checks to see if the accessing user has greater permission to modify the user
     * passed in.
     * 
     * Example:
     * 
     * <pre>
     *  ADMINISTRATOR accessing ADMINISTRATOR -> false
     *  ADMINISTRATOR accessing SITE_ADMINISTRATOR -> true
     *  SITE_ADMINISTRATOR accessing ADMINISTRATOR -> false
     * </pre>
     * 
     * @param accessingUser The user that wants access
     * @param user          The user to check access against
     * @return Boolean determining if the accessing user has permission to the user
     */
    public static boolean hasPermission(List<WebRole> accessingUser, List<WebRole> user) {
        return highestRoleRank(accessingUser).getRank() > highestRoleRank(user).getRank();
    }

    /**
     * Checks to see if the accessing user has equal or greater permisson to modify
     * the user passed in.
     * 
     * Example:
     * 
     * <pre>
     *  ADMINISTRATOR accessing ADMINISTRATOR -> true
     *  ADMINISTRATOR accessing SITE_ADMINISTRATOR -> true
     *  SITE_ADMINISTRATOR accessing ADMINISTRATOR -> false
     * </pre>
     * 
     * @param accessingUser The user that wants access
     * @param user          The user to check access against
     * @return Boolean determining if the accessing user has permission to the user
     */
    public static boolean hasEqualPermission(List<WebRole> accessingUser, List<WebRole> user) {
        return highestRoleRank(accessingUser).getRank() >= highestRoleRank(user).getRank();
    }

    /**
     * Takes in a list of roles and returns the highest rank of the list.
     * 
     * @param roles The list of roles
     * @return The highest ranking role
     */
    public static WebRole highestRoleRank(List<WebRole> roles) {
        if (roles == null) {
            return WebRole.USER;
        }
        return roles.stream().max(Comparator.comparing(WebRole::getRank)).orElse(USER);
    }
}