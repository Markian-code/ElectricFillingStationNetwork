Feature: Invoices
  As an User
  I want to view invoices
  So that I know how much I paid

  Scenario: Display Invoices
    Given for user exist the following charging session:
      | ID        | Type | Status        |
      | CHARGER-1 | AC   | FREI          |
      | CHARGER-2 | DC   | BESETZT       |
      | CHARGER-3 | AC   | AUSSER_BETRIEB|
    When the user wants to display the invoices
    Then a list with all invoices should be displayed

  Scenario: View balance history
    When the user wants to see his balance history
    Then a list with all top-ups and their dates should be displayed

    #################################
    ########## Error Cases ##########
    #################################
  Scenario: Assure presence of invoices
    Given there are no invoices
    When the user wants to display the invoices
    Then an error should be displayed

  Scenario: No available balance history
    Given no funds have been added
    When the user wants to display the balance history
    Then an error should be displayed