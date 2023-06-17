package com.fbl.app.children.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.children.dao.ChildrenDAO;
import com.fbl.app.gurdian.client.GurdianClient;

import io.jsonwebtoken.lang.Assert;

/**
 * Manage Children Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service
public class ManageChildrenService {

    @Autowired
    private ChildrenDAO dao;

    @Autowired
    private ChildrenService childrenService;

    @Autowired
    private GurdianClient gurdianClient;

    /**
     * Creates a new child for the given user object.
     * 
     * @param child The child to create.
     * @return {@link Child} that was created.
     */
    public Child insertChild(Child child) {
        Assert.notEmpty(child.getGurdians(), "Child must be associated to at least one gurdian");
        int childId = dao.insertChild(child);
        gurdianClient.associateChild(childId, child.getGurdians());
        return childrenService.getChildById(childId);
    }

    /**
     * Update the child personal information.
     * 
     * @param id    The id of the child to update
     * @param child what information on the child needs to be updated.
     * @return child associated to that id with the updated information
     */
    public Child updateChild(int id, Child child) {
        dao.updateChild(id, child);
        return childrenService.getChildById(id);
    }

    /**
     * Delete a child by id.
     * 
     * @param childId The child id to delete.
     */
    public void deleteChild(int childId) {
        dao.deleteChild(childId);
    }
}
