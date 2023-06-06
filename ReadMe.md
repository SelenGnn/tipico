## Tipico Project ##

This project includes fetching open jobs on https://www.tipico-careers.com/en/jobs/ and writing them to db test automation scenarios by using Selenium, Cucumber and BDD framework.

Test scenarios are described in Gherkin format in the feature files located here ./src\test\resources\features

## Installation ##

You need to have [Java 8 JDK]

To run the tests locally with Chrome, install ChromeDriver (https://www.google.com/chrome/thank-you.html?statcb=1&installdataindex=empty&defaultbrowser=0)

To install all dependencies, build


## How to run the tests ##

To run test scenarios press CTRL+F10 or write "gradle cucumber" command to Execute Gradle Task
Tests will run on Chrome. You can run the tests on different browsers by adding the definition of your desired browser to OpenBrowser function in Steps Java Class.


## Dependencies
* *[selenium](https://www.selenium.dev/)*
* *[webdrivermanager](https://github.com/bonigarcia/webdrivermanager)*
* *[oracle connection](https://www.oracle.com/tr/database/technologies/oracle-database-software-downloads.html)*

