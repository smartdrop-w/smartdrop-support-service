package com.smart.drop.support.interfaces.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Solicitud para actualizar el estado de un ticket de soporte")
public record UpdateSupportTicketStatusRequest(
        @Schema(description = "Nuevo estado del ticket (OPEN, IN_PROGRESS, RESOLVED, CLOSED)", example = "RESOLVED")
        @NotBlank(message = "status is required")
        String status
) {
}
