package com.smart.drop.support.domain.model.repositories;

import com.smart.drop.support.domain.model.entities.Alert;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for alerts.
 */
public interface AlertRepository {

    Alert save(Alert alert);

    Optional<Alert> findById(Integer alertId);

    List<Alert> findByUserId(Integer userId);

    List<Alert> findAll();

    void deleteById(Integer alertId);
}
