package com.smart.drop.support.interfaces.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Detalle del ticket de soporte técnico y estado de atención")
public record SupportTicketResponse(
        @Schema(description = "Identificador único del ticket", example = "501")
        Integer ticketId,

        @Schema(description = "Identificador del usuario", example = "2")
        Integer userId,

        @Schema(description = "Asunto del ticket", example = "Error en lectura de caudalímetro")
        String subject,

        @Schema(description = "Prioridad de atención", example = "HIGH")
        String priority,

        @Schema(description = "Descripción del problema reportado", example = "El sensor de caudal reporta valores constantes de 0.0 L/min.")
        String description,

        @Schema(description = "Estado del ticket (OPEN, IN_PROGRESS, RESOLVED, CLOSED)", example = "OPEN")
        String status,

        @Schema(description = "Fecha y hora de creación", example = "2026-11-13T23:30:00")
        LocalDateTime createdAt
) {
}
