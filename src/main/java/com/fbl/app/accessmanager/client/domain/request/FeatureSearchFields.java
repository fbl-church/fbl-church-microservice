/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.client.domain.request;

import com.fbl.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Defines search fields for a web role feature.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Getter
@Schema(description = "The feature search fields.")
public enum FeatureSearchFields implements SearchField {
    FEATURE_APP("a.display_name"),
    FEATURE_NAME("f.key");

    private String column;

    FeatureSearchFields(String column) {
        this.column = column;
    }
}
