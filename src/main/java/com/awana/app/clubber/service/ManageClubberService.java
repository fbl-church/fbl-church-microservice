package com.awana.app.clubber.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awana.app.clubber.client.domain.Clubber;
import com.awana.app.clubber.dao.ClubberDAO;
import com.awana.app.gurdian.client.GurdianClient;

import io.jsonwebtoken.lang.Assert;

/**
 * Manage Clubber Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022`
 */
@Service
public class ManageClubberService {
    private final Logger LOGGER = LoggerFactory.getLogger(ManageClubberService.class);

    @Autowired
    private ClubberDAO dao;

    @Autowired
    private ClubberService clubberService;

    @Autowired
    private GurdianClient gurdianClient;

    /**
     * Creates a new clubber for the given user object.
     * 
     * @param clubber The clubber to create.
     * @return {@link Clubber} that was created.
     */
    public Clubber insertClubber(Clubber clubber) {
        Assert.notEmpty(clubber.getGurdianIds(), "Clubber must be associated to at least one gurdian");
        int clubberId = dao.insertClubber(clubber);
        associateClubberToGurdians(clubberId, clubber.getGurdianIds());
        return clubberService.getClubberById(clubberId);
    }

    /**
     * Delete a clubber by id.
     * 
     * @param clubberId The clubber id to delete.
     */
    public void deleteClubber(int clubberId) {
        dao.deleteClubber(clubberId);
    }

    /**
     * Associates a list of gurdians to a clubber
     * 
     * @param clubberId The clubber id to associate
     * @param gIds      The gurdian ids to associate
     */
    private void associateClubberToGurdians(int clubberId, List<Integer> gIds) {
        for (int gurdianId : gIds) {
            try {
                gurdianClient.associateClubber(gurdianId, clubberId);
            } catch (Exception e) {
                LOGGER.warn("Clubber Gurdian Association already exists", e);
            }

        }
    }
}
