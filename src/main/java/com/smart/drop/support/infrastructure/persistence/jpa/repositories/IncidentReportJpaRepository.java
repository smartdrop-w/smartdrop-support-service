package com.smart.drop.support.infrastructure.persistence.jpa.repositories;

import com.smart.drop.support.infrastructure.persistence.jpa.entities.IncidentReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentReportJpaRepository extends JpaRepository<IncidentReportEntity, Integer> {
    List<IncidentReportEntity> findByAdminId(Integer adminId);
}

