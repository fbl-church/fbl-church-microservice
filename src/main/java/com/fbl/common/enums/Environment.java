/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.enums;

import java.util.Arrays;

import org.springframework.util.Assert;

/**
 * Enum to map environments to objects.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public enum Environment implements TextEnum {
    PRODUCTION("P", "https://www.fbl-church.com"),
    DEVELOPMENT("D", "http://localhost:4200"),
    TEST("T", "http://localhost:4200"),
    LOCAL("L", "http://localhost:4200");

    private String textId;
    private String url;

    private Environment(String textId, String url) {
        this.textId = textId;
        this.url = url;
    }

    @Override
    public String getTextId() {
        return textId;
    }

    public String getUrl() {
        return url;
    }

    /**
     * Will get the environment object enum from the passed in text value. If the
     * enum is invalid it will return the {@link Environment#LOCAL} environment by
     * default.
     * 
     * @param text The text to process.
     * @return {@link Environment} Object
     */
    public static Environment get(String text) {
        Assert.notNull(text, "Text ID can not be null");
        return Arrays.asList(Environment.values()).stream().filter(e -> e.getTextId().equals(text.toUpperCase()))
                .findAny().orElse(LOCAL);
    }
}