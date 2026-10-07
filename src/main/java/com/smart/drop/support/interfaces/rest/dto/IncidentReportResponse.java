package com.smart.drop.support.interfaces.rest.dto;

import java.time.LocalDateTime;

public record IncidentReportResponse(
        Integer incidentId,
        Integer adminId,
        String title,
        String description,
        String status,
        LocalDateTime createdAt,
        LocalDateTime resolvedAt
) {
}

