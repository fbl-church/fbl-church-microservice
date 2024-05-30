package com.fbl.app.sms.client.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * SMS Verifier
 * 
 * @author Sam Butler
 * @since May 29, 2024
 */
@Getter
@Setter
public class SMSVerifier {
    private String phone;
    private String code;
}
