package com.smart.drop.support.bdd.steps;

import com.smart.drop.support.application.services.SupportService;
import com.smart.drop.support.domain.model.entities.IncidentReport;
import com.smart.drop.support.domain.model.entities.SupportTicket;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class SupportBddSteps {

    @Autowired
    private SupportService supportService;

    private Integer userId = 3001;
    private SupportTicket currentTicket;
    private IncidentReport currentIncident;

    @Given("the SmartDrop support service is operational")
    public void theSmartDropSupportServiceIsOperational() {
        assertThat(supportService).isNotNull();
    }

    @Given("the user has ID {int}")
    public void theUserHasId(int id) {
        this.userId = id;
    }

    @When("the client posts a support ticket with subject {string}, priority {string}, and description {string}")
    public void theClientPostsASupportTicket(String subject, String priority, String description) {
        currentTicket = supportService.createSupportTicket(userId, subject, priority, description);
    }

    @Then("the ticket should be registered successfully")
    public void theTicketShouldBeRegisteredSuccessfully() {
        assertThat(currentTicket).isNotNull();
        assertThat(currentTicket.ticketId()).isNotNull();
    }

    @And("the ticket priority should be {string}")
    public void theTicketPriorityShouldBe(String expectedPriority) {
        assertThat(currentTicket.priority()).isEqualTo(expectedPriority);
    }

    @And("the ticket status should be {string}")
    public void theTicketStatusShouldBe(String expectedStatus) {
        assertThat(currentTicket.status()).isEqualTo(expectedStatus);
    }

    @When("the administrator creates an incident report titled {string} with description {string} and status {string}")
    public void theAdministratorCreatesAnIncidentReport(String title, String description, String status) {
        currentIncident = supportService.createIncidentReport(userId, title, description, status);
    }

    @Then("the incident report should be created successfully")
    public void theIncidentReportShouldBeCreatedSuccessfully() {
        assertThat(currentIncident).isNotNull();
        assertThat(currentIncident.incidentId()).isNotNull();
    }

    @And("the incident report status should be {string}")
    public void theIncidentReportStatusShouldBe(String expectedStatus) {
        assertThat(currentIncident.status()).isEqualTo(expectedStatus);
    }

    @Given("an active technical incident exists titled {string} with status {string}")
    public void anActiveTechnicalIncidentExistsTitledWithStatus(String title, String status) {
        currentIncident = supportService.createIncidentReport(userId, title, "Initial description for incident", status);
    }

    @When("the technician updates the incident status to {string}")
    public void theTechnicianUpdatesTheIncidentStatusTo(String newStatus) {
        currentIncident = supportService.updateIncidentReportStatus(currentIncident.incidentId(), newStatus);
    }

    @Then("the incident report status should become {string}")
    public void theIncidentReportStatusShouldBecome(String expectedStatus) {
        assertThat(currentIncident.status()).isEqualTo(expectedStatus);
    }

    @When("the technician marks the incident as resolved")
    public void theTechnicianMarksTheIncidentAsResolved() {
        currentIncident = supportService.resolveIncidentReport(currentIncident.incidentId());
    }

    @And("the incident report should contain a non-null resolution timestamp")
    public void theIncidentReportShouldContainANonNullResolutionTimestamp() {
        assertThat(currentIncident.resolvedAt()).isNotNull();
    }
}
