/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.subscription.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.WebRole;
import com.fbl.subscription.client.domain.NotificationSocket;
import com.fbl.subscription.client.domain.UserPrincipal;
import com.fbl.subscription.notification.UserNotification;
import com.fbl.subscription.openapi.TagSubscription;
import com.fbl.subscription.service.SubscriptionNotifierService;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/subscription-app")
@RestApiController
@TagSubscription
public class SubscriptionTestController {

    @Autowired
    private SubscriptionNotifierService service;

    /**
     * Will get the active users connected to the websocket session.
     * 
     * @return List of SimpUser connections.
     */
    @Operation(summary = "Get's a list of active user sessions", description = "Will return a list of SimpUser objects of connected sessions.")
    @GetMapping(path = "/users")
    @HasAccess(WebRole.SITE_ADMIN)
    public List<UserPrincipal> getActiveSessionUsers() {
        return service.getActiveUserSessions();
    }

    /**
     * Test endpoint for sending a notification body to everyone.
     */
    @PostMapping(path = "/notification")
    @HasAccess(WebRole.SITE_ADMIN)
    public void pushGeneralNotification() {
        UserNotification user = new UserNotification();
        user.setName("TEST USER");
        user.setUserId(15);
        service.send(user);
    }

    /**
     * Test endpoint for sending a notification body to the given user.
     */
    @PostMapping(path = "/user/{userId}/notification")
    @HasAccess(WebRole.SITE_ADMIN)
    public void pushUserNotification(@PathVariable int userId) {
        UserNotification user = new UserNotification();
        user.setName("TEST USER");
        user.setUserId(15);
        service.sendToUser(user, userId);
    }

    /**
     * Test endpoint for sending a notification body to the given system.
     */
    @PostMapping(path = "/system/{uuid}/notification")
    @HasAccess(WebRole.SITE_ADMIN)
    public void pushSystemNotification(@PathVariable String uuid) {
        UserNotification user = new UserNotification();
        user.setName("TEST USER");
        user.setUserId(15);
        service.send(user, NotificationSocket.QUEUE_SYSTEM_NOTIFICATION, uuid);
    }
}