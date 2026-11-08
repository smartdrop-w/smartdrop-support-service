package com.smart.drop.support.interfaces.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Solicitud para registrar o emitir una notificación al usuario")
public record CreateNotificationRequest(
        @Schema(description = "Identificador del usuario destinatario", example = "2")
        @NotNull(message = "userId is required")
        Integer userId,

        @Schema(description = "Tipo de notificación (ALERT, REFILL_REMINDER, SYSTEM)", example = "ALERT")
        @NotBlank(message = "type is required")
        String type,

        @Schema(description = "Contenido del mensaje a transmitir", example = "Su nivel de agua en Tanque 1 ha descendido al 20%.")
        @NotBlank(message = "message is required")
        String message,

        @Schema(description = "Canal de emisión (EMAIL, SMS, PUSH)", example = "EMAIL")
        @NotBlank(message = "channel is required")
        String channel,

        @Schema(description = "Estado inicial del envío (PENDING, SENT, DELIVERED)", example = "SENT")
        String status
) {
}
