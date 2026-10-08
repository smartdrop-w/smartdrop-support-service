package com.smart.drop.support.interfaces.rest.dto;

public record CreateSupportTicketRequest(
        Integer userId,
        String subject,
        String priority,
        String description
) {
}
