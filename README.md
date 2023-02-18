# Test automation framework #
Test automation framework: Selenium of Maven, TestNG, Allure + Selenoid for remote parallel tests run

### Requirements ###
* Java 8+
* Maven 3+
* Will to run UI tests :)

### Information
* To see framework architecture visit: 
* Framework supports next browsers: Chrome, Edge

### How to run tests?
Use provided maven commands to run tests:
* To run smoke test suite you can use this maven command:
```mvn clean test -Denv=demo -Ddriver.name=chrome -Dsurefire.suiteXmlFiles=src/test/resources/smoke-test.xml```
* To run single test class:
```mvn clean test -Denv=demo -Ddriver.name=chrome -Dtest=YourAwesomeTestClassName```
* To run single test method:
```mvn clean test -Denv=demo -Ddriver.name=chrome -Dtest=YourAwesomeTestClassName#yourTestMethod```
