//package Util;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.android.options.UiAutomator2Options;
//import java.util.Set;
//import org.testng.ITestResult;
//import org.testng.annotations.*;
//
//import java.io.File;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.time.Duration;
//
//public class TestBase {
//
//    public static String appPath = "/flutterlogin-debug.apk";
//    public static AppiumDriver driver;
//    public ExtentReports extent;
//    public ExtentTest logger;
//
//    private final String reportDirectory = "reports";
//    private final String reportFormat = "html";
//    private final String testName = "Flutter Automation";
//    private final String udid = "";        // Set your device UDID here
//    private final String deviceName = ""; // Set your device name here
//
//    @BeforeClass
//    public void setup() throws MalformedURLException {
//        File rootFile = new File("");
//        File appFile = new File(rootFile.getAbsolutePath() + appPath);
//        appPath = appFile.getAbsolutePath();
//
//        UiAutomator2Options options = new UiAutomator2Options();
//        options.setDeviceName(deviceName);
//        options.setPlatformName("Android");
//        options.setAutomationName("Flutter");
//        options.setApp(appPath);
//        options.setNoReset(true);
//        options.setSkipUnlock(true);
//        options.setCapability("reportDirectory", reportDirectory);
//        options.setCapability("reportFormat", reportFormat);
//        options.setCapability("testName", testName);
//
//        // Set udid only if provided
//        if (!udid.isEmpty()) {
//            options.setUdid(udid);
//        }
//
//        // Appium 2.x no longer needs /wd/hub — use http://127.0.0.1:4723
//        // If you're still on Appium 1.x, change to: http://127.0.0.1:4723/wd/hub
//        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//    }
//
//    @AfterClass
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//
//    @BeforeTest
//    public void startReport() {
//        String reportPath = System.getProperty("user.dir") + "/test-output/extentReport.html";
//        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
//        extent = new ExtentReports();
//        extent.attachReporter(spark);
//    }
//
//    @AfterMethod
//    public void getResult(ITestResult result) {
//        if (result.getStatus() == ITestResult.FAILURE) {
//            logger.fail("Test Case Failed: " + result.getName());
//            logger.fail("Exception: " + result.getThrowable());
//        } else if (result.getStatus() == ITestResult.SKIP) {
//            logger.skip("Test Case Skipped: " + result.getName());
//        } else if (result.getStatus() == ITestResult.SUCCESS) {
//            logger.pass("Test Case Passed: " + result.getName());
//        }
//    }
//
//    @AfterTest
//    public void endReport() {
//        extent.flush();
//    }
//
//    public static void switchContext(String context) {
//        AndroidDriver androidDriver = (AndroidDriver) driver;
//        Set<String> contexts = androidDriver.getContextHandles();
//        for (String c : contexts) {
//            if (c.contains(context)) {
//                androidDriver.context(c);
//                break;
//            }
//        }
//    }
//}

package Util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Set;

public class TestBase {

    public static String appPath = "/flutterlogin-debug.apk";
    public static AppiumDriver driver;
    public ExtentReports extent;
    public ExtentTest logger;

    private final String udid = "";        // run: adb devices
    private final String deviceName = "";  // e.g. "Pixel 6" or "emulator-5554"

    @BeforeClass
    public void setup() throws MalformedURLException {
        File rootFile = new File("");
        File appFile = new File(rootFile.getAbsolutePath() + appPath);
        appPath = appFile.getAbsolutePath();

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(deviceName);
        options.setPlatformName("Android");
        options.setAutomationName("Flutter");
        options.setApp(appPath);
        options.setNoReset(true);
        options.setSkipUnlock(true);

        if (!udid.isEmpty()) {
            options.setUdid(udid);
        }

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeTest
    public void startReport() {
        String reportPath = System.getProperty("user.dir") + "/test-output/extentReport.html";
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.fail("Test Case Failed: " + result.getName());
            logger.fail("Exception: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.skip("Test Case Skipped: " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.pass("Test Case Passed: " + result.getName());
        }
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }

    public static void switchContext(String context) {
        AndroidDriver androidDriver = (AndroidDriver) driver;
        Set<String> contexts = androidDriver.getContextHandles();
        for (String c : contexts) {
            if (c.contains(context)) {
                androidDriver.context(c);
                break;
            }
        }
    }
}