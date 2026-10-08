package com.smart.drop.support.interfaces.rest.dto;

public record CreateAlertRequest(
        Integer userId,
        String type,
        String title,
        String description
) {
}
