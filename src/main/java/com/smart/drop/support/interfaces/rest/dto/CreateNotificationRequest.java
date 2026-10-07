package com.smart.drop.support.interfaces.rest.dto;

public record CreateNotificationRequest(
        Integer userId,
        String type,
        String message,
        String channel,
        String status
) {
}

