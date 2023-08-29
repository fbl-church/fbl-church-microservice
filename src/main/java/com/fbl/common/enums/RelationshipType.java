/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.enums;

/**
 * Enum to map for gurdian types to objects.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public enum RelationshipType implements TextEnum {
    MOTHER("MOTHER"),
    FATHER("FATHER"),
    BROTHER("BROTHER"),
    SISTER("SISTER"),
    AUNT("AUNT"),
    UNCLE("UNCLE"),
    GRANDMA("GRANDMA"),
    GRANDPA("GRANDPA"),
    OTHER("OTHER");

    private String textId;

    private RelationshipType(String textId) {
        this.textId = textId;
    }

    @Override
    public String getTextId() {
        return textId;
    }
}
