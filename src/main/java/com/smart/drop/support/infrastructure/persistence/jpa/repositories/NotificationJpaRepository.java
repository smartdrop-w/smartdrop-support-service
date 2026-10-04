package com.smart.drop.support.infrastructure.persistence.jpa.repositories;

import com.smart.drop.support.infrastructure.persistence.jpa.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Integer> {
    List<NotificationEntity> findByUserId(Integer userId);
}

