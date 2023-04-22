package com.awana.app.clubber.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awana.app.clubber.client.domain.Clubber;
import com.awana.app.clubber.client.domain.request.ClubberGetRequest;
import com.awana.app.clubber.openapi.TagClubber;
import com.awana.app.clubber.service.ClubberService;
import com.awana.app.clubber.service.ManageClubberService;
import com.awana.common.annotations.interfaces.HasAccess;
import com.awana.common.enums.WebRole;
import com.awana.common.page.Page;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Clubber Controller for managing endpoints
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@RequestMapping("/api/clubbers")
@RestController
@TagClubber
public class ClubberController {

    @Autowired
    private ClubberService clubberService;

    @Autowired
    private ManageClubberService manageClubberService;

    /**
     * Gets a list of clubbers based of the request filter
     * 
     * @param request to filter on
     * @return list of clubbers objects
     */
    @Operation(summary = "Get a list of clubbers.", description = "Given a Clubber Get Request, it will return a list of clubbers that match the request.")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Page<Clubber> getClubbers(ClubberGetRequest request) {
        return clubberService.getClubbers(request);
    }

    /**
     * Gets a clubber by id
     * 
     * @param id The clubber id
     * @return The found clubber based on the id
     */
    @Operation(summary = "Get a clubber by id", description = "Given a Clubber id, it will return that clubber information if found.")
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Clubber getClubberById(@PathVariable int id) {
        return clubberService.getClubberById(id);
    }

    /**
     * Creates a new clubber for the given user object.
     * 
     * @param clubber The clubber to create.
     * @return {@link Clubber} that was created.
     */
    @Operation(summary = "Create a new Clubber.", description = "Given a Clubber request body. It will create a new clubber.")
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.CUBBIES_LEADER)
    public Clubber insertClubber(@RequestBody @Valid Clubber clubber) {
        return manageClubberService.insertClubber(clubber);
    }
}
