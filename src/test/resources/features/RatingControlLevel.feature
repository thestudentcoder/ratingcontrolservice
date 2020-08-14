Feature: Rating Control

  Scenario: Rating control level decision to read book

    Given I am a customer with a rating control level of 12
    When I request to read an equal level book B1234
    Then I should get a decision granting access to read

  Scenario: Rating control level decision not to read book

    Given I am a customer with a rating control level of 12
    When I request to read a higher level book B1234
    Then I should get a decision to deny access