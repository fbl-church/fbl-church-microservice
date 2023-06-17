/**
 * Copyright of FBL Church App. All rights reserved.
 */
/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.sql.abstracts;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.fbl.common.page.Page;
import com.opengamma.elsql.ElSqlBundle;
import com.opengamma.elsql.ElSqlConfig;

/**
 * Abstract class for building the DAO classes and running queries against the
 * database.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Service
public abstract class AbstractSqlDao extends AbstractSqlGlobals {

    private final NamedParameterJdbcTemplate template;
    private final ElSqlBundle bundle;

    public AbstractSqlDao() {
        this.template = null;
        this.bundle = null;
    }

    public AbstractSqlDao(NamedParameterJdbcTemplate template, ElSqlConfig config) {
        this.template = template;
        this.bundle = ElSqlBundle.of(config, this.getClass());
    }

    /**
     * Gets the default template for the datasource.
     * 
     * @return {@link NamedParameterJdbcTemplate} object.
     */
    protected NamedParameterJdbcTemplate getTemplate() {
        return template;
    }

    /**
     * Does a get on the database for a single record. It will then use the given
     * mapper to serialize to an object.
     * 
     * @param <T>      The object type of the method to cast the rows too.
     * @param fragment The name of the sql fragment.
     * @param params   Params to be inserted into the query.
     * @param mapper   The mapper to return the data as.
     * @return Object of the returned data.
     */
    protected <T> T get(String fragment, MapSqlParameterSource params, RowMapper<T> mapper) {
        String sql = getSql(fragment, params);
        return getTemplate().queryForObject(sql, params, mapper);
    }

    /**
     * Does a get on the database for a single record. It will then use the given
     * mapper to serialize to an object.
     * 
     * @param <T>      The object type of the method to cast the rows too.
     * @param fragment The name of the sql fragment.
     * @param mapper   The mapper to return the data as.
     * @return Object of the returned data.
     */
    protected <T> T get(String fragment, RowMapper<T> mapper) {
        return get(fragment, new MapSqlParameterSource(), mapper);
    }

    /**
     * Does a get on the database for a single record. It will return the type of
     * the passed in class.
     * 
     * @param <T>      The object type of the method to cast the rows too.
     * @param fragment The name of the sql fragment.
     * @param params   Params to be inserted into the query.
     * @param clazz    The class to map the data as.
     * @return Object of the returned data.
     */
    protected <T> T get(String fragment, MapSqlParameterSource params, Class<T> clazz) {
        String sql = getSql(fragment, params);
        return getTemplate().queryForObject(sql, params, clazz);
    }

    /**
     * Does a get on the database for a single record. It will return the type of
     * the passed in class.
     * 
     * @param <T>      The object type of the method to cast the rows too.
     * @param fragment The name of the sql fragment.
     * @param params   Params to be inserted into the query.
     * @param clazz    The class to map the data as.
     * @return Object of the returned data.
     */
    protected <T> T get(String fragment, Class<T> clazz) {
        return get(fragment, new MapSqlParameterSource(), clazz);
    }

    /**
     * Does a get on the database for a single record. If it does not exist it will
     * return an empty {@link Optional}. Otherwise it will return the data wrapped
     * in an optional with the desired mapper.
     * 
     * @param <T>      The object type of the method to cast the rows too.
     * @param fragment The name of the sql fragment.
     * @param params   Params to be inserted into the query.
     * @param clazz    The class to map the data as.
     * @return Object of the returned data.
     */
    protected <T> Optional<T> getOptional(String fragment, MapSqlParameterSource params, Class<T> clazz) {
        try {
            return Optional.of(get(fragment, params, clazz));
        }catch(Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Does a get on the database for a single record. If it does not exist it will
     * return an empty {@link Optional}. Otherwise it will return the data wrapped
     * in an optional with the desired mapper.
     * 
     * @param <T>      The object type of the method to cast the rows too.
     * @param fragment The name of the sql fragment.
     * @param clazz    The class to map the data as.
     * @return Object of the returned data.
     */
    protected <T> Optional<T> getOptional(String fragment, Class<T> clazz) {
        try {
            return Optional.of(get(fragment, clazz));
        }catch(Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Does a get on the database for a single record. If it does not exist it will
     * return an empty {@link Optional}. Otherwise it will return the data wrapped
     * in an optional with the desired mapper.
     * 
     * @param <T>      The object type of the method to cast the rows too.
     * @param fragment The name of the sql fragment.
     * @param params   Params to be inserted into the query.
     * @param mapper   The mapper to serialize the data as.
     * @return Object of the returned data.
     */
    protected <T> Optional<T> getOptional(String fragment, MapSqlParameterSource params, RowMapper<T> mapper) {
        try {
            return Optional.of(get(fragment, params, mapper));
        }catch(Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Does a get on the database for a single record. If it does not exist it will
     * return an empty {@link Optional}. Otherwise it will return the data wrapped
     * in an optional with the desired mapper.
     * 
     * @param <T>      The object type of the method to cast the rows too.
     * @param fragment The name of the sql fragment.
     * @param mapper   The mapper to serialize the data as.
     * @return Object of the returned data.
     */
    protected <T> Optional<T> getOptional(String fragment, RowMapper<T> mapper) {
        try {
            return Optional.of(get(fragment, mapper));
        }catch(Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Querys the database for a list of data. It will return the data as a list of
     * the called mapper.
     * 
     * @param <T>      The object type of the method to cast the rows too.
     * @param fragment The name of the sql fragment.
     * @param params   Params to be inserted into the query.
     * @param mapper   The mapper to return the data as.
     * @return List of the returned data.
     */
    protected <T> List<T> getList(String fragment, MapSqlParameterSource params, RowMapper<T> mapper) {
        String sql = getSql(fragment, params);
        return getTemplate().query(sql, params, mapper);
    }

    /**
     * Querys the database for a page of data. It will return the data as a list of
     * the called object.
     * 
     * @param <T>      The object type of the method to cast the rows too.
     * @param fragment The name of the sql fragment.
     * @param params   Params to be inserted into the query.
     * @param mapper   The mapper to return the data as.
     * @return List of the returned data.
     */
    protected <T> Page<T> getPage(String fragment, MapSqlParameterSource params, RowMapper<T> mapper) {
        return getPage(fragment + "TotalCount", fragment, params, mapper);
    }

    /**
     * Querys the database for a page of data. It will return the data as a list of
     * the called object.
     * 
     * @param <T>      The object type of the method to cast the rows too.
     * @param total    The total count sql fragment.
     * @param fragment The name of the sql fragment.
     * @param params   Params to be inserted into the query.
     * @param mapper   The mapper to return the data as.
     * @return List of the returned data.
     */
    protected <T> Page<T> getPage(String total, String fragment, MapSqlParameterSource params, RowMapper<T> mapper) {
        int totalCount = get(total, params, Integer.class);
        List<T> list = getList(fragment, params, mapper);
        return new Page<T>(totalCount, list);
    }

    /**
     * Performs an update against the database.
     * 
     * @param fragment The name of the sql fragment.
     * @param params   Params to be inserted into the query.
     */
    protected int update(String fragment, MapSqlParameterSource params) {
        String sql = getSql(fragment, params);
        return getTemplate().update(sql, params);
    }

    /**
     * Performs an update against the database with the given keyholder value.
     * 
     * @param fragment  The name of the sql fragment.
     * @param params    Params to be inserted into the query.
     * @param keyHolder The keyholder for the autoincrement id.
     */
    protected int update(String fragment, MapSqlParameterSource params, KeyHolder keyHolder) {
        String sql = getSql(fragment, params);
        return getTemplate().update(sql, params, keyHolder);
    }

    /**
     * Does an insertion into the database with the given sql and params. It will
     * also get the auto incremented id of the table with the key holder.
     * 
     * @param fragment  The name of the sql fragment.
     * @param params    Params to be inserted into the query.
     * @param keyHolder used to get the auto increment id.
     */
    protected int post(String fragment, MapSqlParameterSource params, KeyHolder keyHolder) {
        return update(fragment, params, keyHolder);
    }

    /**
     * Does an insertion into the database with the given sql and params.
     * 
     * @param fragment The name of the sql fragment.
     * @param params   Params to be inserted into the query.
     */
    protected int post(String fragment, MapSqlParameterSource params) {
        return update(fragment, params);
    }

    /**
     * Performs a delete on the database for the given sql.
     * 
     * @param fragment The name of the sql fragment.
     * @param params   Params to be inserted into the query.
     */
    protected int delete(String fragment, MapSqlParameterSource params) {
        return update(fragment, params);
    }

    /**
     * Gets the sql fragement for the given name and filters out any parameters that
     * don't exist in the fragment.
     * 
     * @param name   The name of the sql fragement.
     * @param params The params to filter out of the query.
     * @return {@link String} of the filtered query.
     */
    protected String getSql(String name, SqlParameterSource params) {
        return bundle.getSql(name, params).trim();
    }

    /**
     * Gets the raw sql fragement string for the given name.
     * 
     * @param name The name of the sql fragement.
     * @return {@link String} of the sql fragment.
     */
    protected String getSql(String name) {
        return bundle.getSql(name).trim();
    }

    /**
     * Creates a new {@link MapSqlParameterSource} object with the given name and
     * value.
     * 
     * @param name  Name of the map to store the value under.
     * @param value What value to store in the map.
     * @return {@link MapSqlParameterSource} instance
     */
    protected MapSqlParameterSource parameterSource(String name, Object value) {
        return new MapSqlParameterSource(name, value);
    }
}
