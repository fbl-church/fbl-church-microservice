/**
 * Copyright of FBL Church App. All rights reserved.
 */
/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.page;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Page class to add total count header.
 * 
 * @author Sam Butler
 * @since January 22, 2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Page obejct for holding list and total count.")
public class Page<T> {
    public static final String TOTAL_ITEM_COUNT = "total-count";

    @Schema(description = "Total count of items in the list.")
    private long totalCount;

    @Schema(description = "The list of generic objects.")
    private List<T> list;
}
