package com.smart.drop.support.interfaces.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Detalle de notificación emitida y canal de entrega")
public record NotificationResponse(
        @Schema(description = "Identificador único de notificación", example = "401")
        Integer notificationId,

        @Schema(description = "Identificador del usuario", example = "2")
        Integer userId,

        @Schema(description = "Tipo de notificación", example = "ALERT")
        String type,

        @Schema(description = "Mensaje emitido", example = "Su nivel de agua en Tanque 1 ha descendido al 20%.")
        String message,

        @Schema(description = "Canal de envío", example = "EMAIL")
        String channel,

        @Schema(description = "Fecha y hora de envío", example = "2026-11-13T23:30:00")
        LocalDateTime sentAt,

        @Schema(description = "Estado de entrega (SENT, DELIVERED, FAILED)", example = "SENT")
        String status
) {
}
