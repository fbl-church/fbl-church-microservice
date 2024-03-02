/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.email.client.domain;

import java.time.LocalDateTime;

import com.sendgrid.Email;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User Email class request that is used to send an email to users.
 * 
 * @author Sam Butler
 * @since January 28, 2023
 */
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "User email object.")
public class UserEmail {

    @Schema(description = "Who the email is coming from.")
    private Email from;

    @Schema(description = "Who the email is going too.")
    private Email recipient;

    @Schema(description = "The subject message of the email.")
    private String subject;

    @Schema(description = "The body of the message.")
    private String body;

    @Schema(description = "When the email was sent.")
    private LocalDateTime sentDate;

    public UserEmail(Email recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }
}
