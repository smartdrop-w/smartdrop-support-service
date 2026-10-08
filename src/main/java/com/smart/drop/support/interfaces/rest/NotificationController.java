package com.smart.drop.support.interfaces.rest;

import com.smart.drop.support.application.services.NotificationService;
import com.smart.drop.support.domain.model.entities.Notification;
import com.smart.drop.support.interfaces.rest.dto.CreateNotificationRequest;
import com.smart.drop.support.interfaces.rest.dto.NotificationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/support/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> create(@RequestBody CreateNotificationRequest request) {
        Notification created = notificationService.createNotification(
                request.userId(),
                request.type(),
                request.message(),
                request.channel(),
                request.status()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResponse> getById(@PathVariable Integer notificationId) {
        return notificationService.getNotificationById(notificationId)
                .map(notification -> ResponseEntity.ok(toResponse(notification)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getByUserId(@PathVariable Integer userId) {
        List<NotificationResponse> data = notificationService.getNotificationsByUserId(userId).stream().map(this::toResponse).toList();
        return ResponseEntity.ok(data);
    }

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

