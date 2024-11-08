Feature: Display locations

  As owner or user
  I want to view information's on location, prices and chargers in the electric filling station network
  So that I can find a charging location easily


Background:

  Given the following locations with chargers exist:
    | name                     | address                     | PricePerACkwH | PricePerDCkwH | PriceACMinute | PriceDCMinute | Charger1    | Type-1 | Status-1      | Charger2    | Type-2 | Status-2  | Charger3    | Type-3 | Status-3      |
    | Wien Hauptbahnhof        | Hauptbahnhof 11, Wien       | 0.10          | 0.15          | 0.05          | 0.10          | CHARGER-1   | AC     | FREI          | CHARGER-2   | DC     | BESETZT   |             |        |               |
    | Graz Operngasse          | Operngasse 4, Graz          | 0.15          | 0.20          | 0.10          | 0.15          | CHARGER-3   | AC     | AUSSER_BETRIEB| CHARGER-4   | DC     | FREI      |             |        |               |
    | Salzburg Hauptbahnhof    | Hauptbahnhof 1, Salzburg    | 0.20          | 0.25          | 0.15          | 0.20          | CHARGER-5   | AC     | FREI          | CHARGER-6   | DC     | BESETZT   |             |        |               |
    | Wien Dresdnerstrasse     | Dresdnerstrasse 42, Wien    | 0.25          | 0.30          | 0.20          | 0.25          | CHARGER-7   | AC     | AUSSER_BETRIEB| CHARGER-8   | DC     | FREI      |             |        |               |
    | Linz Hauptplatz          | Hauptplatz 5, Linz          | 0.18          | 0.22          | 0.12          | 0.18          | CHARGER-9   | AC     | FREI          | CHARGER-10  | DC     | BESETZT   |             |        |               |
    | Innsbruck Bahnhofstrasse | Bahnhofstrasse 7, Innsbruck | 0.17          | 0.21          | 0.11          | 0.17          | CHARGER-11  | AC     | FREI          |             |        |           |             |        |               |
    | Klagenfurt Neuer Platz   | Neuer Platz 1, Klagenfurt   | 0.14          | 0.19          | 0.09          | 0.14          | CHARGER-12  | DC     | AUSSER_BETRIEB|             |        |           |             |        |               |
    | St. Poelten Rathausplatz | Rathausplatz 3, St. Poelten | 0.16          | 0.21          | 0.10          | 0.16          | CHARGER-13  | AC     | FREI          | CHARGER-14  | DC     | BESETZT   |             |        |               |


  Scenario: Display locations and all chargers

    When I display all locations with chargers
    Then I should see the following locations with their chargers:
      | Location                 | Address                     | AC price per kWh | DC price per kWh | AC price per Minute | DC price per Minute | Charger ID | Type | Status        |
      | Wien Hauptbahnhof        | Hauptbahnhof 11, Wien       | 0.10              | 0.15             | 0.05                | 0.10                 | CHARGER-1  | AC   | FREI          |
      | Wien Hauptbahnhof        | Hauptbahnhof 11, Wien       | 0.1              | 0.15             | 0.05                | 0.1                 | CHARGER-2  | DC   | BESETZT       |
      | Graz Operngasse          | Operngasse 4, Graz          | 0.15             | 0.2              | 0.1                 | 0.15                | CHARGER-3  | AC   | AUSSER_BETRIEB|
      | Graz Operngasse          | Operngasse 4, Graz          | 0.15             | 0.2              | 0.1                 | 0.15                | CHARGER-4  | DC   | FREI          |
      | Salzburg Hauptbahnhof    | Hauptbahnhof 1, Salzburg    | 0.2              | 0.25             | 0.15                | 0.2                 | CHARGER-5  | AC   | FREI          |
      | Salzburg Hauptbahnhof    | Hauptbahnhof 1, Salzburg    | 0.2              | 0.25             | 0.15                | 0.2                 | CHARGER-6  | DC   | BESETZT       |
      | Wien Dresdnerstrasse     | Dresdnerstrasse 42, Wien    | 0.25             | 0.3              | 0.2                 | 0.25                | CHARGER-7  | AC   | AUSSER_BETRIEB|
      | Wien Dresdnerstrasse     | Dresdnerstrasse 42, Wien    | 0.25             | 0.3              | 0.2                 | 0.25                | CHARGER-8  | DC   | FREI          |
      | Linz Hauptplatz          | Hauptplatz 5, Linz          | 0.18             | 0.22             | 0.12                | 0.18                | CHARGER-9  | AC   | FREI          |
      | Linz Hauptplatz          | Hauptplatz 5, Linz          | 0.18             | 0.22             | 0.12                | 0.18                | CHARGER-10 | DC   | BESETZT       |
      | Innsbruck Bahnhofstrasse | Bahnhofstrasse 7, Innsbruck | 0.17             | 0.21             | 0.11                | 0.17                | CHARGER-11 | AC   | FREI          |
      | Klagenfurt Neuer Platz   | Neuer Platz 1, Klagenfurt   | 0.14             | 0.19             | 0.09                | 0.14                | CHARGER-12 | DC   | AUSSER_BETRIEB|
      | St. Poelten Rathausplatz | Rathausplatz 3, St. Poelten | 0.16             | 0.21             | 0.1                 | 0.16                | CHARGER-13 | AC   | FREI          |
      | St. Poelten Rathausplatz | Rathausplatz 3, St. Poelten | 0.16             | 0.21             | 0.1                 | 0.16                | CHARGER-14 | DC   | BESETZT       |

  Scenario: Display locations with available chargers

    When I display available chargers at location "Wien Hauptbahnhof"
    Then I should see the following locations with their chargers:
      | Location                 | Address                     | AC price per kWh | DC price per kWh | AC price per Minute | DC price per Minute | Charger ID | Type | Status        |
      | Wien Hauptbahnhof        | Hauptbahnhof 11, Wien       | 0.10             | 0.15             | 0.05                | 0.10                | CHARGER-1  | AC   | FREI          |

