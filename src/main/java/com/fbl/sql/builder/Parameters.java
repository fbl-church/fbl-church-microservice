/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.sql.builder;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fbl.common.search.SearchField;

/**
 * Paramters class for setting up sql search values.
 * 
 * @author Sam Butler
 * @since April 22, 2023
 */
public class Parameters {
    private static final String SEARCH = "search";
    private static final String SEARCH_VALUE = "searchValue";
    private static final String SEARCH_SIZE = "searchSize";

    public static MapSqlParameterSource search(MapSqlParameterSource params, Set<String> searchValues) {
        Assert.notNull(params, "MapSqlParameterSource must not be null");

        int count = 0;
        int subCount = 0;

        if (searchValues != null && searchValues.size() > 0) {
            Set<String> filteredSearch = searchValues.stream().map(String::trim).filter(StringUtils::hasText)
                    .collect(Collectors.toSet());
            for (String value : filteredSearch) {
                String[] nestedValues = isPhoneFormat(value) ? new String[] { value } : value.split(" ");
                for (String searchName : nestedValues) {
                    String paramName = String.format("%s%d_%d", SEARCH_VALUE, count, subCount);
                    params.addValue(paramName, "%" + searchName + "%");
                    subCount++;
                }
                count++;
                subCount = 0;
            }

            params.addValue(SEARCH, count != 0);
        }
        params.addValue(SEARCH_SIZE, count);
        return params;
    }

    public static void searchField(MapSqlParameterSource params, List<? extends SearchField> fields) {
        Assert.notNull(params, "MapSqlParameterSource must not be null");

        int searchSize = Integer.valueOf(params.getValue(SEARCH_SIZE).toString());
        List<String> searchValueNames = Arrays.asList(params.getParameterNames()).stream()
                .filter(s -> s.startsWith(SEARCH_VALUE)).collect(Collectors.toList());

        String searchFieldSql = searchSize > 0 ? "\n(" : "";
        for (int i = 0; i < searchSize; i++) {
            List<String> nestedSearchValues = getSubSearchValues(searchValueNames, i);
            for (int j = 0; j < nestedSearchValues.size(); j++) {
                searchFieldSql += buildSearchFieldSection(fields, nestedSearchValues, j);
                searchFieldSql += closeFieldSection(nestedSearchValues, j);
            }
            searchFieldSql += i == searchSize - 1 ? "" : " OR\n(";
        }

        params.addValue("searchContent", searchFieldSql.trim());
    }

    private static List<String> getSubSearchValues(List<String> searchValues, int index) {
        return searchValues.stream().filter(s -> s.startsWith(String.format("%s%d", SEARCH_VALUE, index)))
                .collect(Collectors.toList());
    }

    private static String buildSearchFieldSection(List<? extends SearchField> fields, List<String> values, int index) {
        String statement = values.size() > 1 ? "\n\t(" : "";
        for (int k = 0; k < fields.size(); k++) {
            statement += String.format("%s LIKE :%s", fields.get(k).getColumn(), values.get(index));
            statement += k == fields.size() - 1 ? ")" : " OR ";
        }
        return statement;
    }

    private static String closeFieldSection(List<String> values, int index) {
        if (values.size() > 1) {
            return index == values.size() - 1 ? "\n)\n" : "\n\tAND";
        }
        return "\n";
    }

    private static boolean isPhoneFormat(String search) {
        return search.matches("\\(\\d{3}\\)\\s{1}\\d{3}-\\d{4}");
    }
}
