package com.smart.drop.support.interfaces.rest;

import com.smart.drop.support.application.services.AlertService;
import com.smart.drop.support.domain.model.entities.Alert;
import com.smart.drop.support.interfaces.rest.dto.CreateAlertRequest;
import com.smart.drop.support.interfaces.rest.dto.AlertResponse;
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
 * Controlador REST para gestión y resolución de alertas operativas.
 */
@Tag(name = "Critical Alerts Management", description = "Endpoints para registro, monitoreo y resolución de alertas críticas del sistema")
@RestController
@RequestMapping("/api/v1/support/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @Operation(summary = "Registrar nueva alerta", description = "Crea una alerta de nivel bajo, fuga o anomalía térmica para un usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alerta creada exitosamente", content = @Content(schema = @Schema(implementation = AlertResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parámetros de alerta inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<AlertResponse> create(@Valid @RequestBody CreateAlertRequest request) {
        Alert created = alertService.createAlert(
                request.userId(),
                request.type(),
                request.title(),
                request.description()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    @Operation(summary = "Obtener alerta por ID", description = "Recupera los datos de una alerta específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alerta encontrada", content = @Content(schema = @Schema(implementation = AlertResponse.class))),
            @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @GetMapping("/{alertId}")
    public ResponseEntity<AlertResponse> getById(@PathVariable Integer alertId) {
        return alertService.getById(alertId)
                .map(alert -> ResponseEntity.ok(toResponse(alert)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar alertas por usuario", description = "Retorna el historial de alertas emitidas para un usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Colección de alertas obtenida")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AlertResponse>> getByUserId(@PathVariable Integer userId) {
        List<AlertResponse> data = alertService.getByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Listar todas las alertas", description = "Retorna todas las alertas registradas en la plataforma.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista completa de alertas")
    })
    @GetMapping
    public ResponseEntity<List<AlertResponse>> getAll() {
        List<AlertResponse> data = alertService.getAll()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Resolver alerta", description = "Marca la condición de alerta como resuelta (resolved = true).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alerta resuelta exitosamente", content = @Content(schema = @Schema(implementation = AlertResponse.class))),
            @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @PutMapping("/{alertId}/resolve")
    public ResponseEntity<AlertResponse> resolve(@PathVariable Integer alertId) {
        Alert updated = alertService.resolveAlert(alertId);
        return ResponseEntity.ok(toResponse(updated));
    }

    @Operation(summary = "Eliminar alerta", description = "Remueve una alerta del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Alerta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @DeleteMapping("/{alertId}")
    public ResponseEntity<Void> delete(@PathVariable Integer alertId) {
        alertService.deleteById(alertId);
        return ResponseEntity.noContent().build();
    }

    private AlertResponse toResponse(Alert alert) {
        return new AlertResponse(
                alert.alertId(),
                alert.userId(),
                alert.type(),
                alert.title(),
                alert.description(),
                alert.resolved(),
                alert.createdAt()
        );
    }
}
