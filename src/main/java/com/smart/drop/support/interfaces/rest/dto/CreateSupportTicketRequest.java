package com.smart.drop.support.interfaces.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Solicitud para abrir un nuevo ticket de soporte")
public record CreateSupportTicketRequest(
        @Schema(description = "Identificador del usuario solicitante", example = "2")
        @NotNull(message = "userId is required")
        Integer userId,

        @Schema(description = "Asunto del ticket", example = "Error en lectura de caudalímetro")
        @NotBlank(message = "subject is required")
        String subject,

        @Schema(description = "Prioridad (LOW, MEDIUM, HIGH, URGENT)", example = "HIGH")
        @NotBlank(message = "priority is required")
        String priority,

        @Schema(description = "Descripción detallada del problema", example = "El sensor de caudal reporta valores constantes de 0.0 L/min.")
        String description
) {
}
