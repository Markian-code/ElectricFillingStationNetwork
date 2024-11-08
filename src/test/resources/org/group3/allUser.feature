Feature: User Management

  Background:
    Given the following users exist:
      | name     | email                 | prepaidBalance |
      | Alice    | alice@example.com     | 50.0           |
      | Bob      | bob@example.com       | 30.0           |
      | Charlie  | charlie@example.com   | 70.0           |
      | Diana    | diana@example.com     | 90.0           |
      | Eve      | eve@example.com       | 20.0           |

  Scenario: Add a new user
    When I add a user with name "Frank" and email "frank@example.com"
    Then the user "Frank" with mail "frank@example.com" should be in the system
    And the total number of users should be 6

  Scenario: Remove an user
    When I remove the user Charlie with email "charlie@example.com"
    Then the user with email "charlie@example.com" should not be in the system
    And the total number of users should be 5

  Scenario: Update user details
    When I update user "Bob" and email "bob@example.com" to have new name "Bobby" and email "bobby@example.com"
    Then the user should now have name "Bobby" and their email should be "bobby@example.com"

  Scenario: Add funds to a user
    When I add 50.0 to my user account with email "alice@example.com"
    Then the user "Alice" with email "alice@example.com" should have a prepaid balance of 100.0
