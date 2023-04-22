/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.subscription.client.domain;

/**
 * Added to entities to allow for notification type to be standardized when
 * publishing a message.
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
public interface NotificationBodyType {

    /**
     * A text value identifying the notification body type.
     * 
     * @return NotificationBodyType
     */
    NotificationType getBodyType();
}