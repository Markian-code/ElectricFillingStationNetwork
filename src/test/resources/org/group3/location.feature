Feature: Admin Locations
  As owner
  i want do admin locations
  So that clients can use the electric filling station network

  Scenario: adding locations to the electric filling station network

    When owner adds the following location:
    |name                  |address                  |PricePerACkwH|PricePerDCkwH|PriceACMinute|PriceDCMinute|
    |Wien Hauptbahnhof     |Hauptbahnhof 11, Wien    |0.10         |0.10         |0.10         |0.10         |
    |Graz Operngasse       |Operngasse 4, Graz       |0.15         |0.15         |0.15         |0.15         |
    |Salzburg Hauptbahnhof |Hauptbahnhof 1, Salzburg |0.20         |0.20         |0.20         |0.20         |
    |Wien Dresdnerstrasse  |Dresdnerstrasse 42, Wien |0.25         |0.25         |0.25         |0.25         |
    Then the number of locations is 4
    And the location with name "Wien Hauptbahnhof" is located at "Hauptbahnhof 11, Wien"


  Scenario: Delete a location of the electric filling station network

    Given the following locations exists:
      |name                  |address                  |PricePerACkwH|PricePerDCkwH|PriceACMinute|PriceDCMinute|
      |Wien Hauptbahnhof     |Hauptbahnhof 11, Wien    |0.10         |0.10         |0.10         |0.10         |
      |Graz Operngasse       |Operngasse 4, Graz       |0.15         |0.15         |0.15         |0.15         |
      |Salzburg Hauptbahnhof |Hauptbahnhof 1, Salzburg |0.20         |0.20         |0.20         |0.20         |
      |Wien Dresdnerstrasse  |Dresdnerstrasse 42, Wien |0.25         |0.25         |0.25         |0.25         |
    When owner deletes the location with name "Salzburg Hauptbahnhof"
    Then the new number of locations is 3
    And the location with name "Wien Dresdnerstrasse" exists


  Scenario: Set the price for a location

    Given the following locations has this prices:
      |name                  |address                  |PricePerACkwH|PricePerDCkwH|PriceACMinute|PriceDCMinute|
      |Wien Hauptbahnhof     |Hauptbahnhof 11, Wien    |0.10         |0.10         |0.10         |0.10         |
    When owner set for location "Wien Hauptbahnhof" PricePerACkwH to 0.20
    And set for location "Wien Hauptbahnhof" PriceACMinute to 0.25
    Then the prices for location "Wien Hauptbahnhof" are:
    |PricePerACkwH|PricePerDCkwH|PriceACMinute|PriceDCMinute|
    |0.20         |0.10         |0.25         |0.10         |


  Scenario: Add chargers to the location
    Given following chargers exists:
      |ID       |Type   |Status        |
      |CHARGER-1|AC     |BESETZT       |
      |CHARGER-2|DC     |FREI          |
      |CHARGER-3|AC     |AUSSER_BETRIEB|
      |CHARGER-4|AC     |FREI          |
      |CHARGER-5|DC     |FREI          |
      |CHARGER-6|AC     |FREI          |
    Given location "Wien Dresdnerstrasse" has following chargers:
      |ID       |Type   |Status        |
      |CHARGER-1|AC     |BESETZT       |
      |CHARGER-2|DC     |FREI          |
      |CHARGER-3|AC     |AUSSER_BETRIEB|
    When owner adds the following chargers to "Wien Dresdnerstrasse":
      |ID       |Type   |Status|
      |CHARGER-4|AC     |FREI  |
      |CHARGER-5|DC     |FREI  |
      |CHARGER-6|AC     |FREI  |
    Then location "Wien Dresdnerstrasse" has 6 chargers
    And charger with ID "CHARGER-5" is of type "DC" and status "FREI"


  Scenario: Remove a charger from a location
    Given following chargers exists:
      | ID        | Type | Status        |
      | CHARGER-1 | AC   | FREI          |
      | CHARGER-2 | DC   | BESETZT       |
      | CHARGER-3 | AC   | AUSSER_BETRIEB|
    Given location "Wien Dresdnerstrasse" has following chargers:
      | ID        |
      | CHARGER-1 |
      | CHARGER-2 |
      | CHARGER-3 |
    When owner removes charger with ID "CHARGER-2" from location "Wien Dresdnerstrasse"
    Then charger with ID "CHARGER-2" should not exist at location "Wien Dresdnerstrasse"
    Then location "Wien Dresdnerstrasse" has 2 chargers


  Scenario: Change the status of a charger at a location
    Given the following chargers are created:
      | ID        | Type | Status        |
      | CHARGER-1 | AC   | FREI          |
      | CHARGER-2 | DC   | BESETZT       |
    And location "Wien Hauptbahnhof" has following chargers:
      | ID        |
      | CHARGER-1 |
      | CHARGER-2 |
    When owner sets status of charger with ID "CHARGER-1" at location "Wien Hauptbahnhof" to "BESETZT"
    Then charger with ID "CHARGER-1" should have type "AC" and status "BESETZT"
    And charger with ID "CHARGER-2" should still have status "BESETZT"


  Scenario: Deleting a non-existent location
    Given the following locations exist:
      | name              | address                  | PricePerACkwH | PricePerDCkwH | PriceACMinute | PriceDCMinute |
      | Wien Hauptbahnhof | Hauptbahnhof 11, Wien    | 0.30          | 0.45          | 0.10          | 0.15          |
    When the owner deletes the location with name "NonExistentLocation"
    Then an error message should be displayed saying "Location with name NonExistentLocation does not exist."

  Scenario: Adding a charger to a non-existent location
    Given the following locations exist:
      | name              | address                 | PricePerACkwH | PricePerDCkwH | PriceACMinute | PriceDCMinute |
      | Wien Hauptbahnhof | Hauptbahnhof 11, Wien   | 0.30          | 0.45          | 0.10          | 0.15          |
    When the owner adds the charger with ID "CHARGER-12" to the location "NonExistentLocation"
    Then an error message should be displayed saying "Location with name NonExistentLocation does not exist."


  Scenario: Changing the status of a non-existent charger
    Given following chargers exists:
      | ID        | Type | Status        |
      | CHARGER-1 | AC   | FREI          |
      | CHARGER-2 | DC   | BESETZT       |
      | CHARGER-3 | AC   | AUSSER_BETRIEB|
    Given location "Wien Dresdnerstrasse" has following chargers:
      | ID        |
      | CHARGER-1 |
      | CHARGER-2 |
      | CHARGER-3 |
    When the owner tries to change the status of the charger with ID "NonExistentCharger" at location "Wien Dresdnerstrasse" to "BESETZT"
    Then an error message should be displayed saying "Charger with ID NonExistentCharger does not exist at this location."