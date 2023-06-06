Feature: Career Page

  Background:
    Given I open browser
    And I maximize browser
    And I open Tipico Careers page
    And I click acceptCookies

  Scenario: Fetching All Jobs
    Then I fetch jobs


