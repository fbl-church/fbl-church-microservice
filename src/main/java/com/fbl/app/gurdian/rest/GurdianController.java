package com.fbl.app.gurdian.rest;

import static org.springframework.http.MediaType.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fbl.app.gurdian.client.domain.Gurdian;
import com.fbl.app.gurdian.client.domain.request.GurdianGetRequest;
import com.fbl.app.gurdian.openapi.TagGurdian;
import com.fbl.app.gurdian.service.GurdianService;
import com.fbl.app.gurdian.service.ManageGurdianService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;

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
    public Gurdian getGurdianById(@PathVariable int id) {
        return gurdianService.getGurdianById(id);
    }

    /**
     * Gets child gurdians by child id.
     * 
     * @param childId The child id
     * @return The list of gurdians associated to the child
     */
    @Operation(summary = "Get list of child gurdians", description = "Given a child id, it will get the list of gurdians for that child.")
    @GetMapping(path = "/child/{childId}", produces = APPLICATION_JSON_VALUE)
    public List<Gurdian> getChildGurdians(@PathVariable int childId) {
        return gurdianService.getChildGurdians(childId);
    }

    /**
     * Creates a new gurdian for the given user object.
     * 
     * @param gurdian The gurdian to create.
     * @return {@link Gurdian} that was created.
     */
    @Operation(summary = "Create a new Gurdian.", description = "Given a Gurdian request body. It will create a new gurdian.")
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @HasAccess(WebRole.AWANA_LEADER)
    public Gurdian insertGurdian(@RequestBody @Valid Gurdian gurdian) {
        return manageGurdianService.insertGurdian(gurdian);
    }

    /**
     * Update the gurdian's information such as email, first name, and last name
     * 
     * @param gurdian what information on the gurdian needs to be updated.
     * @return gurdian associated to that id with the updated information
     */
    @Operation(summary = "Update Gurdian Information", description = "Will update the given gurdian information.")
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.AWANA_WORKER)
    public Gurdian updateGurdianById(@PathVariable int id, @RequestBody Gurdian gurdian) {
        return manageGurdianService.updateGurdianById(id, gurdian);
    }

    /**
     * Update the child's list of gurdians that they are associated too.
     * 
     * @param childId  The child id to be updated
     * @param gurdians The list of gurdians to associate to them.
     * @return list of gurdians that were updated
     */
    @Operation(summary = "Update child gurdians", description = "Will update the child's list of gurdians associated to them")
    @PutMapping(path = "/{childId}/gurdians", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.AWANA_WORKER)
    public List<Gurdian> updateChildGurdiansById(@PathVariable int childId, @RequestBody List<Gurdian> gurdians) {
        return manageGurdianService.updateChildGurdiansById(childId, gurdians);
    }

    /**
     * Delete gurdian by id.
     * 
     * @param gurdianId The id of the gurdian
     */
    @Operation(summary = "Delete a gurdian by id", description = "Will delete the specified gurdian for the given id.")
    @DeleteMapping(path = "/{gurdianId}", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.AWANA_LEADER)
    public void deleteGurdian(@PathVariable int gurdianId) {
        manageGurdianService.deleteGurdian(gurdianId);
    }
}
