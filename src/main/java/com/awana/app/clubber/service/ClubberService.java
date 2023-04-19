package com.awana.app.clubber.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awana.app.clubber.client.domain.Clubber;
import com.awana.app.clubber.client.domain.request.ClubberGetRequest;
import com.awana.app.clubber.dao.ClubberDAO;
import com.awana.common.page.Page;

/**
 * Clubber Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service
public class ClubberService {

    @Autowired
    private ClubberDAO dao;

    /**
     * Gets a list of clubbers based of the request filter
     * 
     * @param request to filter on
     * @return list of clubbers objects
     */
    public Page<Clubber> getClubbers(ClubberGetRequest request) {
        return dao.getClubbers(request);
    }

    /**
     * Gets a clubber by id
     * 
     * @param id The clubber id
     * @return The found clubber based on the id
     */
    public Clubber getClubberById(int id) {
        ClubberGetRequest request = new ClubberGetRequest();
        request.setId(Set.of(id));
        return getClubbers(request).getList().get(0);
    }
}
