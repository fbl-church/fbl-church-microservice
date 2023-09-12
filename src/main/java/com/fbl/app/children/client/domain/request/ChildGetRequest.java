/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.children.client.domain.request;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.common.page.domain.PageParam;
import com.fbl.common.search.SearchField;
import com.fbl.common.search.SearchFieldParams;
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
@Schema(description = "User get request object for filtering users.")
public class ChildGetRequest implements SearchParam, PageParam, SearchFieldParams<ChildSearchFields> {

    @Schema(description = "List of user ids.")
    private Set<Integer> id;

    @Schema(description = "List of user first names.")
    private Set<String> firstName;

    @Schema(description = "List of user last names.")
    private Set<String> lastName;

    @Schema(description = "List of user web roles.", allowableValues = "USER,ADMIN")
    private Set<ChurchGroup> churchGroup;

    @Schema(description = "List of church groups not to return")
    private Set<ChurchGroup> notChurchGroup;

    @Schema(description = "Search Param on search param fields.")
    private String search;

    @Schema(description = "Row Offset for pagenation.")
    private int rowOffset;

    @Schema(description = "Page Size for pagenation result.")
    private int pageSize;

    @JsonIgnore
    @Override
    public List<SearchField> getSearchFields() {
        return Arrays.asList(ChildSearchFields.values());
    }
}