package com.awana.app.gurdian.rest;

import static org.springframework.http.MediaType.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awana.app.gurdian.client.domain.Gurdian;
import com.awana.app.gurdian.client.domain.request.GurdianGetRequest;
import com.awana.app.gurdian.openapi.TagGurdian;
import com.awana.app.gurdian.service.GurdianService;
import com.awana.app.gurdian.service.ManageGurdianService;
import com.awana.common.annotations.interfaces.HasAccess;
import com.awana.common.enums.WebRole;
import com.awana.common.page.Page;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Gurdian Controller for managing endpoints
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@RequestMapping("/api/gurdians")
@RestController
@TagGurdian
public class GurdianController {

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
    @Operation(summary = "Get a list of gurdians.", description = "Given a Gurdian Get Request, it will return a list of gurdians that match the request.")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.TNT_LEADER)
    public Page<Gurdian> getGurdians(GurdianGetRequest request) {
        return gurdianService.getGurdians(request);
    }

    /**
     * Gets a gurdian by id
     * 
     * @param id The gurdian id
     * @return The found gurdian based on the id
     */
    @Operation(summary = "Get a gurdian by id", description = "Given a Gurdian id, it will return that gurdian information if found.")
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.TNT_LEADER)
    public Gurdian getGurdianById(int id) {
        return gurdianService.getGurdianById(id);
    }

    /**
     * Creates a new gurdian for the given user object.
     * 
     * @param gurdian The gurdian to create.
     * @return {@link Gurdian} that was created.
     */
    @Operation(summary = "Create a new Gurdian.", description = "Given a Gurdian request body. It will create a new gurdian.")
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.SITE_ADMIN)
    public Gurdian insertGurdian(@RequestBody @Valid Gurdian gurdian) {
        return manageGurdianService.insertGurdian(gurdian);
    }
}
