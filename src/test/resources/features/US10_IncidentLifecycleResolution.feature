@support @lifecycle @resolution
Feature: US10 - Technical Incident Lifecycle Transitions and Resolution Stamping
  As a maintenance supervisor or support lead
  I want to transition incident reports across their lifecycle and stamp their final resolution
  So that mean time to resolution (MTTR) is tracked and incidents are properly closed.

  Background:
    Given the SmartDrop support service is operational
    And an active technical incident exists titled "Fuga de presion en tuberia matriz" with status "OPEN"

  Scenario: Transitioning incident status from OPEN to IN_PROGRESS
    When the technician updates the incident status to "IN_PROGRESS"
    Then the incident report status should become "IN_PROGRESS"

  Scenario: Resolving technical incident with automatic resolution timestamp
    When the technician marks the incident as resolved
    Then the incident report status should become "RESOLVED"
    And the incident report should contain a non-null resolution timestamp
