package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.*;
import util.ConfigReader;
import util.ExtentManager;

import java.net.URL;
import java.time.Duration;
import java.io.File;
import java.awt.Desktop;

public class TestBase {

    // --- Driver and reporting objects ---
    public static AppiumDriver driver; // accessible to tests
    public static ExtentReports extent; // accessible to listeners
    public static ExtentTest logger; // accessible to listeners

    // --- TestNG Suite Hooks ---

    @BeforeSuite
    public void startReport() {
        // Initialize ExtentReports via ExtentManager
        extent = ExtentManager.getExtentReports();
    }

    @AfterSuite
    public void endReport() {
        if (extent != null) {
            extent.flush();

            // Automatically open the report in default browser
            try {
                String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
                Desktop.getDesktop().browse(new File(reportPath).toURI());
                System.out.println("Extent Report opened automatically!");
            } catch (Exception e) {
                System.out.println("Failed to open Extent Report automatically.");
                e.printStackTrace();
            }
        }
    }

    @BeforeClass
    public void setup() throws Exception {
        // Setup Appium Android Driver with Flutter automation
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(ConfigReader.get("platformName"));
        options.setDeviceName(ConfigReader.get("deviceName"));
        options.setAutomationName(ConfigReader.get("automationName")); // Should be "Flutter"
        options.setApp(ConfigReader.get("appPath")); // path to your APK

        // Add Flutter-specific capabilities
        options.setCapability("useSystemUiautomator", false);
        options.setCapability("forceAppLaunch", true);
        options.setCapability("retryBackoffFactor", 1.5);
        options.setCapability("retryInterval", 100);
        options.setCapability("noReset", false); // Reset app state before each test
        options.setCapability("fullReset", false);

        // Increase connection timeout for Flutter apps
        options.setCapability("appWaitDuration", 45000); // 45 seconds

        driver = new AndroidDriver(
                new URL(ConfigReader.get("appiumServer")),
                options);

        // Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Note: pageLoadTimeout not supported by Flutter driver

        // For Flutter apps, wait for it to be ready
        System.out.println("Waiting for Flutter app to initialize...");
        Thread.sleep(3000); // Give app time to start

        try {
            driver.executeScript("flutter:isReady");
            System.out.println("✓ Flutter driver connected successfully");
        } catch (Exception e) {
            System.out.println("⚠ Warning: Flutter driver not ready, but continuing with test");
            System.out.println("Error: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        // Quit driver after tests in the class
        if (driver != null) {
            driver.quit();
        }
    }

    // --- Helper methods ---

    /**
     * Switch to Flutter context (for Flutter driver plugin)
     */
    // public void switchToFlutter() {
    // try {
    // driver.executeScript("flutter:isReady");
    // System.out.println("Already in Flutter context");
    // } catch (Exception e) {
    // System.err.println("Cannot switch to Flutter context: " + e.getMessage());
    // }
    // }
    /**
     * Switch to Flutter context (Flutter context is already set via automationName)
     */
    public void switchToFlutter() {
        try {
            ((AndroidDriver) driver).context("FLUTTER");
            System.out.println("Switched to Flutter context successfully");
        } catch (Exception e) {
            System.err.println("Failed to switch to Flutter context: " + e.getMessage());
        }
    }
}