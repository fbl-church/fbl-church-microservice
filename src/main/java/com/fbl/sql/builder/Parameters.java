/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.sql.builder;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.Assert;

import com.fbl.common.search.SearchField;

/**
 * Paramters class for setting up sql search values.
 * 
 * @author Sam Butler
 * @since April 22, 2023
 */
public class Parameters {
    private static final String SEARCH = "search";
    private static final String SEARCH_SIZE = "searchSize";

    public static MapSqlParameterSource search(MapSqlParameterSource params, String search) {
        Assert.notNull(params, "MapSqlParameterSource must not be null");

        int count = 0;

        if (search != null) {
            for (String searchName : search.split(" ")) {
                // filter out any whitespace searches
                if (searchName.trim().length() > 0) {
                    String paramName = SEARCH + count;
                    params.addValue(paramName, "%" + searchName + "%");
                    count++;
                } else {
                    continue;
                }
            }
            params.addValue(SEARCH, count != 0).addValue(SEARCH_SIZE, count);
        }
        params.addValue(SEARCH_SIZE, count);
        return params;
    }

    public static void searchField(MapSqlParameterSource params, List<? extends SearchField> fields) {
        Assert.notNull(params, "MapSqlParameterSource must not be null");

        int searchSize = Integer.valueOf(params.getValue(SEARCH_SIZE).toString());
        String searchFieldSql = searchSize > 0 ? "(" : "";
        for (int i = 0; i < searchSize; i++) {
            for (int j = 0; j < fields.size(); j++) {
                searchFieldSql += String.format("%s LIKE :search%d", fields.get(j).getColumn(), i);
                searchFieldSql += j == fields.size() - 1 ? "" : " OR ";
            }
            searchFieldSql += i == searchSize - 1 ? ")" : ") AND (";
        }

        params.addValue("searchContent", searchFieldSql.trim());
    }

}
