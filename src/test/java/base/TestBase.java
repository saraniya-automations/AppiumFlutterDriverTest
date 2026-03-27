package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

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

    @AfterClass
    public void tearDown() {
        // Quit driver after tests in the class
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeClass
    public void setup() throws Exception {
        String platform = ConfigReader.get("platform").trim().toLowerCase();

        if (platform.equals("android")) {
            driver = createAndroidDriver();
        } else if (platform.equals("ios")) {
            driver = createIOSDriver();
        } else {
            throw new RuntimeException("Unsupported platform: " + platform);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Thread.sleep(3000);

        try {
            driver.executeScript("flutter:isReady");
            System.out.println("Flutter driver connected successfully");
        } catch (Exception e) {
            System.out.println("Warning: Flutter driver not ready — " + e.getMessage());
        }
    }

    private AppiumDriver createAndroidDriver() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(ConfigReader.get("android.platformName"));
        options.setDeviceName(ConfigReader.get("android.deviceName"));
        options.setAutomationName(ConfigReader.get("android.automationName"));
        options.setApp(ConfigReader.get("android.appPath"));
        options.setCapability("useSystemUiautomator", false);
        options.setCapability("forceAppLaunch", true);
        options.setCapability("noReset", false);
        options.setCapability("appWaitDuration", 45000);
        return new AndroidDriver(new URL(ConfigReader.get("appiumServer")), options);
    }

    private AppiumDriver createIOSDriver() throws Exception {
        XCUITestOptions options = new XCUITestOptions();
        options.setPlatformName("iOS");
        options.setDeviceName(ConfigReader.get("ios.deviceName"));
        options.setPlatformVersion(ConfigReader.get("ios.platformVersion"));
        options.setAutomationName("Flutter");
        options.setApp(ConfigReader.get("ios.appPath"));
        options.setBundleId(ConfigReader.get("ios.bundleId"));
        options.setNoReset(true);

        String wdaPort = ConfigReader.get("ios.wdaLocalPort");
        if (wdaPort != null && !wdaPort.isBlank()) {
            options.setWdaLocalPort(Integer.parseInt(wdaPort));
        }

        return new IOSDriver(new URL(ConfigReader.get("appiumServer")), options);
    }

    public void switchToFlutter() {
        try {
            String platform = ConfigReader.get("platform").trim().toLowerCase();
            if (platform.equals("android")) {
                ((AndroidDriver) driver).context("FLUTTER");
            }
            // iOS Flutter context is set automatically via automationName=Flutter
            System.out.println("Flutter context ready");
        } catch (Exception e) {
            System.err.println("Failed to switch Flutter context: " + e.getMessage());
        }
    }
}