package com.smart.drop.support.domain.model.entities;

import java.time.LocalDateTime;

/**
 * Immutable domain entity for support tickets.
 */
public record SupportTicket(
        Integer ticketId,
        Integer userId,
        String subject,
        String priority,
        String description,
        String status,
        LocalDateTime createdAt
) {
    public SupportTicket {
        if (description != null && description.isBlank()) {
            throw new IllegalArgumentException("description cannot be blank");
        }
        if (status != null && status.length() > 20) {
            throw new IllegalArgumentException("status must be <= 20 characters");
        }
    }

    public static SupportTicket create(Integer userId, String subject, String priority, String description) {
        return new SupportTicket(null, userId, subject, priority, description, "OPEN", LocalDateTime.now());
    }

    public SupportTicket withStatus(String newStatus) {
        return new SupportTicket(ticketId, userId, subject, priority, description, newStatus, createdAt);
    }
}

