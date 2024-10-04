# Personal Project Test Automation

**Disclaimer**: This project is an independent test automation initiative for educational and personal purposes. It is not affiliated with or endorsed by the official Firefly III project or its developers. All names, logos, and trademarks mentioned are the property of their respective owners. The purpose of this project is solely to demonstrate test automation techniques, and it is not intended for use in any production environment.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Test Cases Documentation](#test-cases-documentation)
- [Test Automation Scope](#test-automation-scope)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Logging](#logging)
- [Test Data Cleanup](#test-data-cleanup)
- [Setup and Installation](#setup-and-installation)
- [Running Tests](#running-tests)
- [Video Demonstrations](#video-demonstrations)
- [License](#license)

---

## Project Overview

This repository contains automated test scripts for the [Firefly III](https://github.com/firefly-iii/firefly-iii) personal finance manager. It includes various test cases to validate the functionality of the application, primarily focusing on critical features such as login, logout, register account, bills, budgets, and user actions. The project uses Selenium WebDriver for browser-based automation and TestNG as the testing framework.

---

## Test Cases Documentation

All test cases, are documented in a Google Spreadsheet, which can be accessed via the following link:
[Personal Project Test Cases](https://docs.google.com/spreadsheets/d/1n2DGq9qy5hMVERLKnGjUG3tyZ4ihPxAEQ5bjs6UqRgQ/edit?usp=sharing)

---

## Test Automation Scope

The tests cover the following key scenarios:
1. **Authentication:** Login, Logout, Forgot Password.
2. **Bills and Budgets:** Creating, updating, and validating recurring bills and budgets.
3. **Cross-browser Compatibility:** Tests executed on multiple browsers to ensure consistent behavior.
4. **Error Handling and Validation:** Test cases for invalid data entries and error messages.

---

## Technology Stack

The automation framework leverages the following tools and libraries:

* **Java 17:** The core programming language used for writing the test automation scripts.
* **Maven:** Dependency management and project build automation.
* **TestNG:** Testing framework to structure, group, and execute tests.
* **Allure:**  For generating test reports with rich, detailed visual feedback on test results.
* **Selenium WebDriver:** For interacting with and automating browser actions during tests.
* **GitHub Actions:** For continuous integration (CI), automating test execution on pushes and pull requests.

---

## Project Structure

The project follows a modular structure for organizing test cases, utilities, and configuration files. Below is an overview of the main directories and files:

````bash
Personal_Project_Test_Automation/
│
├── .github/                 # GitHub workflows and configurations
├── src/
│   ├── test/
│       ├── java/
│       │   ├── page/
│       │   │   ├── base/
│       │   │   ├── bills/
│       │   │   ├── budgets/
│       │   │   └── login/
│       │   ├── test/
│       │   └── utils/
│       │       ├── log/
│       │       └── run/
│       └── resources/
├── .db.env                  # Environment variables for DB
├── .env                     # Environment variables for the project
├── .gitignore               # Git ignore file
├── docker-compose.yml       # Docker Compose configuration for setting up the environment
├── LICENSE                  # License information
├── pom.xml                  # Maven project object model configuration
└── README.md                # Project README file
````

### Project Structure Description

**Page Object Classes** (`page`):

* This directory contains all the Page Object Model (POM) classes for different sections of the Firefly III application.
* The `base` package holds the common base classes shared across other pages.
* Separate packages exist for bills, budgets, and login functionalities.

**Test Classes** (`test`):

* Contains the core test classes for each feature like Bills, Budgets, Logging in/out, and Account Registration.

**Utility Classes** (`utils`):

* Includes utility classes for logging (`log`), test configuration and setup (`run`), and other test helpers.

**Resources** (`resources`):

* The `allure.properties` and `log4j2.xml` files provide configuration for Allure reporting and logging using Log4j2.
* The `local.properties.TEMPLATE` file contains a template environment-specific configuration details.

---

## Logging

The project uses Log4j2 for logging to provide better insights into the behavior of the application. Log4j2 allows configurable logging at different levels (e.g., `INFO`, `ERROR`, `FATAL` etc.), making it easier to trace issues and monitor the application's performance.

To view the logs while the application is running, you can check the logs in the console or in folder `log/` as per the configuration in `log4j2.xml`.

---

## Test Data Cleanup

The project includes automated tests that interact with the Firefly III application. To ensure that the environment is reset after each test run, the `ClearData` class is utilized to clear specific resources, such as bills, budgets, and users, from the database.

**How Data is Cleared After Each Test**

The `ClearData` class provides methods for making HTTP requests to the Firefly III API to remove test data created during the tests. This ensures that the environment remains clean and does not retain any leftover data from previous test runs.

---

## Setup and Installation

### Prerequisites

To run the tests locally, ensure you have the following installed:

* Java Development Kit (JDK 11 or higher)
* Maven: To manage dependencies and run tests.
* Google Chrome: Web browser for testing.
* Docker Compose: Tool for running multi-container applications on Docker defined using the Compose file format.
   

### Installation Steps

1. **Clone the repository:**

```bash
git clone https://github.com/Vlad1kek/Personal_Project_Test_Automation.git
```

2. **Navigate to the project directory:**
```bash
cd Personal_Project_Test_Automation
```

3. **Install dependencies using Maven:**
```bash
mvn clean install
```

4. **Deployment using Docker**
* Modify the `docker-compose.yml` file (optional):
  * You can configure the services, ports, or any environment variables by editing the `docker-compose.yml` file according to your needs, more details from [here](https://docs.firefly-iii.org/how-to/firefly-iii/installation/docker/).


* Start the application:
  * To deploy the application, use the following Docker Compose command:
```bash
docker compose -f docker-compose.yml up -d --pull=always
```
  
5. **Setting up and preparing the Firefly III application:**
* Create your first account in the local Firefly III application
* Create a bank
* Create a second account
* Create Personal Access Token

6. **Configure Test Properties:**
   
The project requires a `local.properties` file to store environment-specific configurations such as database credentials, API endpoints, or other sensitive information. This file is not included in the repository for security reasons but can be created manually using the provided template.

### Steps to Create `local.properties`:

1. **Locate the Template:** In the `src/test/resources` directory of the project, you will find a file named `local.properties.TEMPLATE`.


2. **Copy the Template:** Create a new file based on this template:
```bash
cp src/test/resources/local.properties.TEMPLATE src/test/resources/local.properties
```
3.  **Edit the `local.properties` File:** Open the `local.properties` file located in the `src/test/resources` directory and fill in the necessary values based on your environment. Here’s an example:

```bash
#Created when registering in a local application Firefly III
default.admin.email=example@example.com #Any valid email.
default.admin.password=38zBU#qpSAGkhNTR #Your application password.

#Created when configured in a local application Firefly III
default.admin.email.second=second@example.com #The second registered user assumes the same password.
default.token=<> #Personal access token for API, save value.

#WebDriver settings
default.browser_options=--window-size=1920,1080 
#Before starting a new parameter, they must be separated by a semicolon.

#URL
default.host=localhost #Standard setting.
```

4. **Save the File:** After updating the file with your environment-specific settings, save it.

Notes
* The `local.properties` file should not be committed to the repository as it may contain sensitive information. Ensure it is listed in `.gitignore`.
* You may need to update the file if your environment changes or you switch machines.

---

## Running Tests

* **To execute the test cases, run the following command from the root directory:**

```bash
mvn clean test
```

* **Run a specific test:**
```bash
mvn -Dtest=TestClassName test
```

* **Once you've completed your tests, you can get a detailed report using Allure:**

```bash
allure serve target/allure-results
```

### Continuous Integration

The project is integrated with GitHub Actions to automate the execution of tests on every pull request. The CI pipeline ensures that:
* Tests run in a clean environment.
* Runs the tests across different environments.
* Publishes test results with Allure for review ([last commit report](https://vlad1kek.github.io/Personal_Project_Test_Automation)).

---

## Video Demonstrations

Demonstration of the functionality and automation of the project tests:

1. [Maven project showcase](https://youtu.be/Zm4-fngCPas) - This video showcases the execution of the test automation suite.
2. [GitHub Actions showcase](https://youtu.be/DmggoF8F1y4) - This video showcases execution of the test automation suite in GitHub Actions.
3. [Allure Report GitHub](https://youtu.be/iPqNznL2fPE) - This video showcases Allure report in GitHub Actions.

---

## License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/Vlad1kek/Firefly_III_Test_Automation/blob/main/LICENSE) file for details.