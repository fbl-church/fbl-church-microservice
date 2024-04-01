package com.fbl.common.enums;

/**
 * Description
 * 
 * @author Sam Butler
 * @since Apr 01, 2024
 */
public enum ThemeType implements TextEnum {
    LIGHT("LIGHT"), DARK("DARK");

    private String textId;

    ThemeType(String textId) {
        this.textId = textId;
    }

    @Override
    public String getTextId() {
        return textId;
    }
}
