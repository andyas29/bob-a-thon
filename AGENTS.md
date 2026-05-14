# AGENTS.md

This file provides guidance to agents when working with code in this repository.

## Project Overview

This is a Java-based automated testing framework developed for the IBM Bob-a-thon competition. The project integrates Selenium WebDriver with AI agents to automate web application testing, generate reports, and provide intelligent interpretation of test results.

**Core Objective**: Run automated test suites → Generate reports → AI agent interprets results (identifying application vs. test suite issues) → Optional: AI agent attempts to fix test suite issues.

### Technology Stack

- **Language**: Java 23
- **Build Tool**: Maven 3.x
- **Testing Framework**: JUnit 5 (Jupiter)
- **Web Automation**: Selenium WebDriver 4.28.1
- **BDD Support**: Cucumber 7.21.1 (configured but not yet implemented)
- **JSON Processing**: Jackson Databind 2.18.2
- **Supported Browsers**: Chrome, Firefox, Edge, Remote WebDriver

### Architecture

The project follows the **Page Object Model (POM)** design pattern with the following structure:

- **`pages/`**: Page Object classes representing web pages (HomePage, ChannelPage, VideoPage)
- **`utils/`**: Utility classes for WebDriver management, configuration loading, and element interactions
- **`constants/`**: Application constants (e.g., browser types)
- **`requests/`**: HTTP request handling for API validation
- **`steps/`**: Test step definitions (currently using JUnit, Cucumber integration pending)

## Building and Running

### Prerequisites

- Java 23 or higher
- Maven 3.5+
- Browser drivers (automatically managed by Selenium Manager)

### Build Commands

```bash
# Clean and compile the project
mvn clean compile

# Run all tests
mvn test

# Run tests with specific tag
mvn test -Dgroups="browser"

# Run integration tests
mvn verify

# Package the application
mvn package
```

### Configuration

Edit `src/main/resources/config.properties` to configure:
- `browser`: Browser to use (chrome, firefox, edge)
- `baseUrl`: Target application URL (currently YouTube)
- `api.key`: API key for YouTube Data API v3 (required for subscriber count tests)

### Running Specific Tests

```bash
# Run a specific test class
mvn test -Dtest=BrowserTest

# Run a specific test method
mvn test -Dtest=BrowserTest#searchTest
```

## Development Conventions

### Page Object Pattern

- All page classes extend the base `Page` class
- Use `@FindBy` annotations for element locators
- Keep page objects focused on element definitions, not test logic
- Page objects are instantiated via `WebElementUtil.pageFactory()`

### Test Structure

- Tests are organized in the `steps/` package
- Use JUnit 5 annotations: `@Test`, `@BeforeEach`, `@AfterEach`, `@BeforeAll`
- Tag tests appropriately using `@Tag` (e.g., `@Tag("browser")`)
- Use descriptive `@DisplayName` annotations for test readability
- Each test method should initialize its own WebDriver instance via `@BeforeEach`
- Always clean up WebDriver instances in `@AfterEach` using `driver.quit()`

### WebDriver Management

- Use `WebDriverWrapper` class to initialize browser instances
- Default settings include:
  - 5-second implicit wait
  - Maximized window
- Browser selection is configuration-driven via `config.properties`

### Element Interaction

- Use `WebElementUtil` for all element interactions
- Available methods: `get()`, `click()`, `sendKeys()`, `pageFactory()`
- Utility handles common Selenium operations with built-in waits

### API Testing

- API requests are handled through the `Request` class
- Currently supports YouTube Data API v3 for validation
- Combine UI and API validation in tests (e.g., comparing subscriber counts)

## Current Test Coverage

The project includes the following test scenarios:

1. **Search Functionality**: Tests YouTube search feature
2. **Cookie Management**: Validates cookie rejection flow
3. **API Validation**: Compares subscriber counts between API and UI
4. **Video Page Verification**: Checks video page elements and content

## Future Development

### Planned Features

- **Cucumber Integration**: BDD framework is configured but not yet implemented
- **Test Reporting**: Generate comprehensive test reports
- **AI Agent Integration**: Implement AI-powered test result interpretation
- **Self-Healing Tests**: AI agent to automatically fix test suite issues

### Adding New Tests

1. Create page objects in `pages/` package for new pages
2. Add test methods in `steps/` package
3. Use appropriate JUnit 5 annotations and tags
4. Follow existing naming conventions and patterns
5. Ensure proper WebDriver lifecycle management

### Adding New Page Objects

```java
public class NewPage extends Page {
    @FindBy(css = "selector")
    public WebElement element;
}
```

Then instantiate in test:
```java
NewPage newPage = elementUtil.pageFactory(NewPage.class);
```

## Notes for AI Agents

- **Configuration**: Always check `config.properties` before running tests
- **Browser Compatibility**: Tests are designed to work across Chrome, Firefox, and Edge
- **API Keys**: YouTube API tests require a valid API key in configuration
- **Implicit Waits**: 5-second implicit wait is configured globally
- **Test Independence**: Each test should be independent and not rely on other tests
- **Cleanup**: Always ensure WebDriver instances are properly closed to prevent resource leaks
- **Cucumber**: While dependencies are present, Cucumber features are not yet implemented
