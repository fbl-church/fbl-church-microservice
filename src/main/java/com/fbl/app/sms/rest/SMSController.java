// package com.fbl.app.sms.rest;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.fbl.app.email.openapi.TagEmail;
// import com.fbl.app.sms.client.SMSMessengerClient;
// import com.fbl.app.sms.client.domain.SMSVerificationStatus;
// import com.fbl.app.sms.client.domain.SMSVerifier;
// import com.fbl.common.annotations.interfaces.RestApiController;
// import com.twilio.rest.verify.v2.service.Verification;

// import jakarta.validation.constraints.NotNull;

// /**
// * SMS Controller for dealing with sending text to users.
// *
// * @author Sam Butler
// * @since August 1, 2021
// */
// @RequestMapping("/api/sms")
// @RestApiController
// @TagEmail
// public class SMSController {
// @Autowired
// private SMSMessengerClient client;

// /**
// * This will send a verification text to the user.
// *
// * @param phone The phone number to send too
// */
// @PostMapping("/send-verification")
// public Verification sendVerificationText(@RequestBody @NotNull String phone)
// {
// return client.sendVerificationText(phone);
// }

// /**
// * This will verify a code against a phone number
// *
// * @param phone The phone number that got the message
// * @param code The code to check against
// */
// @PostMapping("/verify")
// public SMSVerificationStatus verifyCode(@RequestBody @NotNull SMSVerifier
// verifier) {
// return client.verifyCode(verifier);
// }
// }
