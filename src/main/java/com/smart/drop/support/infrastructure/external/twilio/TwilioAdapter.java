package com.smart.drop.support.infrastructure.external.twilio;

import com.smart.drop.support.domain.model.ports.SmsServicePort;

public class TwilioAdapter implements SmsServicePort {

    @Override
    public SmsResult sendSms(String phoneNumber, String message) {
        return new SmsResult(true, "Twilio mock SMS sent to " + phoneNumber);
    }
}


