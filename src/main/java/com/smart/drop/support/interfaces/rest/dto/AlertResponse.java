package com.smart.drop.support.interfaces.rest.dto;

import java.time.LocalDateTime;

public record AlertResponse(
        Integer alertId,
        Integer userId,
        String type,
        String title,
        String description,
        Boolean resolved,
        LocalDateTime createdAt
) {
}
