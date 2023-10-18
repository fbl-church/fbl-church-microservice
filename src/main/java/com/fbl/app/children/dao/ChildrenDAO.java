/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.children.dao;

import static com.fbl.app.children.mapper.ChildMapper.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.children.client.domain.request.ChildGetRequest;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.common.page.Page;
import com.fbl.common.util.CommonUtil;
import com.fbl.sql.abstracts.BaseDao;
import com.fbl.sql.builder.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for children
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Repository
public class ChildrenDAO extends BaseDao {

    public ChildrenDAO(DataSource source) {
        super(source);
    }

    /**
     * Gets a list of children based of the request filter
     * 
     * @param request to filter on
     * @return list of child objects
     */
    public Page<Child> getChildren(ChildGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams().withParam(ID, request.getId())
                .withParam(FIRST_NAME, request.getFirstName()).withParam(LAST_NAME, request.getLastName())
                .withParamTextEnumCollection(CHURCH_GROUP, request.getChurchGroup())
                .withParamTextEnumCollection(NOT_CHURCH_GROUP, request.getNotChurchGroup()).build();

        return getPage("getChildrenPage", params, CHILD_MAPPER);
    }

    /**
     * Gets guardian children by guardian id.
     * 
     * @param guardianId The guardian id
     * @return The list of children associated to the guardian
     */
    public List<Child> getGuardianChildren(int guardianId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(GUARDIAN_ID, guardianId).build();
        return getList("getGuardianChildren", params, CHILD_MAPPER);
    }

    /**
     * Get a list of child groups by id.
     * 
     * @param childId The child id
     * @return List of church groups
     */
    public List<ChurchGroup> getChildChurchGroupsById(int childId) {
        MapSqlParameterSource params = parameterSource(CHILD_ID, childId);
        return getList("getChildChurchGroupsById", params, ChurchGroup.class);
    }

    /**
     * Creates a new child for the given user object.
     * 
     * @param child The child to create.
     * @return {@link Integer} auto increment id of the new child.
     */
    public void insertChild(int userId, Child child) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, userId)
                .withParam(CUID, child.getCuid())
                .withParam(BIRTHDAY, child.getBirthday())
                .withParam(ALLERGIES, CommonUtil.serializeStringList(child.getAllergies(), ","))
                .withParam(ADDITIONAL_INFO, child.getAdditionalInfo())
                .withParam(RELEASE_OF_LIABILITY, child.isReleaseOfLiability()).build();

        post("insertChild", params);
    }

    /**
     * Inserts a child church group
     * 
     * @param childId The child to assign the group too
     * @param group   The group to assign
     */
    public void insertChildGroup(int childId, ChurchGroup group) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(CHILD_ID, childId)
                .withParam(CHURCH_GROUP, group)
                .build();
        post("insertChildGroup", params);
    }

    /**
     * Update the child's information such as email, first name, and last name
     * 
     * @param id    The id of the child to update
     * @param child what information on the child needs to be updated.
     * @return child associated to that id with the updated information
     */
    public void updateChildById(int id, Child child) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(CHURCH_GROUP, child.getChurchGroup())
                .withParam(BIRTHDAY, child.getBirthday())
                .withParam(ALLERGIES, CommonUtil.serializeStringList(child.getAllergies(), ","))
                .withParam(ADDITIONAL_INFO, child.getAdditionalInfo()).withParam(USER_ID, id)
                .withParam(RELEASE_OF_LIABILITY, child.isReleaseOfLiability()).build();

        post("updateChildById", params);
    }

    /**
     * Delete a child by id.
     * 
     * @param childId The child id to delete.
     */
    public void deleteChild(int childId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, childId).build();
        delete("deleteChild", params);
    }

    /**
     * Delete the child groups for the given id.
     * 
     * @param childId The child id to remove the groups from.
     */
    public void deleteChildGroups(int childId) {
        delete("deleteChildGroups", parameterSource(CHILD_ID, childId));
    }
}
