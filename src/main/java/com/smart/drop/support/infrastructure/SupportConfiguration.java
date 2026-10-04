package com.smart.drop.support.infrastructure;

import com.smart.drop.support.domain.model.ports.SmsServicePort;
import com.smart.drop.support.domain.model.ports.TicketManagementPort;
import com.smart.drop.support.domain.model.repositories.NotificationRepository;
import com.smart.drop.support.infrastructure.external.twilio.TwilioAdapter;
import com.smart.drop.support.infrastructure.external.zendesk.ZendeskAdapter;
import com.smart.drop.support.infrastructure.persistence.jpa.adapters.SupportRepositoryAdapter;
import com.smart.drop.support.infrastructure.persistence.jpa.repositories.IncidentReportJpaRepository;
import com.smart.drop.support.infrastructure.persistence.jpa.repositories.NotificationJpaRepository;
import com.smart.drop.support.infrastructure.persistence.jpa.repositories.SupportTicketJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupportConfiguration {

    @Bean
    public NotificationRepository notificationRepository(NotificationJpaRepository notificationJpaRepository,
                                                         SupportTicketJpaRepository supportTicketJpaRepository,
                                                         IncidentReportJpaRepository incidentReportJpaRepository) {
        return new SupportRepositoryAdapter(notificationJpaRepository, supportTicketJpaRepository, incidentReportJpaRepository);
    }

    @Bean
    public SmsServicePort smsServicePort() {
        return new TwilioAdapter();
    }

    @Bean
    public TicketManagementPort ticketManagementPort() {
        return new ZendeskAdapter();
    }
}

