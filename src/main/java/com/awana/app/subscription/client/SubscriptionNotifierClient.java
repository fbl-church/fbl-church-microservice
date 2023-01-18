package com.awana.app.subscription.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.socket.server.WebSocketService;

import com.awana.app.subscription.client.domain.Notification;
import com.awana.app.subscription.client.domain.NotificationSocket;
import com.awana.app.subscription.client.domain.UserPrincipal;
import com.awana.app.subscription.service.SubscriptionNotifierService;
import com.awana.app.user.client.domain.WebRole;
import com.awana.common.annotations.interfaces.Client;

/**
 * Client for {@link WebSocketService} to expose the given endpoint's to other
 * services.
 * 
 * @author Sam Butler
 * @since Dec 14, 2020
 */
@Client
public class SubscriptionNotifierClient {

    @Autowired
    private SubscriptionNotifierService service;

    /**
     * Push a web notification to a user for the given user id. The default socket
     * this notification will be sent to
     * {@link NotificationSocket#QUEUE_USER_NOTIFICATION}.
     * 
     * @param body   The body to be sent.
     * @param socket The socket path the notification should be sent too.
     * @param userId The user id of the user to send it too.
     */
    public void sendToUser(Notification body, int userId) {
        service.sendToUser(body, userId);
    }

    /**
     * Push a web notification to list of users with the given role. It will perform
     * a notification action with the passed in notification body. The default
     * socket this notification will be sent to
     * {@link NotificationSocket#QUEUE_USER_NOTIFICATION}.
     * 
     * @param body The body to be sent.
     * @param role The role of the user to send it too.
     */
    public void sendToUser(Notification body, WebRole role) {
        service.sendToUser(body, role);
    }

    /**
     * Push a web notification. Default path this will be sent to is
     * {@link NotificationSocket#GENERAL}
     * 
     * @param body The body to be sent.
     */
    public void send(Notification body) {
        service.send(body);
    }

    /**
     * Push a web notification to the passed in socket url.
     * 
     * @param body   The body to be sent.
     * @param socket The socket path the notification should be sent too.
     */
    public void send(Notification body, String socket) {
        service.send(body, socket);
    }

    /**
     * Push a web notification based on the given session id. Only the client with
     * the specified session id will receive the notification.
     * 
     * @param body      The body to be sent.
     * @param socket    The socket path the notification should be sent too.
     * @param sessionId The session of id of the client to send the notification
     *                  too.
     */
    public void send(Notification body, String socket, String sessionId) {
        service.send(body, socket, sessionId);
    }

    /**
     * Will get the active users connected to the websocket session.
     * 
     * @return List of SimpUser connections.
     */
    public List<UserPrincipal> getActiveUserSessions() {
        return service.getActiveUserSessions();
    }
}