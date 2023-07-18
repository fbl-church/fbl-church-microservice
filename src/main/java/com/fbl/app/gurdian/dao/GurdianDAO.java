package com.fbl.app.gurdian.dao;

import static com.fbl.app.gurdian.mapper.GurdianMapper.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fbl.app.gurdian.client.domain.Gurdian;
import com.fbl.app.gurdian.client.domain.request.GurdianGetRequest;
import com.fbl.common.enums.RelationshipType;
import com.fbl.common.page.Page;
import com.fbl.sql.abstracts.BaseDao;
import com.fbl.sql.builder.SqlParamBuilder;

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
     * Gets child gurdians by child id.
     * 
     * @param childId The child id
     * @return The list of gurdians associated to the child
     */
    public List<Gurdian> getChildGurdians(int childId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(CHILD_ID, childId).build();
        return getList("getChildGurdians", params, GURDIAN_MAPPER);
    }

    /**
     * Creates a new gurdian for the given user object.
     * 
     * @param userId  The user id to associate with
     * @param gurdian The gurdian to create.
     * @return {@link Integer} auto increment id of the new gurdian.
     */
    public void insertGurdian(int userId, Gurdian gurdian) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, userId)
                .withParam(PHONE, gurdian.getPhone()).withParam(ADDRESS, gurdian.getAddress()).build();
        post("insertGurdian", params);
    }

    /**
     * Update the gurdian's information such as email, first name, and last name
     * 
     * @param id      The id of the gurdian to update
     * @param gurdian what information on the gurdian needs to be updated.
     * @return gurdian associated to that id with the updated information
     */
    public void updateGurdianById(int id, Gurdian gurdian) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(PHONE, gurdian.getPhone())
                .withParam(ADDRESS, gurdian.getAddress()).withParam(ID, id)
                .build();

        post("updateGurdian", params);
    }

    /**
     * Associate a child to a gurdian.
     * 
     * @param gurdianId    The id of the gurdian
     * @param childId      The id of the child.
     * @param relationship How the to relate
     */
    public void associateChild(int gurdianId, int childId, RelationshipType relationship) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(GURDIAN_ID, gurdianId)
                .withParam(CHILD_ID, childId).withParam(RELATIONSHIP, relationship).build();
        post("associateChildToGurdian", params);
    }

    /**
     * Unassociate a child from it's gurdians.
     * 
     * @param gurdianId The id of the gurdian
     * @param childId   The id of the child.
     */
    public void unassociateChildGurdians(int childId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(CHILD_ID, childId).build();
        delete("unassociateChildGurdians", params);
    }

    /**
     * Removes the gurdian role from the user.
     * 
     * @param userId The id of the user gurdian
     */
    public void removeGurdianRoleFromUser(int userId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, userId).build();
        delete("removeGurdianRoleFromUser", params);
    }

    /**
     * Delete gurdian by id.
     * 
     * @param gurdianId The id of the gurdian
     */
    public void deleteGurdian(int gurdianId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, gurdianId).build();
        delete("deleteGurdian", params);
    }
}
