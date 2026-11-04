package com.smart.drop.support.interfaces.rest;

import com.smart.drop.support.application.services.SupportService;
import com.smart.drop.support.domain.model.entities.IncidentReport;
import com.smart.drop.support.domain.model.entities.SupportTicket;
import com.smart.drop.support.interfaces.rest.dto.*;
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
 * Controlador REST para gestión de incidentes técnicos y tickets de soporte al cliente.
 */
@Tag(name = "Support Tickets & Incident Management", description = "Endpoints para ciclo de vida de tickets de soporte e incidentes técnicos de infraestructura")
@RestController
@RequestMapping("/api/v1/support/management")
public class SupportController {

    private final SupportService supportService;

    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    @Operation(summary = "Crear nuevo ticket de soporte", description = "Abre un nuevo ticket de soporte técnico para el usuario y sincroniza con el adaptador de gestión de tickets.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ticket creado exitosamente", content = @Content(schema = @Schema(implementation = SupportTicketResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de ticket inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/tickets")
    public ResponseEntity<SupportTicketResponse> createTicket(@Valid @RequestBody CreateSupportTicketRequest request) {
        SupportTicket created = supportService.createSupportTicket(request.userId(), request.subject(), request.priority(), request.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(toTicketResponse(created));
    }

    @Operation(summary = "Obtener ticket por ID", description = "Recupera la información y estado de un ticket por su identificador numérico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ticket encontrado", content = @Content(schema = @Schema(implementation = SupportTicketResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<SupportTicketResponse> getTicketById(@PathVariable Integer ticketId) {
        return supportService.getSupportTicketById(ticketId)
                .map(ticket -> ResponseEntity.ok(toTicketResponse(ticket)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar tickets por usuario", description = "Retorna todos los tickets de soporte generados por un cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tickets recuperada exitosamente")
    })
    @GetMapping("/tickets/user/{userId}")
    public ResponseEntity<List<SupportTicketResponse>> getTicketsByUserId(@PathVariable Integer userId) {
        List<SupportTicketResponse> data = supportService.getSupportTicketsByUserId(userId).stream().map(this::toTicketResponse).toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Listar todos los tickets", description = "Retorna la colección completa de tickets de soporte técnico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista completa de tickets")
    })
    @GetMapping("/tickets")
    public ResponseEntity<List<SupportTicketResponse>> getAllTickets() {
        List<SupportTicketResponse> data = supportService.getAllSupportTickets().stream().map(this::toTicketResponse).toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Actualizar estado de ticket de soporte", description = "Modifica el estado de atención del ticket (OPEN, IN_PROGRESS, RESOLVED, CLOSED).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado de ticket actualizado", content = @Content(schema = @Schema(implementation = SupportTicketResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @PutMapping("/tickets/{ticketId}/status")
    public ResponseEntity<SupportTicketResponse> updateTicketStatus(@PathVariable Integer ticketId,
                                                                    @Valid @RequestBody UpdateSupportTicketStatusRequest request) {
        SupportTicket updated = supportService.updateSupportTicketStatus(ticketId, request.status());
        return ResponseEntity.ok(toTicketResponse(updated));
    }

    @Operation(summary = "Eliminar ticket de soporte", description = "Remueve un ticket de soporte de la plataforma.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ticket eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @DeleteMapping("/tickets/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer ticketId) {
        supportService.deleteSupportTicket(ticketId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Registrar nuevo incidente técnico", description = "Crea un reporte de incidente de infraestructura o servicio.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Incidente registrado exitosamente", content = @Content(schema = @Schema(implementation = IncidentReportResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parámetros de incidente inválidos")
    })
    @PostMapping("/incidents")
    public ResponseEntity<IncidentReportResponse> createIncident(@Valid @RequestBody CreateIncidentReportRequest request) {
        IncidentReport created = supportService.createIncidentReport(
                request.adminId(),
                request.title(),
                request.description(),
                request.status()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toIncidentResponse(created));
    }

    @Operation(summary = "Obtener incidente por ID", description = "Recupera la ficha de un incidente por su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Incidente encontrado", content = @Content(schema = @Schema(implementation = IncidentReportResponse.class))),
            @ApiResponse(responseCode = "404", description = "Incidente no encontrado")
    })
    @GetMapping("/incidents/{incidentId}")
    public ResponseEntity<IncidentReportResponse> getIncidentById(@PathVariable Integer incidentId) {
        return supportService.getIncidentReportById(incidentId)
                .map(incident -> ResponseEntity.ok(toIncidentResponse(incident)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar incidentes por administrador", description = "Retorna los incidentes registrados o atendidos por un administrador específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Colección de incidentes obtenida")
    })
    @GetMapping("/incidents/admin/{adminId}")
    public ResponseEntity<List<IncidentReportResponse>> getIncidentsByAdminId(@PathVariable Integer adminId) {
        List<IncidentReportResponse> data = supportService.getIncidentReportsByAdminId(adminId).stream().map(this::toIncidentResponse).toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Listar todos los incidentes técnicos", description = "Retorna el historial completo de incidentes registrados en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista completa de incidentes")
    })
    @GetMapping("/incidents")
    public ResponseEntity<List<IncidentReportResponse>> getAllIncidents() {
        List<IncidentReportResponse> data = supportService.getAllIncidentReports().stream().map(this::toIncidentResponse).toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Actualizar estado del incidente", description = "Modifica el estado de atención del incidente técnico (OPEN, IN_PROGRESS, RESOLVED, CLOSED).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado del incidente actualizado", content = @Content(schema = @Schema(implementation = IncidentReportResponse.class))),
            @ApiResponse(responseCode = "404", description = "Incidente no encontrado")
    })
    @PutMapping("/incidents/{incidentId}/status")
    public ResponseEntity<IncidentReportResponse> updateIncidentStatus(
            @PathVariable Integer incidentId,
            @Valid @RequestBody UpdateIncidentStatusRequest request) {
        IncidentReport updated = supportService.updateIncidentReportStatus(incidentId, request.status());
        return ResponseEntity.ok(toIncidentResponse(updated));
    }

    @Operation(summary = "Resolver incidente técnico", description = "Establece el estado del incidente a RESOLVED y registra la marca de tiempo de resolución.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Incidente resuelto exitosamente", content = @Content(schema = @Schema(implementation = IncidentReportResponse.class))),
            @ApiResponse(responseCode = "404", description = "Incidente no encontrado")
    })
    @PutMapping("/incidents/{incidentId}/resolve")
    public ResponseEntity<IncidentReportResponse> resolveIncident(@PathVariable Integer incidentId) {
        IncidentReport resolved = supportService.resolveIncidentReport(incidentId);
        return ResponseEntity.ok(toIncidentResponse(resolved));
    }

    private SupportTicketResponse toTicketResponse(SupportTicket ticket) {
        return new SupportTicketResponse(ticket.ticketId(), ticket.userId(), ticket.subject(), ticket.priority(), ticket.description(), ticket.status(), ticket.createdAt());
    }

    private IncidentReportResponse toIncidentResponse(IncidentReport incident) {
        return new IncidentReportResponse(
                incident.incidentId(),
                incident.adminId(),
                incident.title(),
                incident.description(),
                incident.status(),
                incident.createdAt(),
                incident.resolvedAt()
        );
    }
}
