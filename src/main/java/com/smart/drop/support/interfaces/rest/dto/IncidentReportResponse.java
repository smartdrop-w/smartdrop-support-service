package com.smart.drop.support.interfaces.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Detalle del reporte de incidente técnico y su trazabilidad de resolución")
public record IncidentReportResponse(
        @Schema(description = "Identificador único del incidente", example = "301")
        Integer incidentId,

        @Schema(description = "Identificador del administrador asignado", example = "1")
        Integer adminId,

        @Schema(description = "Título del incidente", example = "Falla de conectividad gateway LoRaWAN")
        String title,

        @Schema(description = "Descripción técnica", example = "Pérdida de paquetes de telemetría desde sensores de nivel")
        String description,

        @Schema(description = "Estado actual del ciclo de vida (OPEN, IN_PROGRESS, RESOLVED, CLOSED)", example = "RESOLVED")
        String status,

        @Schema(description = "Fecha y hora de registro", example = "2026-11-13T22:00:00")
        LocalDateTime createdAt,

        @Schema(description = "Fecha y hora de resolución efectiva", example = "2026-11-13T23:30:00")
        LocalDateTime resolvedAt
) {
}
