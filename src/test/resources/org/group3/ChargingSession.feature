Feature:
  As an User
  I want to charge my vehicle
  So that I can use my car

  Scenario: Start charging session
    When I have a user account
    And I plugged in my car at
    | Location name | charger | Type | price DC | price per minute | starttime | duration |
    | Wien Hbf      |  1      | DC   | 0.12     | 0.24             | 12:42     | 60       |
    Then a charging session is created

  Scenario: Find charging duration
    When I plugged in my car at
      | Location name | charger | Type | price DC | price per minute | starttime | endtime |
      | Wien Hbf      |  1      | DC   | 0.12     | 0.24             | 12:42     | 13:42   |
      | Wien Westbf   |  3      | AC   | 0.17     | 0.26             | 17:00     | 17:33   |
    Then the charging duration should be {int}
      | time |
      | 60   |
      | 33   |