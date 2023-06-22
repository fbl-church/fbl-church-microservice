package com.fbl.app.gurdian.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.gurdian.client.domain.Gurdian;
import com.fbl.app.gurdian.dao.GurdianDAO;

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
     * Update the child's list of gurdians that they are associated too.
     * 
     * @param childId  The child id to be updated
     * @param gurdians The list of gurdians to associate to them.
     * @return List of gurdians that were updated on the child
     */
    public List<Gurdian> updateChildGurdiansById(int childId, List<Gurdian> gurdians) {
        unassociateChildGurdians(childId);
        associateChild(childId, gurdians);
        return gurdians;
    }

    /**
     * Associate a child to a list of gurdians.
     * 
     * @param childId  The id of the child.
     * @param gurdians List of gurdians to associate
     */
    public void associateChild(int childId, List<Gurdian> gurdians) {
        Assert.notEmpty(gurdians, "Can not associate child to list of empty gurdians.");
        for (Gurdian g : gurdians) {
            try {
                dao.associateChild(g.getId(), childId, g.getRelationship());
            } catch (Exception e) {
                LOGGER.warn("Unable to associate child id '{}' to Gurdian id '{}'", childId, g.getId());
            }

        }
    }

    /**
     * Unassociates a child from a list of gurdians.
     * 
     * @param childId  The id of the child.
     * @param gurdians List of gurdians to associate
     */
    public void unassociateChildGurdians(int childId) {
        dao.unassociateChildGurdians(childId);
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
