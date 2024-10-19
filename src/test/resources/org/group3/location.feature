Feature:
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





  Scenario: Add a Charger to the location