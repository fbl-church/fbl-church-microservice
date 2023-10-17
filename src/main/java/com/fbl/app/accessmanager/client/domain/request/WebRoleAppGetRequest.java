/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.accessmanager.client.domain.request;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.domain.PageParam;
import com.fbl.common.search.SearchField;
import com.fbl.common.search.SearchFieldParams;
import com.fbl.common.search.SearchParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Web Role App get request object for filtering features.")
public class WebRoleAppGetRequest implements SearchParam, PageParam, SearchFieldParams {

    @Schema(description = "Set of roles to filter on")
    private Set<WebRole> webRole;

    @Schema(description = "Search Param on search param fields.")
    private Set<String> search;

    @Schema(description = "Row Offset for pagenation.")
    private int rowOffset;

    @Schema(description = "Page Size for pagenation result.")
    private int pageSize;

    @JsonIgnore
    @Override
    public List<SearchField> getSearchFields() {
        return Arrays.asList(WebRoleAppSearchFields.values());
    }
}
