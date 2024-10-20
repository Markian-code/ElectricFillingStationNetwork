Feature: Manage Chargers

  Scenario: Add a charger with an ID
    Given I have no chargers
    When I add a charger with ID "12345"
    Then the charger with ID "12345" should be added to the system

  Scenario: Add another charger with a different ID
    Given I have an existing charger with ID "12345"
    When I add another charger with ID "67890"
    Then the charger with ID "67890" should be added to the system

  Scenario: Delete a charger with an ID
    Given I have a charger with ID "12345"
    When I delete the charger with ID "12345"
    Then the charger with ID "12345" should be removed from the system
