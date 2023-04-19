/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.awana.app.clubber.client.domain.request;

import com.awana.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Defines search fields for a clubber.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Schema(description = "The clubber search fields.")
public enum ClubberSearchFields implements SearchField {
    FIRST_NAME("c.first_name"),
    LAST_NAME("c.last_name");

    private String column;

    ClubberSearchFields(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
