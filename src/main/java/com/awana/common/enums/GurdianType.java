package com.awana.common.enums;

/**
 * Enum to map clubber gurdian types to objects.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public enum GurdianType implements TextEnum {
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

    private GurdianType(String textId) {
        this.textId = textId;
    }

    @Override
    public String getTextId() {
        return textId;
    }
}
