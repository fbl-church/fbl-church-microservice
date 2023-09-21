/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.children.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.children.client.domain.request.ChildGetRequest;
import com.fbl.app.children.dao.ChildrenDAO;
import com.fbl.app.guardian.client.GuardianClient;
import com.fbl.common.page.Page;

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
        ChildGetRequest request = new ChildGetRequest();
        request.setId(Set.of(id));
        Child c = getChildren(request).getList().get(0);
        c.setGuardians(guardianClient.getChildGuardians(id));
        c.setChurchGroup(dao.getChildChurchGroupsById(id));
        return c;
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
}
