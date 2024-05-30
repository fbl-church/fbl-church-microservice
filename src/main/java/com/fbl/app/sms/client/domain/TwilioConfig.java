package com.fbl.app.sms.client.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Description
 * 
 * @author Sam Butler
 * @since May 29, 2024
 */
@Component
@ConfigurationProperties("twilio")
@Data
public class TwilioConfig {
    @JsonProperty("account-sid")
    private String accountSID;

    @JsonProperty("auth-token")
    private String authToken;

    @JsonProperty("verification-sid")
    private String verificationSID;
}
