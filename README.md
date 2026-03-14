# Appium Flutter Driver Test

Automated UI testing framework for Flutter mobile applications using Appium and Java with TestNG.

## Overview

This project provides a comprehensive test automation framework for Flutter applications using:

- **Appium**: Mobile app automation framework
- **Flutter Driver**: Driver for Flutter app interaction
- **TestNG**: Testing framework
- **Maven**: Build automation
- **Extent Reports**: Test reporting and analytics
- **Page Object Model**: Design pattern for maintainability

## Prerequisites

Before running the tests, ensure you have the following installed:

- **Java**: JDK 11 or higher
- **Maven**: 3.6 or higher
- **Appium**: 2.0 or higher
- **Node.js**: Required for Appium
- **Flutter SDK**: Latest stable version
- **Android SDK**: For Android testing
- **Xcode**: For iOS testing (macOS only)

## Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd AppiumFlutterDriverTest
```

### 2. Install Dependencies

```bash
mvn clean install
```

### 3. Configure Appium Server

```bash
# Install Appium globally (if not already installed)
npm install -g appium

# Install Flutter driver
appium driver install flutter

# Install UiAutomator2 driver (Android)
appium driver install uiautomator2

# Install XCUITest driver (iOS)
appium driver install xcuitest

# Start Appium server (default: http://localhost:4723)
appium
```

### 4. Update Configuration

Edit `src/test/resources/config.properties`:

```properties
# Appium Server
appium.server.url=http://localhost:4723
appium.timeout=30

# Device Configuration
device.name=emulator-5554
device.platform=Android
device.os.version=11
device.app.bundle.id=com.example.provider_shopper
device.app.location=/path/to/app.apk
```

### 5. Prepare Test Data

Update test data in `src/test/resources/testData/testData.json`:

```json
{
  "loginTests": [
    {
      "email": "test@example.com",
      "password": "password123"
    }
  ]
}
```

## Project Structure

```
src/
├── test/
│   ├── java/
│   │   ├── Main.java                     # Entry point
│   │   ├── appium_flutter_driver/        # Flutter driver utilities
│   │   │   ├── FlutterFinder.java
│   │   │   └── finder/
│   │   │       └── FlutterElement.java
│   │   ├── base/
│   │   │   └── TestBase.java             # Base test class with setup/teardown
│   │   ├── driver/
│   │   │   └── DriverManager.java        # Appium driver initialization
│   │   ├── listeners/
│   │   │   └── TestListener.java         # TestNG event listeners
│   │   ├── pages/
│   │   │   ├── BasePage.java             # Base page with common methods
│   │   │   ├── LoginPage.java            # Login page object
│   │   │   └── CatalogPage.java          # Catalog page object
│   │   ├── tests/
│   │   │   └── LoginTest.java            # Test cases
│   │   └── util/
│   │       ├── ConfigReader.java         # Reads config.properties
│   │       ├── JsonReader.java           # Reads JSON test data
│   │       ├── ScreenshotUtil.java       # Screenshot utilities
│   │       └── ExtentManager.java        # Extent report management
│   └── resources/
│       ├── config.properties             # Configuration file
│       └── testData/
│           └── testData.json             # Test data in JSON format
```

## Running Tests

### Run All Tests

```bash
mvn clean test
```

### Run Specific Test Class

```bash
mvn clean test -Dtest=LoginTest
```

### Run Specific Test Method

```bash
mvn clean test -Dtest=LoginTest#loginWithValidCredentials
```

### Run with TestNG XML

```bash
mvn clean test -Dsurefire.suiteXmlFiles=run-test.xml
```

## Test Reports

After test execution, reports are generated in two locations:

### Extent Reports

- **Location**: `test-output/extentReport.html`
- **Features**:
  - Detailed test execution logs
  - Screenshot attachments for each step
  - Test timeline and analytics
  - Device and environment info

### TestNG Reports

- **Location**: `target/surefire-reports/`
- **Formats**: HTML, XML (for CI/CD integration)

## Architecture

### Page Object Model (POM)

Each page of the application is represented as a class:

- Encapsulates page elements and actions
- Reduces code duplication
- Improves maintainability

### Base Test Class

- Initializes Appium driver
- Handles context switching (Flutter/Native)
- Manages test lifecycle
- Provides logging and reporting

### Utilities

- **ConfigReader**: Manages configuration properties
- **JsonReader**: Parses JSON test data
- **ScreenshotUtil**: Captures screenshots at key steps
- **ExtentManager**: Manages Extent report instances

## Key Features

✓ **Flutter-specific testing** with Flutter Driver integration  
✓ **Cross-platform support** (Android & iOS)  
✓ **Page Object Model** pattern for maintainability  
✓ **JSON-based test data** for easy data management  
✓ **Screenshot capture** at each test step  
✓ **Extent Reports** with detailed analytics  
✓ **Comprehensive logging** for debugging  
✓ **TestNG integration** with listeners and annotations  
✓ **CI/CD ready** with XML report generation

## Example Test

```java
@Test(priority = 0, description = "Login with valid credentials")
public void loginWithValidCredentials() {
    switchToFlutter();

    JsonObject testData = JsonReader.getJsonData().getAsJsonArray("loginTests").get(0).getAsJsonObject();
    String email = testData.get("email").getAsString();
    String password = testData.get("password").getAsString();

    LoginPage loginPage = new LoginPage(driver);
    loginPage.enterUsername(email);
    loginPage.enterPassword(password);
    CatalogPage catalogPage = loginPage.clickLogin();

    assert catalogPage.getCatalogTitle().equals("Catalog");
}
```

## Troubleshooting

### Appium Server Connection Issues

- Ensure Appium server is running on the configured URL
- Check firewall settings
- Verify device is connected: `adb devices`

### Element Not Found

- Verify element identifiers in Flutter Inspector
- Check if app is in correct context (Flutter/Native)
- Use `FlutterFinder` for Flutter-specific element detection

### Screenshot Issues

- Ensure `screenshots/` directory exists
- Check file permissions
- Verify screenshot paths in config

### Test Data Not Loading

- Verify JSON file path in `config.properties`
- Check JSON syntax with a validator
- Ensure test data keys match code references

## CI/CD Integration

For GitLab CI, use the generated XML reports:

```yaml
artifacts:
  reports:
    junit: target/surefire-reports/TEST-*.xml
```

View HTML reports:

```yaml
artifacts:
  paths:
    - test-output/extentReport.html
    - target/surefire-reports/
```

## Contributing

1. Follow Page Object Model pattern
2. Add meaningful test descriptions
3. Capture screenshots for key steps
4. Update README for new features
5. Ensure all tests pass before committing

