/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.gurdian.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.awana.app.gurdian.client.domain.Gurdian;
import com.awana.app.gurdian.client.domain.request.GurdianGetRequest;
import com.awana.app.gurdian.service.GurdianService;
import com.awana.app.gurdian.service.ManageGurdianService;
import com.awana.common.annotations.interfaces.Client;
import com.awana.common.page.Page;

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
     * Gets clubber gurdians by clubber id.
     * 
     * @param clubberId The clubber id
     * @return The list of gurdians associated to the clubber
     */
    public List<Gurdian> getClubberGurdians(int clubberId) {
        return gurdianService.getClubberGurdians(clubberId);
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
     * Associate a clubber to a gurdian.
     * 
     * @param gurdianId The id of the gurdian
     * @param gurdians  The list of gurdians
     */
    public void associateClubber(int clubberId, List<Gurdian> gurdians) {
        manageGurdianService.associateClubber(clubberId, gurdians);
    }

    /**
     * Unassociate a clubber from a gurdian.
     * 
     * @param gurdianId The id of the gurdian
     * @param gurdians  The list of gurdians
     */
    public void unassociateClubber(int clubberId, List<Gurdian> gurdians) {
        manageGurdianService.unassociateClubber(clubberId, gurdians);
    }
}
