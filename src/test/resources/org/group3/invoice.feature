Feature: User views invoice for charging sessions


  Background:
    Given the following locations exist:
      | name                | address               | PricePerACkwH | PricePerDCkwH | PriceACMinute | PriceDCMinute |
      | Wien Hauptbahnhof   | Hauptbahnhof 11, Wien | 0.10          | 0.15          | 0.05          | 0.10          |
      | Graz Operngasse     | Operngasse 4, Graz    | 0.18          | 0.20          | 0.12          | 0.15          |

    And the following chargers exist at each location:
      | Location             | ChargerID | Type | Status |
      | Wien Hauptbahnhof    | CHARGER-1 | AC   | FREI   |
      | Wien Hauptbahnhof    | CHARGER-2 | DC   | FREI   |
      | Graz Operngasse      | CHARGER-3 | AC   | FREI   |
      | Graz Operngasse      | CHARGER-4 | DC   | FREI   |


  Scenario: User views invoice after 5 charging sessions

    Given a user "Michael" with email "michael@example.com" and balance 100 has 5 charging sessions:
      | Location             | ChargerID | Start Time          | End Time            | Power Consumed |
      | Wien Hauptbahnhof    | CHARGER-1 | 2024-11-01T10:00:00 | 2024-11-01T10:30:00 | 10.0           |
      | Wien Hauptbahnhof    | CHARGER-2 | 2024-11-02T14:00:00 | 2024-11-02T14:40:00 | 15.0           |
      | Graz Operngasse      | CHARGER-3 | 2024-11-03T16:00:00 | 2024-11-03T16:45:00 | 5.0            |
      | Graz Operngasse      | CHARGER-4 | 2024-11-04T11:00:00 | 2024-11-04T11:50:00 | 8.0            |
      | Wien Hauptbahnhof    | CHARGER-1 | 2024-11-05T09:00:00 | 2024-11-05T09:20:00 | 12.0           |

    When the user views the invoice

    Then the invoice should display:
      | Location             | ChargerID | Start Time          | End Time            | Duration (minutes)  | Power Consumed | Total Cost (€) |Balance after charging (€) |
      | Wien Hauptbahnhof    | CHARGER-1 | 2024-11-01T10:00    | 2024-11-01T10:30    | 30                  | 10.0           | 2.50           |  97.5                     |
      | Wien Hauptbahnhof    | CHARGER-2 | 2024-11-02T14:00    | 2024-11-02T14:40    | 40                  | 15.0           | 6.25           |  91.25                    |
      | Graz Operngasse      | CHARGER-3 | 2024-11-03T16:00    | 2024-11-03T16:45    | 45                  | 5.0            | 6.30           |  84.95                    |
      | Graz Operngasse      | CHARGER-4 | 2024-11-04T11:00    | 2024-11-04T11:50    | 50                  | 8.0            | 9.10           |  75.85                    |
      | Wien Hauptbahnhof    | CHARGER-1 | 2024-11-05T09:00    | 2024-11-05T09:20    | 20                  | 12.0           | 2.20           |  73.65                    |

    And the total amount due should be "26.35"
