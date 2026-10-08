package com.smart.drop.support.domain.model.repositories;

import com.smart.drop.support.domain.model.entities.IncidentReport;
import com.smart.drop.support.domain.model.entities.Notification;
import com.smart.drop.support.domain.model.entities.SupportTicket;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for support and notifications.
 */
public interface NotificationRepository {

    Notification saveNotification(Notification notification);

    Optional<Notification> findNotificationById(Integer notificationId);

    List<Notification> findNotificationsByUserId(Integer userId);

    List<Notification> findAllNotifications();

    SupportTicket saveSupportTicket(SupportTicket supportTicket);

    Optional<SupportTicket> findSupportTicketById(Integer ticketId);

    List<SupportTicket> findSupportTicketsByUserId(Integer userId);

    List<SupportTicket> findAllSupportTickets();

    void deleteSupportTicket(Integer ticketId);

    IncidentReport saveIncidentReport(IncidentReport incidentReport);

    Optional<IncidentReport> findIncidentReportById(Integer incidentId);

    List<IncidentReport> findIncidentReportsByAdminId(Integer adminId);

    List<IncidentReport> findAllIncidentReports();
}

