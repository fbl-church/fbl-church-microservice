/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.guardian.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fbl.app.guardian.client.domain.Guardian;
import com.fbl.app.guardian.client.domain.request.GuardianGetRequest;
import com.fbl.app.guardian.openapi.TagGuardian;
import com.fbl.app.guardian.service.GuardianService;
import com.fbl.app.guardian.service.ManageGuardianService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

/**
 * Guardian Controller for managing endpoints
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@RequestMapping("/api/guardians")
@RestApiController

@TagGuardian
public class GuardianController {

    @Autowired
    private GuardianService guardianService;

    @Autowired
    private ManageGuardianService manageGuardianService;

    /**
     * Gets a list of guardians based of the request filter
     * 
     * @param request to filter on
     * @return list of guardians objects
     */
    @Operation(summary = "Get a list of guardians.", description = "Given a Guardian Get Request, it will return a list of guardians that match the request.")
    @GetMapping
    public Page<Guardian> getGuardians(GuardianGetRequest request) {
        return guardianService.getGuardians(request);
    }

    /**
     * Gets a guardian by id
     * 
     * @param id The guardian id
     * @return The found guardian based on the id
     */
    @Operation(summary = "Get a guardian by id", description = "Given a Guardian id, it will return that guardian information if found.")
    @GetMapping("/{id}")
    public Guardian getGuardianById(@PathVariable int id) {
        return guardianService.getGuardianById(id);
    }

    /**
     * Gets child guardians by child id.
     * 
     * @param childId The child id
     * @return The list of guardians associated to the child
     */
    @Operation(summary = "Get list of child guardians", description = "Given a child id, it will get the list of guardians for that child.")
    @GetMapping("/child/{childId}")
    public List<Guardian> getChildGuardians(@PathVariable int childId) {
        return guardianService.getChildGuardians(childId);
    }

    /**
     * Checks to see if the guardian already exists for the given information
     * 
     * @param c The guardian to check
     * @return The list of guardian that match the given child
     */
    @Operation(summary = "Checks to see if guardian exists", description = "Given a guardian, It will check if there are any guardian that already exist")
    @GetMapping("/exists")
    public Guardian doesGuardianExist(Guardian g) {
        return guardianService.doesGuardianExist(g);
    }

    /**
     * This will check to see if the phone number exists. If it does then it will
     * return true, otherwise false.
     * 
     * @param phone The phone to check
     * @return {@link Boolean} to see if the phone exists
     */
    @GetMapping("/check-phone")
    public boolean doesPhoneNumberExist(@RequestParam String phone) {
        return guardianService.doesPhoneNumberExist(phone);
    }

    /**
     * Creates a new guardian for the given user object.
     * 
     * @param guardian The guardian to create.
     * @return {@link Guardian} that was created.
     */
    @Operation(summary = "Create a new Guardian.", description = "Given a Guardian request body. It will create a new guardian.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @HasAccess(WebRole.LEADER)
    public Guardian insertGuardian(@RequestBody @Valid Guardian guardian) {
        return manageGuardianService.insertGuardian(guardian);
    }

    /**
     * Used when assigning the guardian role to an existing user.
     * 
     * @param guardian The guardian to create.
     * @return {@link Guardian} that was created.
     */
    @Operation(summary = "Create guardian entry to existing user", description = "Will take an existing user and assign them the guardian entry")
    @PutMapping("/{userId}/user")
    @ResponseStatus(HttpStatus.CREATED)
    @HasAccess(WebRole.WORKER)
    public Guardian assignGuardianToExistingUser(@PathVariable int userId, @RequestBody Guardian guardian) {
        return manageGuardianService.assignGuardianToExistingUser(userId, guardian);
    }

    /**
     * Update the guardian's profile information. Only guardian can update their own
     * information
     * 
     * @param guardian what information on the guardian needs to be updated.
     * @return guardian associated to that id with the updated information
     */
    @Operation(summary = "Update Guardian Profile Information", description = "Endpoint used for a guardian updating their own information")
    @PutMapping("/profile/{id}")
    public Guardian updateGuardianProfile(@PathVariable int id, @RequestBody Guardian guardian) {
        return manageGuardianService.updateGuardianProfile(id, guardian);
    }

    /**
     * Update the guardian's information such as email, first name, and last name
     * 
     * @param guardian what information on the guardian needs to be updated.
     * @return guardian associated to that id with the updated information
     */
    @Operation(summary = "Update Guardian Information", description = "Will update the given guardian information.")
    @PutMapping("/{id}")
    @HasAccess(WebRole.WORKER)
    public Guardian updateGuardianById(@PathVariable int id, @RequestBody Guardian guardian) {
        return manageGuardianService.updateGuardianById(id, guardian);
    }

    /**
     * Update the child's list of guardians that they are associated too.
     * 
     * @param childId   The child id to be updated
     * @param guardians The list of guardians to associate to them.
     * @return list of guardians that were updated
     */
    @Operation(summary = "Update child guardians", description = "Will update the child's list of guardians associated to them")
    @PutMapping("/{childId}/guardians")
    @HasAccess(WebRole.WORKER)
    public List<Guardian> updateChildGuardiansById(@PathVariable int childId, @RequestBody List<Guardian> guardians) {
        return manageGuardianService.updateChildGuardiansById(childId, guardians);
    }

    /**
     * Restore deleted guardian by id
     * 
     * @param userId The user Id to restore
     */
    @Operation(summary = "Restore deleted guardian", description = "Restore deleted guardian for the given id.")
    @PutMapping("/{childId}/restore")
    @HasAccess(WebRole.SITE_ADMINISTRATOR)
    public void restoreGuardian(@PathVariable int guardianId) {
        manageGuardianService.restoreGuardian(guardianId);
    }

    /**
     * Delete guardian by id.
     * 
     * @param guardianId The id of the guardian
     */
    @Operation(summary = "Delete a guardian by id", description = "Will delete the specified guardian for the given id.")
    @DeleteMapping("/{guardianId}")
    @HasAccess(WebRole.LEADER)
    public void deleteGuardian(@PathVariable int guardianId) {
        manageGuardianService.deleteGuardian(guardianId);
    }
}
