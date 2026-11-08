package com.smart.drop.support.interfaces.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Solicitud para registrar una nueva alerta")
public record CreateAlertRequest(
        @Schema(description = "Identificador del usuario", example = "2")
        @NotNull(message = "userId is required")
        Integer userId,

        @Schema(description = "Tipo de alerta (CRITICAL_LEAK, LOW_RESERVE, THERMAL_LIMIT)", example = "CRITICAL_LEAK")
        @NotBlank(message = "type is required")
        String type,

        @Schema(description = "Título descriptivo", example = "Fuga Crítica Detectada")
        @NotBlank(message = "title is required")
        String title,

        @Schema(description = "Detalle del evento", example = "Descenso de nivel de 8.5 cm/h en Tanque 1")
        String description
) {
}
