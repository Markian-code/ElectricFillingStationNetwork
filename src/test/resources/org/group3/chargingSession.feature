Feature: Charging Session

  As owner
  the system should monitor all charging session of clients
  so that i can bill them correctly

  Scenario: User starts and finishes a charging session
    Given the following location exists:
      | name              | address                   | pricePerACkwH | pricePerDCkwH | priceACMinute | priceDCMinute |
      | Wien Hauptbahnhof | Hauptbahnhof 11, Wien      | 0.25          | 0.30          | 0.10          | 0.12         |
    And the following charger exists:
      | ID        | Type | Status  |
      | CHARGER-1 | AC   | FREI    |
    When user "john_doe" starts a charging session at "Wien Hauptbahnhof" using charger "CHARGER-1" with 25.0 kWh
    And the session lasts 60 minutes
    Then the total cost for the session should be €12.25
    And the session duration should be 60 minutes
    And the session for user "john_doe" should exist in the list of sessions
