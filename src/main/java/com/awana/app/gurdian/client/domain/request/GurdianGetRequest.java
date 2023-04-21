/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.gurdian.client.domain.request;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.awana.common.page.domain.PageParam;
import com.awana.common.search.SearchField;
import com.awana.common.search.SearchFieldParams;
import com.awana.common.search.SearchParam;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This class handles lookups passed to the DAO.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
@Schema(description = "Gurdian get request object for filtering users.")
public class GurdianGetRequest implements SearchParam, PageParam, SearchFieldParams<GurdianSearchFields> {

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
        return Arrays.asList(GurdianSearchFields.values());
    }
}