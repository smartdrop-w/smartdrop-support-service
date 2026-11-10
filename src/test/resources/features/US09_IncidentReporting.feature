@support @incidents
Feature: US09 - Support Ticket Submission and Technical Incident Reporting
  As an authenticated SmartDrop user or system administrator
  I want to submit support requests and record technical incidents
  So that customer care and maintenance staff can track, assign, and remediate technical faults.

  Background:
    Given the SmartDrop support service is operational
    And the user has ID 3001

  Scenario: Submitting an urgent support ticket for sensor malfunction
    When the client posts a support ticket with subject "Sensor ESP32 desconectado", priority "HIGH", and description "Perdida de senal de flujo"
    Then the ticket should be registered successfully
    And the ticket priority should be "HIGH"
    And the ticket status should be "OPEN"

  Scenario: Creating a technical incident report by system administrator
    When the administrator creates an incident report titled "Falla en valvula solenoide 3" with description "Bloqueo de apertura" and status "OPEN"
    Then the incident report should be created successfully
    And the incident report status should be "OPEN"
