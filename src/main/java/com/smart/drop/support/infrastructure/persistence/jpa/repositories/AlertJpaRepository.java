package com.smart.drop.support.infrastructure.persistence.jpa.repositories;

import com.smart.drop.support.infrastructure.persistence.jpa.entities.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertJpaRepository extends JpaRepository<AlertEntity, Integer> {

    List<AlertEntity> findByUserId(Integer userId);
}
