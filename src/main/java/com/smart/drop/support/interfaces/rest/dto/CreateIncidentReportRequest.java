package com.smart.drop.support.interfaces.rest.dto;

public record CreateIncidentReportRequest(
        Integer adminId,
        String title,
        String description,
        String status
) {
}

