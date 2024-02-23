/*
 * Copyright of FBL Church App. All rights reserved.
*/
package com.fbl.app.user.client.domain.request;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fbl.common.page.domain.PageParam;
import com.fbl.common.search.SearchField;
import com.fbl.common.search.SearchFieldParams;
import com.fbl.common.search.SearchParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Description
 *
 * @author Sam Butler
 * @since Feb 14, 2024 02
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User get request object for filtering users schedules.")
public class UserScheduleRequest implements SearchParam, PageParam, SearchFieldParams {
    @Schema(description = "List of user ids.")
    private Set<Integer> userId;

    @Schema(description = "List of months to filter by")
    private Set<Integer> months;

    @Schema(description = "Search Param on search param fields.")
    private Set<String> search;

    @Schema(description = "Row Offset for pagenation.")
    private Integer rowOffset;

    @Schema(description = "Page Size for pagenation result.")
    private Integer pageSize;

    @JsonIgnore
    @Override
    public List<SearchField> getSearchFields() {
        return Arrays.asList(UserScheduleSearchFields.values());
    }
}
