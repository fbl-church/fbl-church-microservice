// package com.fbl.app.sms.client;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.util.StringUtils;

// import com.fbl.app.sms.client.domain.SMSVerificationStatus;
// import com.fbl.app.sms.client.domain.SMSVerifier;
// import com.fbl.app.sms.client.domain.TwilioConfig;
// import com.fbl.common.annotations.interfaces.Client;
// import com.twilio.Twilio;
// import com.twilio.rest.verify.v2.service.Verification;
// import com.twilio.rest.verify.v2.service.VerificationCheck;

// /**
// * SMS Messenger Client
// *
// * @author Sam Butler
// * @since May 29, 2024
// */
// @Client
// public class SMSMessengerClient {

// @Autowired
// private TwilioConfig twilioConfig;

// /**
// * This will send a verification text to the user.
// *
// * @param phone The phone number to send too
// */
// public Verification sendVerificationText(String phoneNumber) {
// Twilio.init(twilioConfig.getAccountSID(), twilioConfig.getAuthToken());
// Verification verifier = Verification
// .creator(twilioConfig.getVerificationSID(),
// convertPhoneToTwilioFormat(phoneNumber), "sms").create();
// return verifier;
// }

// /**
// * This will verify a code against a phone number
// *
// * @param verifier The phone number and code to verify
// */
// public SMSVerificationStatus verifyCode(SMSVerifier verifier) {
// Twilio.init(twilioConfig.getAccountSID(), twilioConfig.getAuthToken());

// if (!StringUtils.hasText(verifier.getPhone()) ||
// !StringUtils.hasText(verifier.getCode())) {
// return SMSVerificationStatus.DENIED;
// }

// try {
// VerificationCheck.creator(
// twilioConfig.getVerificationSID())
// .setTo(convertPhoneToTwilioFormat(verifier.getPhone()))
// .setCode(verifier.getCode())
// .create();

// return SMSVerificationStatus.APPROVED;

// } catch (Exception e) {
// return SMSVerificationStatus.DENIED;
// }
// }

// private String convertPhoneToTwilioFormat(String phone) {
// return "+1" + phone.replaceAll("\\D+", "");
// }
// }
