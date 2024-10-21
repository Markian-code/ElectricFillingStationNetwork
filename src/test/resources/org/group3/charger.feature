Feature: Manage Charger
  As owner
  i want do manage chargers
  So that chargers can be added to locations

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
      | CHARGER-1 | AC   | FREI          |
      | CHARGER-2 | DC   | BESETZT       |
      | CHARGER-3 | AC   | AUSSER_BETRIEB|
      | CHARGER-4 | DC   | FREI          |
      | CHARGER-5 | DC   | BESETZT       |
    When the charger with ID "CHARGER-4" is deleted
    Then charger with ID "CHARGER-4" should not longer exist
    And number of charges is 4.

  Scenario: Update the status of a charger
    Given the following chargers are created:
      | ID        | Type | Status        |
      | CHARGER-1 | AC   | FREI          |
      | CHARGER-2 | DC   | BESETZT       |
      | CHARGER-3 | AC   | AUSSER_BETRIEB|
    When the status of charger with ID "CHARGER-1" is updated to "BESETZT"
    Then charger with ID "CHARGER-1" should have type "AC" and status "BESETZT"
    And charger with ID "CHARGER-2" should still have status "BESETZT"
    And charger with ID "CHARGER-3" should still have status "AUSSER_BETRIEB"