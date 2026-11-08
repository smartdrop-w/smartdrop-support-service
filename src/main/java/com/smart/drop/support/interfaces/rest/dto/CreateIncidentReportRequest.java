package com.smart.drop.support.interfaces.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Solicitud para registrar un incidente técnico de infraestructura")
public record CreateIncidentReportRequest(
        @Schema(description = "Identificador del administrador", example = "1")
        @NotNull(message = "adminId is required")
        Integer adminId,

        @Schema(description = "Título del incidente", example = "Falla de conectividad gateway LoRaWAN")
        @NotBlank(message = "title is required")
        String title,

        @Schema(description = "Descripción técnica detallada", example = "Pérdida de paquetes de telemetría desde sensores de nivel")
        String description,

        @Schema(description = "Estado inicial (OPEN, IN_PROGRESS)", example = "OPEN")
        @NotBlank(message = "status is required")
        String status
) {
}
