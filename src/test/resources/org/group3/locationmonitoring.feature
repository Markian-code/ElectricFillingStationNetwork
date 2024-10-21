Feature: Location Monitoring

  As an owner or user
  I want to monitor all locations and their chargers
  So that I can see details about locations and the status of chargers

  Scenario: Display all locations with chargers
    Given the following locations exist:
      | name              | address                     | PricePerACkwH | PricePerDCkwH | PriceACMinute | PriceDCMinute |
      | Wien Hauptbahnhof | Hauptbahnhof 11, Wien       | 0.30          | 0.45          | 0.10          | 0.15          |
      | Graz Operngasse   | Operngasse 9, Graz          | 0.35          | 0.50          | 0.12          | 0.17          |

    And the following chargers exist:
      | ID        | Type  | Status     |
      | CHARGER-1 | AC    | FREI       |
      | CHARGER-2 | DC    | BESETZT    |
      | CHARGER-3 | AC    | FREI       |

    And chargers are assigned to locations:
      | name               | chargerID |
      | Wien Hauptbahnhof  | CHARGER-1 |
      | Wien Hauptbahnhof  | CHARGER-2 |
      | Graz Operngasse    | CHARGER-3 |

    When I monitor all locations
    Then the system displays all locations with chargers
    And the output should contain the following:
      | name              | Charger ID | Type | Status  |
      | Wien Hauptbahnhof | CHARGER-1  | AC   | FREI    |
      | Wien Hauptbahnhof | CHARGER-2  | DC   | BESETZT |
      | Graz Operngasse   | CHARGER-3  | AC   | FREI    |
