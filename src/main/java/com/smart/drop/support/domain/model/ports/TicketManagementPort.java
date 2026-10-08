package com.smart.drop.support.domain.model.ports;

/**
 * External ticket management port.
 */
public interface TicketManagementPort {

    TicketResult createTicket(String subject, String description, String priority);

    TicketResult updateTicketStatus(String externalTicketId, String status);

    record TicketResult(boolean success, String externalTicketId, String providerMessage) {
    }
}

