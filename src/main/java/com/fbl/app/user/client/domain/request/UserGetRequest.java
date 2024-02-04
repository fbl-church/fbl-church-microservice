/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.client.domain.request;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fbl.common.enums.AccountStatus;
import com.fbl.common.enums.WebRole;
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
 * This class handles lookups passed to the DAO.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User get request object for filtering users.")
public class UserGetRequest implements SearchParam, PageParam, SearchFieldParams {

    @Schema(description = "List of user ids.")
    private Set<Integer> id;

    @Schema(description = "List of user first names.")
    private Set<String> firstName;

    @Schema(description = "List of user last names.")
    private Set<String> lastName;

    @Schema(description = "List of emails.")
    private Set<String> email;

    @Schema(description = "List of user web roles.")
    private Set<WebRole> webRole;

    @Schema(description = "List of user web roles not to return")
    private Set<WebRole> notWebRole;

    @Schema(description = "Status of the user account")
    private AccountStatus status;

    @Schema(description = "Search Param on search param fields.")
    private Set<String> search;

    @Schema(description = "Row Offset for pagenation.")
    private Integer rowOffset;

    @Schema(description = "Page Size for pagenation result.")
    private Integer pageSize;

    @JsonIgnore
    @Override
    public List<SearchField> getSearchFields() {
        return Arrays.asList(UserSearchFields.values());
    }
}