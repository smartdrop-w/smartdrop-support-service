package com.smart.drop.support.domain.model.ports;

/**
 * External SMS service port.
 */
public interface SmsServicePort {

    SmsResult sendSms(String phoneNumber, String message);

    record SmsResult(boolean success, String providerMessage) {
    }
}

