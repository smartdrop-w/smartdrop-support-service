package com.smart.drop.support.interfaces.rest;

import com.smart.drop.support.application.services.NotificationService;
import com.smart.drop.support.domain.model.entities.Notification;
import com.smart.drop.support.interfaces.rest.dto.CreateNotificationRequest;
import com.smart.drop.support.interfaces.rest.dto.NotificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión y envío de notificaciones multicanal (Email, SMS, Push).
 */
@Tag(name = "Customer Notifications", description = "Endpoints para registro y consulta de notificaciones operativas multicanal")
@RestController
@RequestMapping("/api/v1/support/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Operation(summary = "Crear y emitir notificación", description = "Registra una notificación y la envía al usuario a través del canal configurado.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Notificación creada y enviada", content = @Content(schema = @Schema(implementation = NotificationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parámetros de notificación inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<NotificationResponse> create(@Valid @RequestBody CreateNotificationRequest request) {
        Notification created = notificationService.createNotification(
                request.userId(),
                request.type(),
                request.message(),
                request.channel(),
                request.status()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    @Operation(summary = "Obtener notificación por ID", description = "Recupera los datos de una notificación por su ID numérico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación encontrada", content = @Content(schema = @Schema(implementation = NotificationResponse.class))),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResponse> getById(@PathVariable Integer notificationId) {
        return notificationService.getNotificationById(notificationId)
                .map(notification -> ResponseEntity.ok(toResponse(notification)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar notificaciones por usuario", description = "Retorna el historial de mensajes enviados a un cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial de notificaciones recuperado")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getByUserId(@PathVariable Integer userId) {
        List<NotificationResponse> data = notificationService.getNotificationsByUserId(userId).stream().map(this::toResponse).toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Listar todas las notificaciones", description = "Retorna el historial completo de notificaciones emitidas por la plataforma.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista completa de notificaciones")
    })
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAll() {
        List<NotificationResponse> data = notificationService.getAllNotifications().stream().map(this::toResponse).toList();
        return ResponseEntity.ok(data);
    }

    private NotificationResponse toResponse(Notification notification) {
        return new NotificationResponse(
                notification.notificationId(),
                notification.userId(),
                notification.type(),
                notification.message(),
                notification.channel(),
                notification.sentAt(),
                notification.status()
        );
    }
}
