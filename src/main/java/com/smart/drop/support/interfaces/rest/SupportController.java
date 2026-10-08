package com.smart.drop.support.interfaces.rest;

import com.smart.drop.support.application.services.SupportService;
import com.smart.drop.support.domain.model.entities.IncidentReport;
import com.smart.drop.support.domain.model.entities.SupportTicket;
import com.smart.drop.support.interfaces.rest.dto.CreateIncidentReportRequest;
import com.smart.drop.support.interfaces.rest.dto.CreateSupportTicketRequest;
import com.smart.drop.support.interfaces.rest.dto.IncidentReportResponse;
import com.smart.drop.support.interfaces.rest.dto.SupportTicketResponse;
import com.smart.drop.support.interfaces.rest.dto.UpdateSupportTicketStatusRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/support/management")
public class SupportController {

    private final SupportService supportService;

    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    @PostMapping("/tickets")
    public ResponseEntity<SupportTicketResponse> createTicket(@RequestBody CreateSupportTicketRequest request) {
        SupportTicket created = supportService.createSupportTicket(request.userId(), request.subject(), request.priority(), request.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(toTicketResponse(created));
    }

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<SupportTicketResponse> getTicketById(@PathVariable Integer ticketId) {
        return supportService.getSupportTicketById(ticketId)
                .map(ticket -> ResponseEntity.ok(toTicketResponse(ticket)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tickets/user/{userId}")
    public ResponseEntity<List<SupportTicketResponse>> getTicketsByUserId(@PathVariable Integer userId) {
        List<SupportTicketResponse> data = supportService.getSupportTicketsByUserId(userId).stream().map(this::toTicketResponse).toList();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<SupportTicketResponse>> getAllTickets() {
        List<SupportTicketResponse> data = supportService.getAllSupportTickets().stream().map(this::toTicketResponse).toList();
        return ResponseEntity.ok(data);
    }

    @PutMapping("/tickets/{ticketId}/status")
    public ResponseEntity<SupportTicketResponse> updateTicketStatus(@PathVariable Integer ticketId,
                                                                    @RequestBody UpdateSupportTicketStatusRequest request) {
        SupportTicket updated = supportService.updateSupportTicketStatus(ticketId, request.status());
        return ResponseEntity.ok(toTicketResponse(updated));
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/tickets/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer ticketId) {
        supportService.deleteSupportTicket(ticketId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/incidents")
    public ResponseEntity<IncidentReportResponse> createIncident(@RequestBody CreateIncidentReportRequest request) {
        IncidentReport created = supportService.createIncidentReport(
                request.adminId(),
                request.title(),
                request.description(),
                request.status()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toIncidentResponse(created));
    }

    @GetMapping("/incidents/{incidentId}")
    public ResponseEntity<IncidentReportResponse> getIncidentById(@PathVariable Integer incidentId) {
        return supportService.getIncidentReportById(incidentId)
                .map(incident -> ResponseEntity.ok(toIncidentResponse(incident)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/incidents/admin/{adminId}")
    public ResponseEntity<List<IncidentReportResponse>> getIncidentsByAdminId(@PathVariable Integer adminId) {
        List<IncidentReportResponse> data = supportService.getIncidentReportsByAdminId(adminId).stream().map(this::toIncidentResponse).toList();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/incidents")
    public ResponseEntity<List<IncidentReportResponse>> getAllIncidents() {
        List<IncidentReportResponse> data = supportService.getAllIncidentReports().stream().map(this::toIncidentResponse).toList();
        return ResponseEntity.ok(data);
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

