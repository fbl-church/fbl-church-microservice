/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.email.client.domain;

import java.time.LocalDateTime;

import com.sendgrid.Email;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * User Email class request that is used to send an email to users.
 * 
 * @author Sam Butler
 * @since January 28, 2023
 */
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

    public UserEmail() {
    }

    public UserEmail(Email recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    public Email getFrom() {
        return from;
    }

    public void setFrom(Email from) {
        this.from = from;
    }

    public Email getRecipient() {
        return recipient;
    }

    public void setRecipient(Email recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

}
