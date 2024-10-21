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

  Scenario: Attempting to Create a User Account with Missing Details
    Given I am on the registration page
    When I provide my personal details with missing email
    Then I receive an error message stating "Email is required"
    And my User account is not created

  Scenario: Adding Funds with Invalid Amount
    Given I am logged into my User account
    When I attempt to add -$20 to my prepaid account
    Then I receive an error message stating "Invalid amount"
    And my prepaid account balance remains unchanged

  Scenario: Attempting to Update User Information with Invalid Email
    Given I am logged into my User account
    When I update my email to "invalid-email-format"
    Then I receive an error message stating "Invalid email format"
    And my email remains unchanged

  Scenario: Deleting a Non-Existent User Account
    Given I am logged into my User account
    When I attempt to delete my account multiple times
    Then I receive a message stating "User account does not exist"