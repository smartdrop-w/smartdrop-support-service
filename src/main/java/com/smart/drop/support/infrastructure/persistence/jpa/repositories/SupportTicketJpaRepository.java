package com.smart.drop.support.infrastructure.persistence.jpa.repositories;

import com.smart.drop.support.infrastructure.persistence.jpa.entities.SupportTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportTicketJpaRepository extends JpaRepository<SupportTicketEntity, Integer> {
    List<SupportTicketEntity> findByUserId(Integer userId);
}

