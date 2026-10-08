package com.smart.drop.support.infrastructure.persistence.jpa.adapters;

import com.smart.drop.support.domain.model.entities.IncidentReport;
import com.smart.drop.support.domain.model.entities.Notification;
import com.smart.drop.support.domain.model.entities.SupportTicket;
import com.smart.drop.support.domain.model.repositories.NotificationRepository;
import com.smart.drop.support.infrastructure.persistence.jpa.entities.IncidentReportEntity;
import com.smart.drop.support.infrastructure.persistence.jpa.entities.NotificationEntity;
import com.smart.drop.support.infrastructure.persistence.jpa.entities.SupportTicketEntity;
import com.smart.drop.support.infrastructure.persistence.jpa.repositories.IncidentReportJpaRepository;
import com.smart.drop.support.infrastructure.persistence.jpa.repositories.NotificationJpaRepository;
import com.smart.drop.support.infrastructure.persistence.jpa.repositories.SupportTicketJpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class SupportRepositoryAdapter implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;
    private final SupportTicketJpaRepository supportTicketJpaRepository;
    private final IncidentReportJpaRepository incidentReportJpaRepository;

    public SupportRepositoryAdapter(NotificationJpaRepository notificationJpaRepository,
                                    SupportTicketJpaRepository supportTicketJpaRepository,
                                    IncidentReportJpaRepository incidentReportJpaRepository) {
        this.notificationJpaRepository = notificationJpaRepository;
        this.supportTicketJpaRepository = supportTicketJpaRepository;
        this.incidentReportJpaRepository = incidentReportJpaRepository;
    }

    @Override
    public Notification saveNotification(Notification notification) {
        NotificationEntity entity = toNotificationEntity(notification);
        NotificationEntity saved = notificationJpaRepository.save(entity);
        return toNotificationDomain(saved);
    }

    @Override
    public Optional<Notification> findNotificationById(Integer notificationId) {
        return notificationJpaRepository.findById(notificationId).map(this::toNotificationDomain);
    }

    @Override
    public List<Notification> findNotificationsByUserId(Integer userId) {
        return notificationJpaRepository.findByUserId(userId).stream().map(this::toNotificationDomain).toList();
    }

    @Override
    public List<Notification> findAllNotifications() {
        return notificationJpaRepository.findAll().stream().map(this::toNotificationDomain).toList();
    }

    @Override
    public SupportTicket saveSupportTicket(SupportTicket supportTicket) {
        SupportTicketEntity entity = toSupportTicketEntity(supportTicket);
        SupportTicketEntity saved = supportTicketJpaRepository.save(entity);
        return toSupportTicketDomain(saved);
    }

    @Override
    public Optional<SupportTicket> findSupportTicketById(Integer ticketId) {
        return supportTicketJpaRepository.findById(ticketId).map(this::toSupportTicketDomain);
    }

    @Override
    public List<SupportTicket> findSupportTicketsByUserId(Integer userId) {
        return supportTicketJpaRepository.findByUserId(userId).stream().map(this::toSupportTicketDomain).toList();
    }

    @Override
    public List<SupportTicket> findAllSupportTickets() {
        return supportTicketJpaRepository.findAll().stream().map(this::toSupportTicketDomain).toList();
    }

    @Override
    public void deleteSupportTicket(Integer ticketId) {
        supportTicketJpaRepository.deleteById(ticketId);
    }

    @Override
    public IncidentReport saveIncidentReport(IncidentReport incidentReport) {
        IncidentReportEntity entity = toIncidentReportEntity(incidentReport);
        IncidentReportEntity saved = incidentReportJpaRepository.save(entity);
        return toIncidentReportDomain(saved);
    }

    @Override
    public Optional<IncidentReport> findIncidentReportById(Integer incidentId) {
        return incidentReportJpaRepository.findById(incidentId).map(this::toIncidentReportDomain);
    }

    @Override
    public List<IncidentReport> findIncidentReportsByAdminId(Integer adminId) {
        return incidentReportJpaRepository.findByAdminId(adminId).stream().map(this::toIncidentReportDomain).toList();
    }

    @Override
    public List<IncidentReport> findAllIncidentReports() {
        return incidentReportJpaRepository.findAll().stream().map(this::toIncidentReportDomain).toList();
    }

    private NotificationEntity toNotificationEntity(Notification notification) {
        NotificationEntity entity = new NotificationEntity();
        entity.setNotificationId(notification.notificationId());
        entity.setUserId(notification.userId());
        entity.setType(notification.type());
        entity.setMessage(notification.message());
        entity.setChannel(notification.channel());
        entity.setSentAt(notification.sentAt() == null ? LocalDateTime.now() : notification.sentAt());
        entity.setStatus(notification.status());
        return entity;
    }

    private Notification toNotificationDomain(NotificationEntity entity) {
        return new Notification(entity.getNotificationId(), entity.getUserId(), entity.getType(), entity.getMessage(), entity.getChannel(), entity.getSentAt(), entity.getStatus());
    }

    private SupportTicketEntity toSupportTicketEntity(SupportTicket supportTicket) {
        SupportTicketEntity entity = new SupportTicketEntity();
        entity.setTicketId(supportTicket.ticketId());
        entity.setUserId(supportTicket.userId());
        entity.setSubject(supportTicket.subject());
        entity.setPriority(supportTicket.priority());
        entity.setDescription(supportTicket.description());
        entity.setStatus(supportTicket.status());
        entity.setCreatedAt(supportTicket.createdAt() == null ? LocalDateTime.now() : supportTicket.createdAt());
        return entity;
    }

    private SupportTicket toSupportTicketDomain(SupportTicketEntity entity) {
        return new SupportTicket(entity.getTicketId(), entity.getUserId(), entity.getSubject(), entity.getPriority(), entity.getDescription(), entity.getStatus(), entity.getCreatedAt());
    }

    private IncidentReportEntity toIncidentReportEntity(IncidentReport incidentReport) {
        IncidentReportEntity entity = new IncidentReportEntity();
        entity.setIncidentId(incidentReport.incidentId());
        entity.setAdminId(incidentReport.adminId());
        entity.setTitle(incidentReport.title());
        entity.setDescription(incidentReport.description());
        entity.setStatus(incidentReport.status());
        entity.setCreatedAt(incidentReport.createdAt() == null ? LocalDateTime.now() : incidentReport.createdAt());
        entity.setResolvedAt(incidentReport.resolvedAt());
        return entity;
    }

    private IncidentReport toIncidentReportDomain(IncidentReportEntity entity) {
        return new IncidentReport(entity.getIncidentId(), entity.getAdminId(), entity.getTitle(), entity.getDescription(), entity.getStatus(), entity.getCreatedAt(), entity.getResolvedAt());
    }
}


