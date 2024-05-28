/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.guardian.client;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.fbl.app.guardian.client.domain.Guardian;
import com.fbl.app.guardian.client.domain.request.GuardianGetRequest;
import com.fbl.app.guardian.service.GuardianService;
import com.fbl.app.guardian.service.ManageGuardianService;
import com.fbl.common.annotations.interfaces.Client;
import com.fbl.common.util.CommonUtil;

/**
 * This class exposes the guardian endpoint's to other app's to pull data across
 * the platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class GuardianClient {

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
    public List<Guardian> getGuardians(GuardianGetRequest request) {
        return guardianService.getGuardians(request).getList();
    }

    /**
     * Gets a guardian by id
     * 
     * @param id The guardian id
     * @return The found guardian based on the id
     */
    public Guardian getGuardianById(int id) {
        return guardianService.getGuardianById(id);
    }

    /**
     * Gets a guardian by phone number
     * 
     * @param phone The guardian phone number
     * @return The found guardian based on the phone number
     */
    public Optional<Guardian> getGuardianByPhoneNumber(String phone) {
        GuardianGetRequest request = new GuardianGetRequest();
        request.setPhone(Set.of(CommonUtil.formatPhone(phone)));
        List<Guardian> guardians = getGuardians(request);
        return guardians.isEmpty() ? Optional.empty() : Optional.ofNullable(guardians.get(0));
    }

    /**
     * Gets child guardians by child id.
     * 
     * @param childId The child id
     * @return The list of guardians associated to the child
     */
    public List<Guardian> getChildGuardians(int childId) {
        return guardianService.getChildGuardians(childId);
    }

    /**
     * Creates a new guardian for the given user object.
     * 
     * @param guardian The guardian to create.
     * @return {@link Guardian} that was created.
     */
    public Guardian insertGuardian(Guardian guardian) {
        return manageGuardianService.insertGuardian(guardian);
    }

    /**
     * Used when assigning the guardian role to an existing user.
     * 
     * @param guardian The guardian to create.
     * @return {@link Guardian} that was created.
     */
    public Guardian assignGuardianToExistingUser(int userId, Guardian guardian) {
        return manageGuardianService.assignGuardianToExistingUser(userId, guardian);
    }

    /**
     * Associate a child to a guardian.
     * 
     * @param guardianId The id of the guardian
     * @param guardians  The list of guardians
     */
    public void associateChild(int childId, List<Guardian> guardians) {
        manageGuardianService.associateChild(childId, guardians);
    }

    /**
     * Unassociate a child from it's guardians.
     * 
     * @param guardianId The id of the guardian
     */
    public void unassociateChildGuardians(int childId) {
        manageGuardianService.unassociateChildGuardians(childId);
    }
}
