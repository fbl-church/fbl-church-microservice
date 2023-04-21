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
}
