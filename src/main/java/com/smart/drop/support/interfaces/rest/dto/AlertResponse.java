package com.smart.drop.support.interfaces.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Detalle de alerta operativa o crítica emitida por la plataforma")
public record AlertResponse(
        @Schema(description = "Identificador único de la alerta", example = "1")
        Integer alertId,

        @Schema(description = "Identificador del usuario", example = "2")
        Integer userId,

        @Schema(description = "Tipo de alerta (CRITICAL_LEAK, LOW_RESERVE, THERMAL_LIMIT)", example = "CRITICAL_LEAK")
        String type,

        @Schema(description = "Título de la alerta", example = "Fuga Crítica Detectada")
        String title,

        @Schema(description = "Descripción del incidente", example = "Descenso de nivel anómalo de 8.5 cm/h en Tanque 1")
        String description,

        @Schema(description = "Indicador de alerta resuelta", example = "false")
        Boolean resolved,

        @Schema(description = "Fecha y hora de emisión", example = "2026-11-13T23:30:00")
        LocalDateTime createdAt
) {
}
