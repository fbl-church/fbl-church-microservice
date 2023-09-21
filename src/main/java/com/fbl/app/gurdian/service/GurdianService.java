/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.gurdian.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.gurdian.client.domain.Gurdian;
import com.fbl.app.gurdian.client.domain.request.GurdianGetRequest;
import com.fbl.app.gurdian.dao.GurdianDAO;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.client.domain.User;
import com.fbl.common.page.Page;

/**
 * Gurdian Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service
public class GurdianService {

    @Autowired
    private GurdianDAO dao;

    @Autowired
    private UserClient userClient;

    /**
     * Gets a list of gurdians based of the request filter
     * 
     * @param request to filter on
     * @return list of gurdians objects
     */
    public Page<Gurdian> getGurdians(GurdianGetRequest request) {
        return dao.getGurdians(request);
    }

    /**
     * Gets a gurdian by id
     * 
     * @param id The gurdian id
     * @return The found gurdian based on the id
     */
    public Gurdian getGurdianById(int id) {
        User user = userClient.getUserById(id);
        GurdianGetRequest request = new GurdianGetRequest();
        request.setId(Set.of(id));
        Gurdian foundGurdian = getGurdians(request).getList().get(0);
        foundGurdian.setWebRole(user.getWebRole());
        return foundGurdian;
    }

    /**
     * Gets child gurdians by child id.
     * 
     * @param childId The child id
     * @return The list of gurdians associated to the child
     */
    public List<Gurdian> getChildGurdians(int childId) {
        return dao.getChildGurdians(childId);
    }
}
