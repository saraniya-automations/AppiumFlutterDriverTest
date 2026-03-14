package listeners;

import com.aventstack.extentreports.Status;
import driver.DriverManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import util.ScreenshotUtil;
import base.TestBase;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("===== Test Suite Started =====");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("===== Test Suite Finished =====");
        TestBase.extent.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        TestBase.logger = TestBase.extent.createTest(result.getMethod().getMethodName());
        System.out.println("Starting Test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Capture screenshot on pass
        String screenshotPath = ScreenshotUtil.takeScreenshot(TestBase.driver, result.getMethod().getMethodName());
        try {
            TestBase.logger.pass("Test Passed",
                com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
            );
        } catch (Exception e) {
            TestBase.logger.pass("Test Passed, screenshot not available");
        }
        System.out.println("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Capture screenshot on fail
        String screenshotPath = ScreenshotUtil.takeScreenshot(TestBase.driver, result.getMethod().getMethodName());
        try {
            TestBase.logger.fail("Test Failed",
                com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
            );
            TestBase.logger.fail(result.getThrowable());
        } catch (Exception e) {
            TestBase.logger.fail("Test Failed, screenshot not available");
        }
        System.out.println("Test Failed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TestBase.logger.skip("Test Skipped");
        System.out.println("Test Skipped: " + result.getMethod().getMethodName());
    }
}