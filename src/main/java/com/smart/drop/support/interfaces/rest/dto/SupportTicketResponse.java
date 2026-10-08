package com.smart.drop.support.interfaces.rest.dto;

import java.time.LocalDateTime;

public record SupportTicketResponse(
        Integer ticketId,
        Integer userId,
        String subject,
        String priority,
        String description,
        String status,
        LocalDateTime createdAt
) {
}
