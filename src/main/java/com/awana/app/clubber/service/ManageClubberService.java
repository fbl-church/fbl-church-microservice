package com.awana.app.clubber.service;

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
 * @since June 25, 2022
 */
@Service
public class ManageClubberService {

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
        Assert.notEmpty(clubber.getGurdians(), "Clubber must be associated to at least one gurdian");
        int clubberId = dao.insertClubber(clubber);
        gurdianClient.associateClubber(clubberId, clubber.getGurdians());
        return clubberService.getClubberById(clubberId);
    }

    /**
     * Update the clubber's information such as email, first name, and last name
     * 
     * @param id      The id of the clubber to update
     * @param clubber what information on the clubber needs to be updated.
     * @return clubber associated to that id with the updated information
     */
    public Clubber updateClubber(int id, Clubber clubber) {
        dao.updateClubber(id, clubber);
        return clubberService.getClubberById(id);
    }

    /**
     * Delete a clubber by id.
     * 
     * @param clubberId The clubber id to delete.
     */
    public void deleteClubber(int clubberId) {
        dao.deleteClubber(clubberId);
    }
}
