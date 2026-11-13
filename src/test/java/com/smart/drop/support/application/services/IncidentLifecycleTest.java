package com.smart.drop.support.application.services;

import com.smart.drop.support.domain.model.entities.IncidentReport;
import com.smart.drop.support.domain.model.ports.TicketManagementPort;
import com.smart.drop.support.domain.model.repositories.NotificationRepository;
import com.smart.drop.support.interfaces.rest.SupportController;
import com.smart.drop.support.interfaces.rest.dto.IncidentReportResponse;
import com.smart.drop.support.interfaces.rest.dto.UpdateIncidentStatusRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - Ciclo de Vida y Resolución de Incidentes Técnicos")
class IncidentLifecycleTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private TicketManagementPort ticketManagementPort;

    private SupportService supportService;
    private SupportController supportController;

    @BeforeEach
    void setUp() {
        supportService = new SupportService(notificationRepository, ticketManagementPort);
        supportController = new SupportController(supportService);
    }

    @Test
    @DisplayName("Debe actualizar el estado del incidente a IN_PROGRESS mediante PUT /incidents/{id}/status")
    void shouldUpdateIncidentStatus() {
        // Arrange
        IncidentReport incident = new IncidentReport(10, 1, "Gateway Caído", "Sin señal", "OPEN", LocalDateTime.now(), null);
        when(notificationRepository.findIncidentReportById(10)).thenReturn(Optional.of(incident));
        when(notificationRepository.saveIncidentReport(any(IncidentReport.class))).thenAnswer(inv -> inv.getArgument(0));

        UpdateIncidentStatusRequest request = new UpdateIncidentStatusRequest("IN_PROGRESS");

        // Act
        ResponseEntity<IncidentReportResponse> response = supportController.updateIncidentStatus(10, request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo("IN_PROGRESS");
        assertThat(response.getBody().resolvedAt()).isNull();
    }

    @Test
    @DisplayName("Debe resolver el incidente y estampar timestamp resolvedAt mediante PUT /incidents/{id}/resolve")
    void shouldResolveIncidentAndSetResolvedAtTimestamp() {
        // Arrange
        IncidentReport incident = new IncidentReport(10, 1, "Gateway Caído", "Sin señal", "IN_PROGRESS", LocalDateTime.now(), null);
        when(notificationRepository.findIncidentReportById(10)).thenReturn(Optional.of(incident));
        when(notificationRepository.saveIncidentReport(any(IncidentReport.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        ResponseEntity<IncidentReportResponse> response = supportController.resolveIncident(10);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo("RESOLVED");
        assertThat(response.getBody().resolvedAt()).isNotNull();
        verify(notificationRepository, times(1)).saveIncidentReport(any(IncidentReport.class));
    }
}
