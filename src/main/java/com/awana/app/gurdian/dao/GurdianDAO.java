package com.awana.app.gurdian.dao;

import static com.awana.app.gurdian.mapper.GurdianMapper.GURDIAN_MAPPER;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.awana.app.gurdian.client.domain.Gurdian;
import com.awana.app.gurdian.client.domain.request.GurdianGetRequest;
import com.awana.common.page.Page;
import com.awana.sql.abstracts.BaseDao;
import com.awana.sql.builder.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for gurdians
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Repository
public class GurdianDAO extends BaseDao {

    public GurdianDAO(DataSource source) {
        super(source);
    }

    /**
     * Gets a list of gurdians based of the request filter
     * 
     * @param request to filter on
     * @return list of v objects
     */
    public Page<Gurdian> getGurdians(GurdianGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams().withParam(ID, request.getId())
                .withParam(FIRST_NAME, request.getFirstName()).withParam(LAST_NAME, request.getLastName())
                .withParam(EMAIL, request.getEmail()).withParam(PHONE, request.getPhone()).build();

        return getPage("getGurdiansPage", params, GURDIAN_MAPPER);
    }

    /**
     * Gets clubber gurdians by clubber id.
     * 
     * @param clubberId The clubber id
     * @return The list of gurdians associated to the clubber
     */
    public List<Gurdian> getClubberGurdians(int clubberId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(CLUBBER_ID, clubberId).build();
        return getList("getClubberGurdians", params, GURDIAN_MAPPER);
    }

    /**
     * Creates a new gurdian for the given user object.
     * 
     * @param gurdian The gurdian to create.
     * @return {@link Integer} auto increment id of the new gurdian.
     */
    public int insertGurdian(Gurdian gurdian) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(FIRST_NAME, gurdian.getFirstName())
                .withParam(LAST_NAME, gurdian.getLastName()).withParam(RELATIONSHIP, gurdian.getRelationship())
                .withParam(EMAIL, gurdian.getEmail()).withParam(PHONE, gurdian.getPhone())
                .withParam(ADDRESS, gurdian.getAddress()).build();

        post("insertGurdian", params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Associate a clubber to a gurdian.
     * 
     * @param gurdianId The id of the gurdian
     * @param clubberId The id of the clubber.
     */
    public void associateClubber(int gurdianId, int clubberId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(GURDIAN_ID, gurdianId)
                .withParam(CLUBBER_ID, clubberId).build();
        post("associateClubberToGurdian", params);
    }

    /**
     * Unassociate a clubber from a gurdian.
     * 
     * @param gurdianId The id of the gurdian
     * @param clubberId The id of the clubber.
     */
    public void unassociateClubber(int gurdianId, int clubberId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(GURDIAN_ID, gurdianId)
                .withParam(CLUBBER_ID, clubberId).build();
        post("unassociateClubberFromGurdian", params);
    }

    /**
     * Delete gurdian by id.
     * 
     * @param gurdianId The id of the gurdian
     */
    public void deleteGurdian(int gurdianId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(GURDIAN_ID, gurdianId).build();
        delete("deleteGurdian", params);
    }
}
