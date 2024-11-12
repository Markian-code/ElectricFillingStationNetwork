Feature: Location and Charger Management

  As owner
  I want to administer and manage the locations,
  So that clients can access and use the charging network.

  Background:
    Given the following locations exist:
      | name                     | address                     | PricePerACkwH | PricePerDCkwH | PriceACMinute | PriceDCMinute |
      | Wien Hauptbahnhof        | Hauptbahnhof 11, Wien       | 0.10          | 0.15          | 0.05          | 0.10          |
      | Graz Operngasse          | Operngasse 4, Graz          | 0.15          | 0.20          | 0.10          | 0.15          |
      | Salzburg Hauptbahnhof    | Hauptbahnhof 1, Salzburg    | 0.20          | 0.25          | 0.15          | 0.20          |
      | Wien Dresdnerstrasse     | Dresdnerstrasse 42, Wien    | 0.25          | 0.30          | 0.20          | 0.25          |
      | Linz Hauptplatz          | Hauptplatz 5, Linz          | 0.18          | 0.22          | 0.12          | 0.18          |
      | Innsbruck Bahnhofstrasse | Bahnhofstrasse 7, Innsbruck | 0.17          | 0.21          | 0.11          | 0.17          |
      | Klagenfurt Neuer Platz   | Neuer Platz 1, Klagenfurt   | 0.14          | 0.19          | 0.09          | 0.14          |
      | St. Poelten Rathausplatz | Rathausplatz 3, St. Poelten | 0.16          | 0.21          | 0.10          | 0.16          |

    And the following chargers exist at each location:
      | Location                 | ChargerID | Type | Status        |
      | Wien Hauptbahnhof        | CHARGER-1 | AC   | FREI          |
      | Wien Hauptbahnhof        | CHARGER-2 | DC   | BESETZT       |
      | Graz Operngasse          | CHARGER-3 | AC   | AUSSER_BETRIEB|
      | Graz Operngasse          | CHARGER-4 | DC   | FREI          |
      | Salzburg Hauptbahnhof    | CHARGER-5 | AC   | FREI          |
      | Salzburg Hauptbahnhof    | CHARGER-6 | DC   | BESETZT       |
      | Wien Dresdnerstrasse     | CHARGER-7 | AC   | AUSSER_BETRIEB|
      | Wien Dresdnerstrasse     | CHARGER-8 | DC   | FREI          |
      | Linz Hauptplatz          | CHARGER-9 | AC   | FREI          |
      | Linz Hauptplatz          | CHARGER-10| DC   | BESETZT       |
      | Innsbruck Bahnhofstrasse | CHARGER-11| AC   | FREI          |
      | Klagenfurt Neuer Platz   | CHARGER-12| DC   | AUSSER_BETRIEB|
      | St. Poelten Rathausplatz | CHARGER-13| AC   | FREI          |
      | St. Poelten Rathausplatz | CHARGER-14| DC   | BESETZT       |


  Scenario: Checking chargers at "Wien Hauptbahnhof"
    Then location "Wien Hauptbahnhof" has 2 charger
    And charger with ID "CHARGER-1" is of type "AC" and status "FREI"

  Scenario: Change the price of "Graz Operngasse"
    When owner sets the price for location "Graz Operngasse" PricePerACkwH to 0.18
    And sets for location "Graz Operngasse" PriceACMinute to 0.12
    Then the prices for location "Graz Operngasse" are:
      | PricePerACkwH | PricePerDCkwH | PriceACMinute | PriceDCMinute |
      | 0.18          | 0.20          | 0.12          | 0.15          |

  Scenario: Adding a charger to "Salzburg Hauptbahnhof"
    When owner adds the following chargers to "Salzburg Hauptbahnhof":
      | ID       | Type | Status |
      | CHARGER-15 | AC   | FREI   |
    Then location "Salzburg Hauptbahnhof" has 3 charger

  Scenario: Removing a charger from "Wien Dresdnerstrasse"
    When owner removes the charger with ID "CHARGER-7" from location "Wien Dresdnerstrasse"
    Then charger with ID "CHARGER-7" should not exist at location "Wien Dresdnerstrasse"
    Then location "Wien Dresdnerstrasse" has 1 charger

  Scenario: Changing the status of a charger at "Innsbruck Bahnhofstrasse"
    When owner sets the status of charger with ID "CHARGER-11" at location "Innsbruck Bahnhofstrasse" to "BESETZT"
    Then charger with ID "CHARGER-11" should have type "AC" and status "BESETZT"

  Scenario: Deleting a non-existent location
    When owner deletes the location with name "NonExistentLocation"
    Then an error message should be displayed saying "Location with name NonExistentLocation does not exist."

  Scenario: Adding a charger to a non-existent location
    When owner adds the charger with ID "CHARGER-99" to the location "NonExistentLocation"
    Then an error message should be displayed saying "Location with name NonExistentLocation does not exist."

  Scenario: Changing the status of a non-existent charger at "Linz Hauptplatz"
    When owner tries to change the status of the charger with ID "NonExistentCharger" at location "Linz Hauptplatz" to "BESETZT"
    Then an error message should be displayed saying "Charger with ID NonExistentCharger does not exist at this location."
