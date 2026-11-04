package com.smart.drop.support.interfaces.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Solicitud para actualizar el estado del reporte de incidente técnico")
public record UpdateIncidentStatusRequest(
        @Schema(description = "Nuevo estado del incidente (OPEN, IN_PROGRESS, RESOLVED, CLOSED)", example = "IN_PROGRESS")
        @NotBlank(message = "status is required")
        String status
) {
}
