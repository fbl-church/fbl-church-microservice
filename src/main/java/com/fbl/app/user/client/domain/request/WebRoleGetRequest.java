/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.client.domain.request;

import java.util.Set;

import com.fbl.common.page.domain.PageParam;
import com.fbl.common.search.SearchParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
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
@Builder
@Schema(description = "WebRole get request object for filtering users.")
public class WebRoleGetRequest implements SearchParam, PageParam {

    @Schema(description = "Search Param on search param fields.")
    private Set<String> search;

    @Schema(description = "Row Offset for pagenation.")
    private int rowOffset;

    @Schema(description = "Page Size for pagenation result.")
    private int pageSize;
}