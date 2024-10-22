Feature: Charger Management

  As the owner of the electric filling station network,
  I want to manage the chargers within the network.
  So that I can add, update, and remove chargers at specific locations efficiently.

  Scenario: Create charges for the electric filling station network
    When owner creates the following chargers:
      | ID        | Type | Status        |
      | CHARGER-1 | AC   | FREI          |
      | CHARGER-2 | DC   | BESETZT       |
      | CHARGER-3 | AC   | AUSSER_BETRIEB|
    Then charger with ID "CHARGER-1" should exist
    And charger with ID "CHARGER-1" should have type "AC" and status "FREI"
    And charger with ID "CHARGER-2" should have type "DC" and status "BESETZT"
    And charger with ID "CHARGER-3" should have type "AC" and status "AUSSER_BETRIEB"

  Scenario: Delete a charger
    Given the following chargers are created:
      | ID        | Type | Status        |
      | CHARGER-4 | AC   | FREI          |
      | CHARGER-5 | DC   | BESETZT       |
      | CHARGER-6 | AC   | AUSSER_BETRIEB|
      | CHARGER-7 | DC   | FREI          |
      | CHARGER-8 | DC   | BESETZT       |
    When the charger with ID "CHARGER-4" is deleted
    Then charger with ID "CHARGER-4" should not longer exist
    And number of charges is 4.

  Scenario: Update the status of a charger
    Given the following chargers are created:
      | ID         | Type | Status        |
      | CHARGER-9  | AC   | FREI          |
      | CHARGER-10 | DC   | BESETZT       |
      | CHARGER-11 | AC   | AUSSER_BETRIEB|
    When the status of charger with ID "CHARGER-9" is updated to "BESETZT"
    Then charger with ID "CHARGER-9" should have type "AC" and status "BESETZT"
    And charger with ID "CHARGER-10" should still have status "BESETZT"
    And charger with ID "CHARGER-11" should still have status "AUSSER_BETRIEB"

  Scenario: Attempt to add a charger with a duplicate ID
    Given a charger with ID "CHARGER-1" already exists in the system
    When I attempt to add another charger with ID "CHARGER-1"
    Then an error should be reported saying "A charger with ID CHARGER-1 already exists"

  Scenario: Attempt to delete a non-existent charger
    Given there are no chargers in the system
    When I attempt to delete the charger with ID "CHARGER-8"
    Then an error should be reported saying "Charger with ID CHARGER-8 does not exist"

