package com.awana.app.clubber.dao;

import static com.awana.app.clubber.mapper.ClubberMapper.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.awana.app.clubber.client.domain.Clubber;
import com.awana.app.clubber.client.domain.request.ClubberGetRequest;
import com.awana.common.page.Page;
import com.awana.sql.abstracts.BaseDao;
import com.awana.sql.builder.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for clubbers
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Repository
public class ClubberDAO extends BaseDao {

    public ClubberDAO(DataSource source) {
        super(source);
    }

    /**
     * Gets a list of clubbers based of the request filter
     * 
     * @param request to filter on
     * @return list of clubbers objects
     */
    public Page<Clubber> getClubbers(ClubberGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams().withParam(ID, request.getId())
                .withParam(FIRST_NAME, request.getFirstName()).withParam(LAST_NAME, request.getLastName())
                .withParamTextEnumCollection(CHURCH_GROUP, request.getChurchGroup()).build();

        return getPage("getClubbersPage", params, CLUBBER_MAPPER);
    }

    /**
     * Gets gurdian clubbers by gurdian id.
     * 
     * @param gurdianId The gurdian id
     * @return The list of clubbers associated to the gurdian
     */
    public List<Clubber> getGurdianClubbers(int gurdianId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(GURDIAN_ID, gurdianId).build();
        return getList("getGurdianClubbers", params, CLUBBER_MAPPER);
    }

    /**
     * Creates a new clubber for the given user object.
     * 
     * @param clubber The clubber to create.
     * @return {@link Integer} auto increment id of the new clubber.
     */
    public int insertClubber(Clubber clubber) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(FIRST_NAME, clubber.getFirstName())
                .withParam(LAST_NAME, clubber.getLastName()).withParam(CHURCH_GROUP, clubber.getChurchGroup())
                .withParam(BIRTHDAY, clubber.getBirthday()).withParam(ALLERGIES, clubber.getAllergies())
                .withParam(ADDITIONAL_INFO, clubber.getAdditionalInfo()).build();

        post("insertClubber", params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Delete a clubber by id.
     * 
     * @param clubberId The clubber id to delete.
     */
    public void deleteClubber(int clubberId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(ID, clubberId).build();
        delete("deleteClubber", params);
    }
}
