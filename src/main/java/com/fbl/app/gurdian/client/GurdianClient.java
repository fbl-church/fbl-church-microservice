/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.gurdian.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fbl.app.gurdian.client.domain.Gurdian;
import com.fbl.app.gurdian.client.domain.request.GurdianGetRequest;
import com.fbl.app.gurdian.service.GurdianService;
import com.fbl.app.gurdian.service.ManageGurdianService;
import com.fbl.common.annotations.interfaces.Client;
import com.fbl.common.page.Page;

/**
 * This class exposes the gurdian endpoint's to other app's to pull data across
 * the platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class GurdianClient {

    @Autowired
    private GurdianService gurdianService;

    @Autowired
    private ManageGurdianService manageGurdianService;

    /**
     * Gets a list of gurdians based of the request filter
     * 
     * @param request to filter on
     * @return list of gurdians objects
     */
    public Page<Gurdian> getGurdians(GurdianGetRequest request) {
        return gurdianService.getGurdians(request);
    }

    /**
     * Gets a gurdian by id
     * 
     * @param id The gurdian id
     * @return The found gurdian based on the id
     */
    public Gurdian getGurdianById(int id) {
        return gurdianService.getGurdianById(id);
    }

    /**
     * Gets child gurdians by child id.
     * 
     * @param childId The child id
     * @return The list of gurdians associated to the child
     */
    public List<Gurdian> getChildGurdians(int childId) {
        return gurdianService.getChildGurdians(childId);
    }

    /**
     * Creates a new gurdian for the given user object.
     * 
     * @param gurdian The gurdian to create.
     * @return {@link Gurdian} that was created.
     */
    public Gurdian insertGurdian(Gurdian gurdian) {
        return manageGurdianService.insertGurdian(gurdian);
    }

    /**
     * Associate a child to a gurdian.
     * 
     * @param gurdianId The id of the gurdian
     * @param gurdians  The list of gurdians
     */
    public void associateChild(int childId, List<Gurdian> gurdians) {
        manageGurdianService.associateChild(childId, gurdians);
    }

    /**
     * Unassociate a child from it's gurdians.
     * 
     * @param gurdianId The id of the gurdian
     */
    public void unassociateChildGurdians(int childId) {
        manageGurdianService.unassociateChildGurdians(childId);
    }
}
