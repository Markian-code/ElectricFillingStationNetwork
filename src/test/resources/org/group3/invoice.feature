Feature: Invoice
  As owner
  the system should monitor all charging session of clients
  so that i can bill them correctly

  Scenario: User requests an invoice sorted by start time
    Given the following location exists:
      | name            | address                    | pricePerACkwH | pricePerDCkwH | priceACMinute | priceDCMinute |
      | Salzburg Hbf    | Hauptbahnhof 4, Salzburg   | 0.30          | 0.40          | 0.10          | 0.15          |
      | Innsbruck Hbf   | Hauptbahnhof 7, Innsbruck  | 0.25          | 0.35          | 0.12          | 0.18          |
    And the following charger exists:
      | ID        | Type  | Status   |
      | CHARGER-1 | AC    | FREI     |
      | CHARGER-2 | DC    | FREI     |
    And user "john_doe" starts a charging session at "2024-10-21-10-15" at "Salzburg Hbf" using charger "CHARGER-1" with 15.0 kWh
    And the session lasts 45 minutes
    And user "john_doe" starts a charging session at "2024-10-22-09-00" at "Innsbruck Hbf" using charger "CHARGER-2" with 10.0 kWh
    And the session lasts 30 minutes
    Then user "john_doe" requests an invoice
    Then the invoice for user "john_doe" should list the following sessions in order:
      | location         | chargerId     | chargerType | startTime           |
      | Salzburg Hbf     | CHARGER-1     | AC          | 2024-10-21T10:15    |
      | Innsbruck Hbf    | CHARGER-2     | DC          | 2024-10-22T09:00    |
    Then the total invoice amount for user "john_doe" should be €17.90