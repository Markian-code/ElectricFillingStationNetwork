Feature: Charging Session Management

  As user
  I want to charge my vehicle,
  So that I can drive with it

  Background:
    Given the following locations with chargers exist:
      | name                     | address                     | PricePerACkwH | PricePerDCkwH | PriceACMinute | PriceDCMinute | Charger1    | Type-1 | Status-1      | Charger2    | Type-2 | Status-2  | Charger3    | Type-3 | Status-3      |
      | Wien Hauptbahnhof        | Hauptbahnhof 11, Wien       | 0.10          | 0.15          | 0.05          | 0.10          | CHARGER-1   | AC     | FREI          | CHARGER-2   | DC     | BESETZT   |             |        |               |
      | Graz Operngasse          | Operngasse 4, Graz          | 0.15          | 0.20          | 0.10          | 0.15          | CHARGER-3   | AC     | AUSSER_BETRIEB| CHARGER-4   | DC     | FREI      |             |        |               |
      | Salzburg Hauptbahnhof    | Hauptbahnhof 1, Salzburg    | 0.20          | 0.25          | 0.15          | 0.20          | CHARGER-5   | AC     | FREI          | CHARGER-6   | DC     | BESETZT   |             |        |               |

  Scenario: Charge vehicle

    Given user "Florian" with email "florian@example.com" and prepaid balance of 100 added at "2024-10-01 09:00" exists
    When user with email "florian@example.com" starts a charging session at "Wien Hauptbahnhof" with charger "CHARGER-1" consuming 15.0 kWh at "2024-10-01 10:00"
    And user with email "florian@example.com" stops the charging session at "2024-10-01 11:00"
    Then the total charging time for user with email "florian@example.com" is 60 minutes
    And the total cost for user with email "florian@example.com" is €4.50
    And the new balance for user with email "florian@example.com" should be 95.50

  Scenario: Trying to view cost for an unfinished session
    When user with email "florian@example.com" starts a charging session at "Salzburg Hauptbahnhof" with charger "CHARGER-5" consuming 10.0 kWh at "2024-10-12 12:00"
    And user with email "florian@example.com" wants to view the total cost
    Then an error message should be displayed "Charging session is still active. Cannot calculate total cost."
