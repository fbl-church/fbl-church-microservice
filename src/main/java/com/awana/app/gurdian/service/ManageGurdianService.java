package com.awana.app.gurdian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awana.app.gurdian.client.domain.Gurdian;
import com.awana.app.gurdian.dao.GurdianDAO;

/**
 * Manage Gurdian Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service
public class ManageGurdianService {

    @Autowired
    private GurdianDAO dao;

    @Autowired
    private GurdianService gurdianService;

    /**
     * Creates a new gurdian for the given user object.
     * 
     * @param gurdian The gurdian to create.
     * @return {@link Gurdian} that was created.
     */
    public Gurdian insertGurdian(Gurdian gurdian) {
        int gurdianId = dao.insertGurdian(gurdian);
        return gurdianService.getGurdianById(gurdianId);
    }

    /**
     * Associate a clubber to a gurdian.
     * 
     * @param gurdianId The id of the gurdian
     * @param clubberId The id of the clubber.
     */
    public void associateClubber(int gurdianId, int clubberId) {
        dao.associateClubber(gurdianId, clubberId);
    }

    /**
     * Unassociate a clubber from a gurdian.
     * 
     * @param gurdianId The id of the gurdian
     * @param clubberId The id of the clubber.
     */
    public void unassociateClubber(int gurdianId, int clubberId) {
        dao.unassociateClubber(gurdianId, clubberId);
    }

    /**
     * Delete gurdian by id.
     * 
     * @param gurdianId The id of the gurdian
     */
    public void deleteGurdian(int gurdianId) {
        dao.deleteGurdian(gurdianId);
    }
}
