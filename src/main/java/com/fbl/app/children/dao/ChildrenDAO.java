package com.fbl.app.children.dao;

import static com.fbl.app.children.mapper.ChildMapper.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.children.client.domain.request.ChildGetRequest;
import com.fbl.common.page.Page;
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
                .withParamTextEnumCollection(CHURCH_GROUP, request.getChurchGroup()).build();

        return getPage("getChildrenPage", params, CHILD_MAPPER);
    }

    /**
     * Gets gurdian children by gurdian id.
     * 
     * @param gurdianId The gurdian id
     * @return The list of children associated to the gurdian
     */
    public List<Child> getGurdianChildren(int gurdianId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(GURDIAN_ID, gurdianId).build();
        return getList("getGurdianChildren", params, CHILD_MAPPER);
    }

    /**
     * Creates a new child for the given user object.
     * 
     * @param child The child to create.
     * @return {@link Integer} auto increment id of the new child.
     */
    public int insertChild(Child child) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(FIRST_NAME, child.getFirstName())
                .withParam(LAST_NAME, child.getLastName()).withParam(CHURCH_GROUP, child.getChurchGroup())
                .withParam(BIRTHDAY, child.getBirthday()).withParam(ALLERGIES, child.getAllergies())
                .withParam(ADDITIONAL_INFO, child.getAdditionalInfo()).build();

        post("insertChild", params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Update the child's information such as email, first name, and last name
     * 
     * @param id    The id of the child to update
     * @param child what information on the child needs to be updated.
     * @return child associated to that id with the updated information
     */
    public void updateChild(int id, Child child) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(FIRST_NAME, child.getFirstName())
                .withParam(LAST_NAME, child.getLastName()).withParam(CHURCH_GROUP, child.getChurchGroup())
                .withParam(BIRTHDAY, child.getBirthday()).withParam(ALLERGIES, child.getAllergies())
                .withParam(ADDITIONAL_INFO, child.getAdditionalInfo()).withParam(ID, id).build();

        post("updateChild", params);
    }

    /**
     * Delete a child by id.
     * 
     * @param childId The child id to delete.
     */
    public void deleteChild(int childId) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(ID, childId).build();
        delete("deleteChild", params);
    }
}