/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.ftp.client.domain.request;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fbl.common.page.domain.PageParam;
import com.fbl.common.search.SearchField;
import com.fbl.common.search.SearchFieldParams;
import com.fbl.common.search.SearchParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * File Get Request.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
@Getter
@Setter
@Schema(description = "File get request for filtering files")
public class FileGetRequest implements SearchParam, PageParam, SearchFieldParams {
    @Schema(description = "Path to pull files from")
    private String path;

    @Schema(description = "Search Param on search param fields.")
    private Set<String> search;

    @Schema(description = "Row Offset for pagenation.")
    private Integer rowOffset;

    @Schema(description = "Page Size for pagenation result.")
    private Integer pageSize;

    @JsonIgnore
    @Override
    public List<SearchField> getSearchFields() {
        return Arrays.asList(FileSearchFields.values());
    }
}
