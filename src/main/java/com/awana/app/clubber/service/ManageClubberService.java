package com.awana.app.clubber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awana.app.clubber.client.domain.Clubber;
import com.awana.app.clubber.dao.ClubberDAO;

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

    /**
     * Creates a new clubber for the given user object.
     * 
     * @param clubber The clubber to create.
     * @return {@link Clubber} that was created.
     */
    public Clubber insertClubber(Clubber clubber) {
        int clubberId = dao.insertClubber(clubber);
        return clubberService.getClubberById(clubberId);
    }
}
