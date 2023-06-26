/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.gurdian.client.domain.request;

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

/**
 * This class handles lookups passed to the DAO.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
@Schema(description = "Gurdian get request object for filtering users.")
public class GurdianGetRequest implements SearchParam, PageParam, SearchFieldParams<UserSearchFields> {

    @Schema(description = "List of gurdian ids.")
    private Set<Integer> id;

    @Schema(description = "List of gurdian first names.")
    private Set<String> firstName;

    @Schema(description = "List of gurdian last names.")
    private Set<String> lastName;

    @Schema(description = "List of gurdian emails.")
    private Set<String> email;

    @Schema(description = "List of gurdian phones.")
    private Set<String> phone;

    @Schema(description = "Search Param on search param fields.")
    private String search;

    @Schema(description = "Row Offset for pagenation.")
    private int rowOffset;

    @Schema(description = "Page Size for pagenation result.")
    private int pageSize;

    public Set<Integer> getId() {
        return id;
    }

    public void setId(Set<Integer> id) {
        this.id = id;
    }

    public Set<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(Set<String> firstName) {
        this.firstName = firstName;
    }

    public Set<String> getLastName() {
        return lastName;
    }

    public void setLastName(Set<String> lastName) {
        this.lastName = lastName;
    }

    public Set<String> getEmail() {
        return email;
    }

    public void setEmail(Set<String> email) {
        this.email = email;
    }

    public Set<String> getPhone() {
        return phone;
    }

    public void setPhone(Set<String> phone) {
        this.phone = phone;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public void setRowOffset(int rowOffset) {
        this.rowOffset = rowOffset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @JsonIgnore
    @Override
    public List<SearchField> getSearchFields() {
        return Arrays.asList(UserSearchFields.values());
    }
}