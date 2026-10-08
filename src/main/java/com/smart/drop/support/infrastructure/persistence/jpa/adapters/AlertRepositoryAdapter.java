package com.smart.drop.support.infrastructure.persistence.jpa.adapters;

import com.smart.drop.support.domain.model.entities.Alert;
import com.smart.drop.support.domain.model.repositories.AlertRepository;
import com.smart.drop.support.infrastructure.persistence.jpa.entities.AlertEntity;
import com.smart.drop.support.infrastructure.persistence.jpa.repositories.AlertJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AlertRepositoryAdapter implements AlertRepository {

    private final AlertJpaRepository alertJpaRepository;

    public AlertRepositoryAdapter(AlertJpaRepository alertJpaRepository) {
        this.alertJpaRepository = alertJpaRepository;
    }

    @Override
    public Alert save(Alert alert) {
        AlertEntity entity = toEntity(alert);
        AlertEntity saved = alertJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Alert> findById(Integer alertId) {
        return alertJpaRepository.findById(alertId).map(this::toDomain);
    }

    @Override
    public List<Alert> findByUserId(Integer userId) {
        return alertJpaRepository.findByUserId(userId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Alert> findAll() {
        return alertJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(Integer alertId) {
        alertJpaRepository.deleteById(alertId);
    }

    private AlertEntity toEntity(Alert alert) {
        AlertEntity entity = new AlertEntity();
        entity.setAlertId(alert.alertId());
        entity.setUserId(alert.userId());
        entity.setType(alert.type());
        entity.setTitle(alert.title());
        entity.setDescription(alert.description());
        entity.setResolved(alert.resolved());
        entity.setCreatedAt(alert.createdAt());
        return entity;
    }

    private Alert toDomain(AlertEntity entity) {
        return new Alert(
                entity.getAlertId(),
                entity.getUserId(),
                entity.getType(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getResolved(),
                entity.getCreatedAt()
        );
    }
}
