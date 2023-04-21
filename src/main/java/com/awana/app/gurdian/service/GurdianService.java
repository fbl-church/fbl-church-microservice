package com.awana.app.gurdian.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awana.app.gurdian.client.domain.Gurdian;
import com.awana.app.gurdian.client.domain.request.GurdianGetRequest;
import com.awana.app.gurdian.dao.GurdianDAO;
import com.awana.common.page.Page;

/**
 * Gurdian Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service
public class GurdianService {

    @Autowired
    private GurdianDAO dao;

    /**
     * Gets a list of gurdians based of the request filter
     * 
     * @param request to filter on
     * @return list of gurdians objects
     */
    public Page<Gurdian> getGurdians(GurdianGetRequest request) {
        return null;
    }

    /**
     * Gets a gurdian by id
     * 
     * @param id The gurdian id
     * @return The found gurdian based on the id
     */
    public Gurdian getGurdianById(int id) {
        GurdianGetRequest request = new GurdianGetRequest();
        request.setId(Set.of(id));
        return null;
    }
}
