@User
Feature: User would like to get users
  Background:
    Given the following users exists in the library
      | code | description                 |
      | 1    | Twinkle twinkle little star |
      | 2    | Johnny Johnny Yes Papa      |

  Scenario: User should be able to get all users
    When user requests for all users
    Then the user gets the following users
      | code | description                 |
      | 1    | Twinkle twinkle little star |
      | 2    | Johnny Johnny Yes Papa      |

  Scenario: User should be able to get users by code
    When user requests for users by code "1"
    Then the user gets the following users
      | code | description                 |
      | 1    | Twinkle twinkle little star |

  Scenario: User should get an appropriate NOT FOUND message while trying get users by an invalid code
    When user requests for users by id "10000" that does not exists
    Then the user gets an exception "User with code 10000 does not exist"