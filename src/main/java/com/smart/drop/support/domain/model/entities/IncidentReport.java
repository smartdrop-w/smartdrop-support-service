package com.smart.drop.support.domain.model.entities;

import java.time.LocalDateTime;

/**
 * Immutable domain entity for incident reports.
 */
public record IncidentReport(
        Integer incidentId,
        Integer adminId,
        String title,
        String description,
        String status,
        LocalDateTime createdAt,
        LocalDateTime resolvedAt
) {
    public IncidentReport {
        if (title != null && title.length() > 100) {
            throw new IllegalArgumentException("title must be <= 100 characters");
        }
        if (status != null && status.length() > 20) {
            throw new IllegalArgumentException("status must be <= 20 characters");
        }
    }

    public static IncidentReport create(Integer adminId, String title, String description, String status) {
        return new IncidentReport(null, adminId, title, description, status, LocalDateTime.now(), null);
    }

    public IncidentReport resolve() {
        return new IncidentReport(incidentId, adminId, title, description, status, createdAt, LocalDateTime.now());
    }
}

