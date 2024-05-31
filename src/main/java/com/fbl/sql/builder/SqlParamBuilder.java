/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.sql.builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.fbl.common.date.TimeZoneUtil;
import com.fbl.common.enums.TextEnum;
import com.fbl.common.page.domain.PageParam;
import com.fbl.common.search.CommonParam;
import com.fbl.common.search.SearchField;
import com.fbl.common.search.SearchFieldParams;
import com.fbl.common.search.SearchParam;
import com.fbl.common.util.CommonUtil;

/**
 * Sql builder to create all query binding parameters for making querys to the
 * database.
 * 
 * @author Sam Butler
 * @since Februrary 2, 2022
 */
public class SqlParamBuilder {
    private MapSqlParameterSource sqlParams;
    private CommonParam commonParam;

    private SqlParamBuilder(CommonParam commonParam, MapSqlParameterSource sqlParams) {
        this.commonParam = commonParam;
        if (sqlParams == null) {
            this.sqlParams = new MapSqlParameterSource();
        } else {
            this.sqlParams = sqlParams;
        }
    }

    /**
     * Initialize the {@link SqlParamBuilder}.
     * 
     * @return {@link SqlParamBuilder} for an empty object.
     */
    public static SqlParamBuilder with() {
        return new SqlParamBuilder(null, null);
    }

    /**
     * Initialize the {@link SqlParamBuilder} with no starting params.
     * 
     * @return {@link SqlParamBuilder} for an empty object.
     */
    public static SqlParamBuilder with(CommonParam commonParam) {
        return new SqlParamBuilder(commonParam, null);
    }

    /**
     * Initialize the {@link SqlParamBuilder} with the given starting params.
     * 
     * @param params {@link MapSqlParameterSource} used for adding extra parameters.
     * @return {@link SqlParamBuilder} with the starting parameters
     */
    public static SqlParamBuilder with(CommonParam commonParam, MapSqlParameterSource params) {
        return new SqlParamBuilder(commonParam, params);
    }

    /**
     * Adds the param name with a null value
     * 
     * @param name The name of the parameter.
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParamNullValue(String name) {
        this.sqlParams.addValue(name, null);
        return this;
    }

    /**
     * Add {@link Object} parameter to the sql map.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter.
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParam(String name, Object value) {
        this.sqlParams.addValue(name, value);
        return this;
    }

    /**
     * Add {@link TextEnum} parameter to sql map and check that the text enum is not
     * null, if not get the text id.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParam(String name, TextEnum value) {
        return withParam(name, value == null ? null : value.getTextId());
    }

    /**
     * Add {@link Boolean} parameter to sql map and check that the text enum is not
     * null, if not get the text id.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParam(String name, Boolean value) {
        return withParam(name, value == null ? null : value ? 1 : 0);
    }

    /**
     * Add {@link LocalDateTime} parameter to sql map and check that the text enum
     * is not null, if not get the text id.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParam(String name, LocalDateTime dt) {
        return withParam(name, dt == null ? null : dt.toString());
    }

    /**
     * Add {@link LocalDate} parameter to sql map and check that the text enum is
     * not null, if not get the text id.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParam(String name, LocalDate dt) {
        return withParam(name, dt == null ? null : dt.toString());
    }

    /**
     * Add {@link LocalDateTime} parameter to sql map. If the value is null it will
     * add the default date time now value.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParamDefault(String name, LocalDateTime dt) {
        if (dt == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return withParam(name, LocalDateTime.now(TimeZoneUtil.SYSTEM_ZONE).format(formatter));
        }
        return withParam(name, dt.toString());
    }

    /**
     * Add {@link LocalDate} parameter to sql map. If the value is null it will add
     * the default date time now value.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParamDefault(String name, LocalDate dt) {
        if (dt == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return withParam(name, LocalDate.now(TimeZoneUtil.SYSTEM_ZONE).format(formatter));
        }
        return withParam(name, dt.toString());
    }

    /**
     * Add parameter to sql map for an enum collection and check that the text enum
     * is not null, if not get the text id.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParamTextEnumCollection(String name, Collection<? extends TextEnum> values) {
        return withParam(name,
                values == null ? null : values.stream().map(TextEnum::getTextId).collect(Collectors.toList()));
    }

    /**
     * Adds search capabilities to the query. If it has an instance of a
     * {@link SearchParam} then it will enable search and add the search if the
     * value exists.
     * 
     * @return {@link SqlParamBuilder} with the search ability.
     */
    public SqlParamBuilder useSearch() {
        if (!(commonParam instanceof SearchParam)) {
            return this;
        }

        SearchParam searchParam = (SearchParam) commonParam;
        Parameters.search(sqlParams, CommonUtil.formatSearch(searchParam.getSearch()));
        return this;
    }

    /**
     * These are what fields the query will search on. If search is enabled,
     * {@link SearchField} has to be on the object.
     * 
     * @return {@link SqlParamBuilder} with the search fields.
     */
    public SqlParamBuilder useSearchField() {
        if (!(commonParam instanceof SearchFieldParams)) {
            return this;
        }

        SearchFieldParams searchFieldParams = (SearchFieldParams) commonParam;
        Parameters.searchField(sqlParams, searchFieldParams.getSearchFields());
        return this;
    }

    /**
     * Adds paging ability to the request.
     * 
     * @return {@link SqlParamBuilder} with the pagenation.
     */
    public SqlParamBuilder usePagenation() {
        if (!(commonParam instanceof PageParam)) {
            return this;
        }

        PageParam pageParam = (PageParam) commonParam;

        if (pageParam.getPageSize() == null || pageParam.getPageSize() == 0) {
            return this;
        }

        this.sqlParams.addValue("pageSize", pageParam.getPageSize());
        this.sqlParams.addValue("rowOffset", pageParam.getRowOffset());
        return this;
    }

    /**
     * Will add all search field capabilities to the sql builder.
     * 
     * @return {@link SqlParamBuilder} object.
     */
    public SqlParamBuilder useAllParams() {
        usePagenation();
        useSearch();
        return useSearchField();
    }

    /**
     * Retrieve {@link MapSqlParameterSource} object for the given builder.
     * 
     * @return {@link MapSqlParameterSource}
     */
    public MapSqlParameterSource build() {
        return sqlParams;
    }
}
