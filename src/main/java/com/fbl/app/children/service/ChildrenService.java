/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.children.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.children.client.domain.request.ChildGetRequest;
import com.fbl.app.children.dao.ChildrenDAO;
import com.fbl.app.guardian.client.GuardianClient;
import com.fbl.common.page.Page;
import com.fbl.exception.types.NotFoundException;
import com.google.common.collect.Sets;

/**
 * Children Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service

public class ChildrenService {

    @Autowired
    private ChildrenDAO dao;

    @Autowired
    private GuardianClient guardianClient;

    /**
     * Gets a list of children based of the request filter
     * 
     * @param request to filter on
     * @return list of children objects
     */
    public Page<Child> getChildren(ChildGetRequest request) {
        return dao.getChildren(request);
    }

    /**
     * Gets a child by id
     * 
     * @param id The child id
     * @return The found child based on the id
     */
    public Child getChildById(int id) {
        Child foundChild = dao.getChildById(id).orElseThrow(() -> new NotFoundException("Child", id));
        foundChild.setGuardians(guardianClient.getChildGuardians(id));
        foundChild.setChurchGroup(dao.getChildChurchGroupsById(id));
        return foundChild;
    }

    /**
     * Gets guardian children by guardian id.
     * 
     * @param guardianId The guardian id
     * @return The list of children associated to the guardian
     */
    public List<Child> getGuardianChildren(int guardianId) {
        return dao.getGuardianChildren(guardianId);
    }

    /**
     * Checks to see if the child already exists for the given information
     * 
     * @param c The child to check
     * @return The list of children that match the given child
     */
    public Child doesChildExist(Child c) {
        ChildGetRequest request = new ChildGetRequest();
        request.setFirstName(Sets.newHashSet(c.getFirstName()));
        request.setLastName(Sets.newHashSet(c.getLastName()));
        List<Child> matchingChildren = getChildren(request).getList();
        return matchingChildren.size() > 0 ? matchingChildren.get(0) : null;
    }
}
