/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fbl.app.user.client.domain.UserStatus;
import com.fbl.app.user.service.ManageUserStatusService;
import com.fbl.app.user.service.UserStatusService;

@RequestMapping("api/users/status")
@RestController
public class UserStatusController {

    @Autowired
    private UserStatusService userStatusService;

    @Autowired
    private ManageUserStatusService manageUserStatusService;

    /**
     * Gets the status for the given user id.
     * 
     * @param id The id of the user to get the status for.
     * @return {@link UserStatus} object
     */
    @GetMapping("/{id}")
    public UserStatus getUserStatusById(@PathVariable int id) {
        return userStatusService.getUserStatusById(id);
    }

    /**
     * Inserts the given user status object into the db
     * 
     * @param userStatus Object to be inserted.
     * @return {@link UserStatus} object
     */
    @PostMapping
    public UserStatus insertUserStatus(@RequestBody UserStatus userStatus) {
        return manageUserStatusService.insertUserStatus(userStatus);
    }

    /**
     * Updates a users status for the given user id
     * 
     * @param id The id of the user to get the status for.
     * @return {@link UserStatus} object
     */
    @PutMapping("/{id}")
    public UserStatus updateUserStatusByUserId(@PathVariable int id, @RequestBody UserStatus userStatus) {
        return manageUserStatusService.updateUserStatusByUserId(id, userStatus);
    }

    /**
     * Updates a users app access for the given user id
     * 
     * @param id        The id of the user to get the status for.
     * @param appAccess boolean determining what access the user has.
     * @return {@link UserStatus} object
     */
    @PutMapping("/{id}/access/{appAccess}")
    public UserStatus updateUserAppAccessByUserId(@PathVariable int id, @PathVariable Boolean appAccess) {
        return manageUserStatusService.updateUserAppAccessByUserId(id, appAccess);
    }
}
