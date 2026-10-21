@support @incidents
Feature: US09 - Support Ticket Creation and Incident Dispatch
  As an authenticated SmartDrop user
  I want to submit support requests and report hardware or hydraulic incidents
  So that customer success and field technicians can track and resolve issues.

  Scenario: Creating an urgent support ticket for sensor malfunction
    When the client posts a ticket with subject "Sensor ESP32 desconectado" and priority "HIGH"
    Then the ticket should be assigned state "OPEN" with an SLA response window of 4 hours
