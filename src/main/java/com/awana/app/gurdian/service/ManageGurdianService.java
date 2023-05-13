package com.awana.app.gurdian.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awana.app.gurdian.client.domain.Gurdian;
import com.awana.app.gurdian.dao.GurdianDAO;

import io.jsonwebtoken.lang.Assert;

/**
 * Manage Gurdian Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service
public class ManageGurdianService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManageGurdianService.class);

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
     * Update the gurdian's information such as email, first name, and last name
     * 
     * @param gurdian what information on the gurdian needs to be updated.
     * @return gurdian associated to that id with the updated information
     */
    public Gurdian updateGurdianById(int id, Gurdian gurdian) {
        dao.updateGurdianById(id, gurdian);
        return gurdianService.getGurdianById(id);
    }

    /**
     * Update the clubber's list of gurdians that they are associated too.
     * 
     * @param clubberId The clubber id to be updated
     * @param gurdians  The list of gurdians to associate to them.
     * @return clubber associated to that id with the updated information
     */
    public List<Gurdian> updateClubberGurdiansById(int clubberId, List<Gurdian> gurdians) {
        unassociateClubber(clubberId, gurdians.stream().map(Gurdian::getId).collect(Collectors.toList()));
        associateClubber(clubberId, gurdians);
        return gurdians;
    }

    /**
     * Associate a clubber to a list of gurdians.
     * 
     * @param clubberId The id of the clubber.
     * @param gurdians  List of gurdians to associate
     */
    public void associateClubber(int clubberId, List<Gurdian> gurdians) {
        Assert.notEmpty(gurdians, "Can not associate clubber to list of empty gurdians.");
        for(Gurdian g : gurdians) {
            try {
                dao.associateClubber(g.getId(), clubberId, g.getRelationship());
            }catch(Exception e) {
                LOGGER.warn("Unable to associate Clubber id '{}' to Gurdian id '{}'", clubberId, g.getId());
            }

        }
    }

    /**
     * Unassociates a clubber from a list of gurdians.
     * 
     * @param clubberId The id of the clubber.
     * @param gurdians  List of gurdians to associate
     */
    public void unassociateClubber(int clubberId, List<Integer> gurdianIds) {
        Assert.notEmpty(gurdianIds, "Can not unassociate clubber from list of empty gurdians.");
        for(Integer gId : gurdianIds) {
            try {
                dao.unassociateClubber(gId, clubberId);
            }catch(Exception e) {
                LOGGER.warn("Unable to unassociate Clubber id '{}' from Gurdian id '{}'", clubberId, gId);
            }

        }
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
