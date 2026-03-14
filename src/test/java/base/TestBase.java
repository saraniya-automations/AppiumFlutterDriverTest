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
    public static AppiumDriver driver;        // accessible to tests
    public static ExtentReports extent;       // accessible to listeners
    public static ExtentTest logger;          // accessible to listeners

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

        driver = new AndroidDriver(
                new URL(ConfigReader.get("appiumServer")),
                options
        );

        // Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // For Flutter apps, execute flutter:isReady to ensure Flutter driver is connected
        try {
            driver.executeScript("flutter:isReady");
            System.out.println("✓ Flutter driver connected successfully");
        } catch (Exception e) {
            System.err.println("⚠ Flutter driver not ready. Ensure Appium Flutter Driver plugin is installed on your Appium server.");
            System.err.println("Install with: npm install -g appium-flutter-driver");
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
    //     try {
    //         driver.executeScript("flutter:isReady");
    //         System.out.println("Already in Flutter context");
    //     } catch (Exception e) {
    //         System.err.println("Cannot switch to Flutter context: " + e.getMessage());
    //     }
    // }
    /**
     * Switch to Flutter context (Flutter context is already set via automationName)
     */
    public void switchToFlutter() {
       try {
        ((AndroidDriver) driver).context("FLUTTER");
        System.out.println("Switched to Flutter context successfully");
       }catch (Exception e) {
        System.err.println("Failed to switch to Flutter context: " + e.getMessage());
       }
    }
}