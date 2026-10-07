package com.smart.drop.support.interfaces.rest.dto;

import java.time.LocalDateTime;

public record NotificationResponse(
        Integer notificationId,
        Integer userId,
        String type,
        String message,
        String channel,
        LocalDateTime sentAt,
        String status
) {
}

