package com.smart.drop.support.domain.model.entities;

import java.time.LocalDateTime;

/**
 * Immutable domain entity representing system alerts.
 */
public record Alert(
        Integer alertId,
        Integer userId,
        String type,
        String title,
        String description,
        Boolean resolved,
        LocalDateTime createdAt
) {
    public Alert {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
    }

    public static Alert create(Integer userId, String type, String title, String description) {
        return new Alert(null, userId, type, title, description, false, LocalDateTime.now());
    }

    public Alert resolve() {
        return new Alert(alertId, userId, type, title, description, true, createdAt);
    }
}
