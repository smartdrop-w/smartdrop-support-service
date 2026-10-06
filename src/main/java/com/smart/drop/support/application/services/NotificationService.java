package com.smart.drop.support.application.services;

import com.smart.drop.support.domain.model.entities.Notification;
import com.smart.drop.support.domain.model.repositories.NotificationRepository;
import com.smart.drop.support.domain.model.ports.SmsServicePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SmsServicePort smsServicePort;

    public NotificationService(NotificationRepository notificationRepository, SmsServicePort smsServicePort) {
        this.notificationRepository = notificationRepository;
        this.smsServicePort = smsServicePort;
    }

    public Notification createNotification(Integer userId, String type, String message, String channel, String status) {
        Notification notification = Notification.create(userId, type, message, channel, status);
        Notification saved = notificationRepository.saveNotification(notification);

        if ("SMS".equalsIgnoreCase(channel)) {
            smsServicePort.sendSms("UNKNOWN", message);
        }

        return saved;
    }

    @Transactional(readOnly = true)
    public Optional<Notification> getNotificationById(Integer notificationId) {
        return notificationRepository.findNotificationById(notificationId);
    }

    @Transactional(readOnly = true)
    public List<Notification> getNotificationsByUserId(Integer userId) {
        return notificationRepository.findNotificationsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAllNotifications();
    }
}

