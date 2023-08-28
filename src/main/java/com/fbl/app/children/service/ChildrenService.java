package com.fbl.app.children.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.children.client.domain.request.ChildGetRequest;
import com.fbl.app.children.dao.ChildrenDAO;
import com.fbl.app.gurdian.client.GurdianClient;
import com.fbl.common.page.Page;

import lombok.RequiredArgsConstructor;

/**
 * Children Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service
@RequiredArgsConstructor
public class ChildrenService {

    private final ChildrenDAO dao;
    private final GurdianClient gurdianClient;

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
        c.setGurdians(gurdianClient.getChildGurdians(id));
        c.setChurchGroup(dao.getChildChurchGroupsById(id));
        return c;
    }

    /**
     * Gets gurdian children by gurdian id.
     * 
     * @param gurdianId The gurdian id
     * @return The list of children associated to the gurdian
     */
    public List<Child> getGurdianChildren(int gurdianId) {
        return dao.getGurdianChildren(gurdianId);
    }
}
