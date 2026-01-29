# Bigbank Loan Calculator - Test Automation Framework
**This is a Modular test automation framework using Playwright + Java + TestNG with Page Object Model for UI and Service Layer for API testing. Configuration-driven via properties file, eliminating hardcoded values. Features Allure reporting with screenshots, and clear separation between UI/API layers for maintainability and scalability.**

### Key Principles
- **Modularity**: Separate layers for UI, API, and utilities
- **Reusability**: Shared components across tests
- **Maintainability**: Config-driven, no hardcoded values
- **Scalability**: Easy to add new tests and features

### Coverage Areas

-  **UI Tests**: Calculator modal behavior, value changes, persistence
-  **API Tests**: Loan calculation endpoint validation  
-  **Boundary Tests**: Min/Max value validation
-  **Negative Tests**: Invalid input handling (negative, zero, alphanumeric, special chars)

## Tech Stack
| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17 | Programming language |
| **Playwright** | 1.57.0 | Browser automation & API testing |
| **TestNG** | 7.11.0 | Test framework & parallel execution |
| **Allure** | 2.32.0 | Test reporting with rich visualizations |
| **Jackson** | 2.16.0 | JSON parsing & serialization |
| **Maven** | 3.8+ | Build automation & dependency management |

## Prerequisites
- Java 17 or higher installed
- Maven 3.8+ installed

## How to Run the Test
- Clone the Repository
  ```bash
      git clone https://github.com/yourusername/BigBank_Task.git
      cd BigBank_Task  
  ```
- Install Dependencies
  ```bash
      mvn clean install
  ```
-  Running Tests
  ```bash
      mvn clean test
  ```
- Generate Report
  ```bash
    mvn allure:serve
  ```
## CI/CD & Reporting (GitHub Actions)
This project uses GitHub Actions to automatically execute API tests on every push, pull request, or manual trigger. The workflow installs all required dependencies, runs the Playwright + TestNG test suite, and generates an Allure report. Test results are published to GitHub Pages, allowing easy access to historical reports without local setup.No manual configuration is required—tests and reports run fully automatically in CI.
The latest build report is automatically generated and deployed to: **[View Live Report](https://faheem412.github.io/HomeTask_BigBank/)**

## Screenshots
<img width="452" height="305" alt="image" src="https://github.com/user-attachments/assets/5652b887-f4d5-4cfe-aeb9-3587df859cfe" />

  
