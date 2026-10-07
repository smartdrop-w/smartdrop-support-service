package com.smart.drop.support.interfaces.rest;

import com.smart.drop.support.application.services.AlertService;
import com.smart.drop.support.domain.model.entities.Alert;
import com.smart.drop.support.interfaces.rest.dto.CreateAlertRequest;
import com.smart.drop.support.interfaces.rest.dto.AlertResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/support/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    public ResponseEntity<AlertResponse> create(@RequestBody CreateAlertRequest request) {
        Alert created = alertService.createAlert(
                request.userId(),
                request.type(),
                request.title(),
                request.description()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    @GetMapping("/{alertId}")
    public ResponseEntity<AlertResponse> getById(@PathVariable Integer alertId) {
        return alertService.getById(alertId)
                .map(alert -> ResponseEntity.ok(toResponse(alert)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AlertResponse>> getByUserId(@PathVariable Integer userId) {
        List<AlertResponse> data = alertService.getByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(data);
    }

    @GetMapping
    public ResponseEntity<List<AlertResponse>> getAll() {
        List<AlertResponse> data = alertService.getAll()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(data);
    }

    @PutMapping("/{alertId}/resolve")
    public ResponseEntity<AlertResponse> resolve(@PathVariable Integer alertId) {
        Alert updated = alertService.resolveAlert(alertId);
        return ResponseEntity.ok(toResponse(updated));
    }

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
