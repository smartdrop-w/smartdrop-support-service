package com.smart.drop.support.infrastructure.external.zendesk;

import com.smart.drop.support.domain.model.ports.TicketManagementPort;

public class ZendeskAdapter implements TicketManagementPort {

    @Override
    public TicketResult createTicket(String subject, String description, String priority) {
        return new TicketResult(true, "ZENDESK-" + System.currentTimeMillis(), "Zendesk mock ticket created");
    }

    @Override
    public TicketResult updateTicketStatus(String externalTicketId, String status) {
        return new TicketResult(true, externalTicketId, "Zendesk mock ticket updated to " + status);
    }
}


