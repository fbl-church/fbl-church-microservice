/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.children.client.domain.request;

import com.fbl.common.page.domain.PageParam;
import com.fbl.common.search.SearchParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * This class handles lookups passed to the DAO.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
@Getter
@Setter
@Schema(description = "Church Group get request object for filtering users.")
public class ChurchGroupGetRequest implements SearchParam, PageParam {

    @Schema(description = "Search Param on search param fields.")
    private String search;

    @Schema(description = "Row Offset for pagenation.")
    private int rowOffset;

    @Schema(description = "Page Size for pagenation result.")
    private int pageSize;
}