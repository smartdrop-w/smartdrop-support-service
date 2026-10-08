package com.smart.drop.support.application.services;

import com.smart.drop.support.domain.model.entities.Alert;
import com.smart.drop.support.domain.model.repositories.AlertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public Alert createAlert(Integer userId, String type, String title, String description) {
        Alert alert = Alert.create(userId, type, title, description);
        return alertRepository.save(alert);
    }

    @Transactional(readOnly = true)
    public Optional<Alert> getById(Integer alertId) {
        return alertRepository.findById(alertId);
    }

    @Transactional(readOnly = true)
    public List<Alert> getByUserId(Integer userId) {
        return alertRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Alert> getAll() {
        return alertRepository.findAll();
    }

    public Alert resolveAlert(Integer alertId) {
        Alert current = alertRepository.findById(alertId)
                .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + alertId));

        Alert resolved = current.resolve();
        return alertRepository.save(resolved);
    }

    public void deleteById(Integer alertId) {
        alertRepository.deleteById(alertId);
    }
}
