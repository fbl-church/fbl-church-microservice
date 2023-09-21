/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.guardian.dao;

import static com.fbl.app.guardian.mapper.GuardianMapper.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fbl.app.guardian.client.domain.Guardian;
import com.fbl.app.guardian.client.domain.request.GuardianGetRequest;
import com.fbl.common.enums.RelationshipType;
import com.fbl.common.page.Page;
import com.fbl.sql.abstracts.BaseDao;
import com.fbl.sql.builder.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for guardians
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Repository
public class GuardianDAO extends BaseDao {

    public GuardianDAO(DataSource source) {
        super(source);
    }

    /**
     * Gets a list of guardians based of the request filter
     * 
     * @param request to filter on
     * @return list of v objects
     */
    public Page<Guardian> getGuardians(GuardianGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams().withParam(ID, request.getId())
                .withParam(FIRST_NAME, request.getFirstName()).withParam(LAST_NAME, request.getLastName())
                .withParam(EMAIL, request.getEmail()).withParam(PHONE, request.getPhone()).build();

        return getPage("getGuardiansPage", params, GUARDIAN_MAPPER);
    }

    /**
     * Gets child guardians by child id.
     * 
     * @param childId The child id
     * @return The list of guardians associated to the child
     */
    public List<Guardian> getChildGuardians(int childId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(CHILD_ID, childId).build();
        return getList("getChildGuardians", params, GUARDIAN_MAPPER);
    }

    /**
     * Creates a new guardian for the given user object.
     * 
     * @param userId   The user id to associate with
     * @param guardian The guardian to create.
     * @return {@link Integer} auto increment id of the new guardian.
     */
    public void insertGuardian(int userId, Guardian guardian) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, userId)
                .withParam(PHONE, guardian.getPhone()).withParam(ADDRESS, guardian.getAddress())
                .withParam(CITY, guardian.getCity()).withParam(STATE, guardian.getState())
                .withParam(ZIP_CODE, guardian.getZipCode()).build();
        post("insertGuardian", params);
    }

    /**
     * Update the guardian's information such as email, first name, and last name
     * 
     * @param id       The id of the guardian to update
     * @param guardian what information on the guardian needs to be updated.
     * @return guardian associated to that id with the updated information
     */
    public void updateGuardianById(int id, Guardian guardian) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(PHONE, guardian.getPhone())
                .withParam(ADDRESS, guardian.getAddress()).withParam(CITY, guardian.getCity())
                .withParam(STATE, guardian.getState()).withParam(ZIP_CODE, guardian.getZipCode()).withParam(USER_ID, id)
                .build();

        post("updateGuardian", params);
    }

    /**
     * Associate a child to a guardian.
     * 
     * @param guardianId   The id of the guardian
     * @param childId      The id of the child.
     * @param relationship How the to relate
     */
    public void associateChild(int guardianId, int childId, RelationshipType relationship) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(GUARDIAN_ID, guardianId)
                .withParam(CHILD_ID, childId).withParam(RELATIONSHIP, relationship).build();
        post("associateChildToGuardian", params);
    }

    /**
     * Unassociate a child from it's guardians.
     * 
     * @param guardianId The id of the guardian
     * @param childId    The id of the child.
     */
    public void unassociateChildGuardians(int childId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(CHILD_ID, childId).build();
        delete("unassociateChildGuardians", params);
    }

    /**
     * Removes the guardian role from the user.
     * 
     * @param userId The id of the user guardian
     */
    public void removeGuardianRoleFromUser(int userId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, userId).build();
        delete("removeGuardianRoleFromUser", params);
    }

    /**
     * Delete guardian by id.
     * 
     * @param guardianId The id of the guardian
     */
    public void deleteGuardian(int guardianId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, guardianId).build();
        delete("deleteGuardian", params);
    }
}
