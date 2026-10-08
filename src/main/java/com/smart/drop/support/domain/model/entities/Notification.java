package com.smart.drop.support.domain.model.entities;

import java.time.LocalDateTime;

/**
 * Immutable domain entity for notifications.
 */
public record Notification(
        Integer notificationId,
        Integer userId,
        String type,
        String message,
        String channel,
        LocalDateTime sentAt,
        String status
) {
    public Notification {
        if (type != null && type.length() > 50) {
            throw new IllegalArgumentException("type must be <= 50 characters");
        }
        if (channel != null && channel.length() > 20) {
            throw new IllegalArgumentException("channel must be <= 20 characters");
        }
        if (status != null && status.length() > 20) {
            throw new IllegalArgumentException("status must be <= 20 characters");
        }
    }

    public static Notification create(Integer userId, String type, String message, String channel, String status) {
        return new Notification(null, userId, type, message, channel, LocalDateTime.now(), status);
    }
}

