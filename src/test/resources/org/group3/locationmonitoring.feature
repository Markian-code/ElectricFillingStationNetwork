Feature: Location Monitoring

  As an owner or user
  I want to monitor all locations
  So that I can see details about locations and the status of chargers

  Scenario: Display all locations with chargers
    Given the following locations exist:
      | name              | address                     | PricePerACkwH | PricePerDCkwH | PriceACMinute | PriceDCMinute |
      | Wien Hauptbahnhof | Hauptbahnhof 11, Wien       | 0.30          | 0.45          | 0.10          | 0.15          |
      | Graz Operngasse   | Operngasse 9, Graz          | 0.35          | 0.50          | 0.12          | 0.17          |

    And the following chargers exist:
      | ID        | Type  | Status     |
      | CHARGER-91 | AC    | FREI       |
      | CHARGER-92 | DC    | BESETZT    |
      | CHARGER-93 | AC    | FREI       |

    And chargers are assigned to locations:
      | name               | chargerID |
      | Wien Hauptbahnhof  | CHARGER-91 |
      | Wien Hauptbahnhof  | CHARGER-92 |
      | Graz Operngasse    | CHARGER-93 |

    When I monitor all locations
    Then the system displays all locations with chargers
    And the output should contain the following:
      | name              | Charger ID | Type | Status  |
      | Wien Hauptbahnhof | CHARGER-91  | AC   | FREI    |
      | Wien Hauptbahnhof | CHARGER-92  | DC   | BESETZT |
      | Graz Operngasse   | CHARGER-93  | AC   | FREI    |
