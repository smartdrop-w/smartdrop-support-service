package com.smart.drop.support.application.services;

import com.smart.drop.support.domain.model.entities.Alert;
import com.smart.drop.support.domain.model.entities.SupportTicket;
import com.smart.drop.support.domain.model.ports.TicketManagementPort;
import com.smart.drop.support.domain.model.repositories.AlertRepository;
import com.smart.drop.support.domain.model.repositories.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas BDD y Unitarias - Microservicio Support: Tickets de Soporte y Alertas Criticas")
class SupportAndAlertBddTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private TicketManagementPort ticketManagementPort;

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private SupportService supportService;

    @InjectMocks
    private AlertService alertService;

    @Test
    @DisplayName("US09 Escenario 1: Creacion de ticket de soporte urgente con estado OPEN y despacho externo")
    void shouldCreateUrgentSupportTicketSuccessfully() {
        // Given
        when(notificationRepository.saveSupportTicket(any(SupportTicket.class))).thenAnswer(inv -> {
            SupportTicket t = inv.getArgument(0);
            return new SupportTicket(701, t.userId(), t.subject(), t.priority(), t.description(), t.status(), LocalDateTime.now());
        });

        // When
        SupportTicket created = supportService.createSupportTicket(
                1001,
                "Sensor ESP32 desconectado",
                "high",
                "El nodo sensor de presion dejo de reportar telemetria"
        );

        // Then
        assertThat(created.ticketId()).isEqualTo(701);
        assertThat(created.status()).isEqualTo("OPEN");
        verify(ticketManagementPort, times(1))
                .createTicket("Sensor ESP32 desconectado", "El nodo sensor de presion dejo de reportar telemetria", "HIGH");
    }

    @Test
    @DisplayName("US09 Escenario 2: Transicion de estado de ticket de soporte a RESOLVED")
    void shouldUpdateSupportTicketStatusToResolved() {
        // Given
        SupportTicket existing = new SupportTicket(701, 1001, "Falla en sonda", "HIGH", "Revision", "OPEN", LocalDateTime.now());
        when(notificationRepository.findSupportTicketById(701)).thenReturn(Optional.of(existing));
        when(notificationRepository.saveSupportTicket(any(SupportTicket.class))).thenAnswer(inv -> inv.getArgument(0));

        // When
        SupportTicket updated = supportService.updateSupportTicketStatus(701, "RESOLVED");

        // Then
        assertThat(updated.status()).isEqualTo("RESOLVED");
    }

    @Test
    @DisplayName("US05/US09 Escenario 3: Creacion de alerta de fuga nocturna no resuelta")
    void shouldCreateAlertWithUnresolvedState() {
        // Given
        when(alertRepository.save(any(Alert.class))).thenAnswer(inv -> {
            Alert a = inv.getArgument(0);
            return new Alert(401, a.userId(), a.type(), a.title(), a.description(), a.resolved(), LocalDateTime.now());
        });

        // When
        Alert alert = alertService.createAlert(1001, "CRITICAL_LEAK", "Fuga Nocturna Detectada", "Flujo continuo a las 03:00 AM");

        // Then
        assertThat(alert.alertId()).isEqualTo(401);
        assertThat(alert.resolved()).isFalse();
    }

    @Test
    @DisplayName("US05/US09 Escenario 4: Resolucion de alerta critica activa")
    void shouldResolveActiveAlertSuccessfully() {
        // Given
        Alert active = new Alert(401, 1001, "CRITICAL_LEAK", "Fuga Nocturna", "Detalle", false, LocalDateTime.now());
        when(alertRepository.findById(401)).thenReturn(Optional.of(active));
        when(alertRepository.save(any(Alert.class))).thenAnswer(inv -> inv.getArgument(0));

        // When
        Alert resolved = alertService.resolveAlert(401);

        // Then
        assertThat(resolved.resolved()).isTrue();
    }

    @Test
    @DisplayName("US09 Escenario 5: Rechazo de creacion de alerta sin titulo valido")
    void shouldRejectAlertWithBlankTitle() {
        // When & Then
        assertThatThrownBy(() -> alertService.createAlert(1001, "WARNING", "   ", "Sin titulo"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Title cannot be null or blank");
    }
}
