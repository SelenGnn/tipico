Feature: Career Page

  Background:
    Given I open browser
    And I maximize browser
    And I open "Tipico Careers" page
    And I accept Cookies

  Scenario: Fetching All Jobs
    When I fetch jobs

