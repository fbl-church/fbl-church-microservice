package com.awana.app.gurdian.rest;

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
     * Gets clubber gurdians by clubber id.
     * 
     * @param clubberId The clubber id
     * @return The list of gurdians associated to the clubber
     */
    @Operation(summary = "Get list of clubber gurdians", description = "Given a clubber id, it will get the list of gurdians for that clubber.")
    @GetMapping(path = "/clubber/{clubberId}", produces = APPLICATION_JSON_VALUE)
    public List<Gurdian> getClubberGurdians(@PathVariable int clubberId) {
        return gurdianService.getClubberGurdians(clubberId);
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
    @HasAccess(WebRole.LEADER)
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
    @HasAccess(WebRole.HELPER)
    public Gurdian updateGurdianById(@PathVariable int id, @RequestBody Gurdian gurdian) {
        return manageGurdianService.updateGurdianById(id, gurdian);
    }

    /**
     * Update the clubber's list of gurdians that they are associated too.
     * 
     * @param clubberId The clubber id to be updated
     * @param gurdians  The list of gurdians to associate to them.
     * @return clubber associated to that id with the updated information
     */
    @Operation(summary = "Update Clubber Gurdians", description = "Will update the clubbers list of gurdians associated to them")
    @PutMapping(path = "/{clubberId}/gurdians", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.HELPER)
    public List<Gurdian> updateClubberGurdiansById(@PathVariable int clubberId, @RequestBody List<Gurdian> gurdians) {
        return manageGurdianService.updateClubberGurdiansById(clubberId, gurdians);
    }

    /**
     * Associate a clubber to a gurdian.
     * 
     * @param gurdianId The id of the gurdian
     * @param clubberId The id of the clubber.
     */
    @Operation(summary = "Associate a clubber to a gurdian", description = "Will associate the given clubber and gurdian ids.")
    @PostMapping(path = "/associate/{clubberId}/clubber", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @HasAccess(WebRole.LEADER)
    public void associateClubber(@PathVariable int clubberId, @RequestBody List<Gurdian> gurdians) {
        manageGurdianService.associateClubber(clubberId, gurdians);
    }

    /**
     * Unassociate a clubber from a gurdian.
     * 
     * @param clubberId The id of the clubber
     * @param gurdians  The list of gurdian ids to unassociate.
     */
    @Operation(summary = "Unassociate a clubber from a gurdian", description = "Will unassociate the given clubber and gurdian id.")
    @DeleteMapping(path = "unassociate/{clubberId}/clubber", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.LEADER)
    public void unassociateClubber(@PathVariable int clubberId, @RequestBody List<Integer> gurdians) {
        manageGurdianService.unassociateClubber(clubberId, gurdians);
    }

    /**
     * Delete gurdian by id.
     * 
     * @param gurdianId The id of the gurdian
     */
    @Operation(summary = "Delete a gurdian by id", description = "Will delete the specified gurdian for the given id.")
    @DeleteMapping(path = "/{gurdianId}", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.LEADER)
    public void deleteGurdian(@PathVariable int gurdianId) {
        manageGurdianService.deleteGurdian(gurdianId);
    }
}
