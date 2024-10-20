Feature:

  As a User,
  I want to manage my account effectively,
  so that I can ensure my information is up-to-date and maintain my prepaid funds.

  Scenario: Creating a New User Account
    Given I am on the registration page
    When I provide my personal details
    Then my User account is created
    And I receive a unique User ID

  Scenario: Adding Funds to Prepaid Account
    Given I am logged into my User account
    When I add $50 to my prepaid account
    Then my prepaid account balance increases by $50

  Scenario: Updating User Information
    Given I am logged into my User account
    When I update my personal details
    Then my User information is updated successfully
    And I receive a confirmation message

  Scenario: Deleting User Account
    Given I am logged into my User account
    When I choose to delete my account
    Then my User account is deleted
    And I receive a confirmation that my account has been removed

