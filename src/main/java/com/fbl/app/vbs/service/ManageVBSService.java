package com.fbl.app.vbs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.guardian.client.GuardianClient;
import com.fbl.app.guardian.client.domain.Guardian;
import com.fbl.app.user.client.UserClient;
import com.fbl.app.user.client.domain.User;
import com.fbl.app.vbs.client.domain.VBSRegistration;
import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.dao.VBSDAO;

/**
 * VBS Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service
public class ManageVBSService {

    @Autowired
    private VBSDAO vbsDao;

    @Autowired
    private GuardianClient guardianClient;

    @Autowired
    private UserClient userClient;

    /**
     * The theme to be created
     * 
     * @param theme The theme to create
     */
    public VBSTheme getThemeById(int id) {
        return vbsDao.getThemeById(id);
    }

    /**
     * The theme to be created
     * 
     * @param theme The theme to create
     */
    public VBSTheme createTheme(VBSTheme theme) {
        int id = vbsDao.createTheme(theme);
        return getThemeById(id);
    }

    /**
     * Takes in a list of children to register for VBS
     * 
     * @param registration The vbs registration
     */
    public void registerChildren(VBSRegistration registration) {
        // List<Guardian> guardians = processGuardians(registration.getGuardians());
        // processChildren(registration.getChildren(), guardians);
    }

    /**
     * Process the guardians on the vbs registration request
     * 
     * @param guardians The guardians to be processed
     * @return The created and processed guardians
     */
    private List<Guardian> processGuardians(List<Guardian> guardians) {
        List<Guardian> createdGuardians = new ArrayList<>();
        for (Guardian g : guardians) {
            // Check to see if Guardian exists already for that phone number
            Optional<Guardian> guardianFound = guardianClient.getGuardianByPhoneNumber(g.getPhone());

            if (guardianFound.isPresent()) {
                // If guardian was found, add them to the running list.
                createdGuardians.add(guardianFound.get());
            } else {
                // If no guardian was found, check if there is a user for the email already
                Optional<User> userFound = userClient.getUserByEmail(g.getEmail());
                if (userFound.isPresent()) {
                    // User exist already, assign the guardian role to the user account
                    createdGuardians.add(guardianClient.assignGuardianToExistingUser(userFound.get().getId(), g));
                } else {
                    // Guardian and User accounts don't exist, create them
                    createdGuardians.add(guardianClient.insertGuardian(g));
                }
            }
        }
        return createdGuardians;
    }

    /**
     * Processes the children to be registered for VBS.
     * 
     * @param children  The children to register
     * @param guardians The guardians of the children being registered.
     */
    private void processChildren(List<Child> children, List<Guardian> guardians) {
        for (Child c : children) {
            // TODO: Check to see if the child exists by first name, last name, and birthday
            // TODO: If they exist, update the existing child information
            // TODO: If they DON'T exist, create them
            // TODO: Check to see if the passed in guardians already exist on the child
            // TODO: if they do, then ignore them
            // TODO: If they DON'T, associate them to the child
        }
    }
}
