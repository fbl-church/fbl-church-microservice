/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.guardian.client.domain.request;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fbl.app.user.client.domain.request.UserSearchFields;
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
@Schema(description = "Guardian get request object for filtering users.")
public class GuardianGetRequest implements SearchParam, PageParam, SearchFieldParams {

    @Schema(description = "List of guardian ids.")
    private Set<Integer> id;

    @Schema(description = "List of guardian first names.")
    private Set<String> firstName;

    @Schema(description = "List of guardian last names.")
    private Set<String> lastName;

    @Schema(description = "List of guardian emails.")
    private Set<String> email;

    @Schema(description = "List of guardian phones.")
    private Set<String> phone;

    @Schema(description = "Search Param on search param fields.")
    private String search;

    @Schema(description = "Row Offset for pagenation.")
    private int rowOffset;

    @Schema(description = "Page Size for pagenation result.")
    private int pageSize;

    @JsonIgnore
    @Override
    public List<SearchField> getSearchFields() {
        return Arrays.asList(UserSearchFields.values());
    }
}