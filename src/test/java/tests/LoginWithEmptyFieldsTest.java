package tests;

import base.TestBase;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import pages.LoginPage;
import pages.CatalogPage;
import util.ScreenshotUtil;

public class LoginWithEmptyFieldsTest extends TestBase {

    @Test(priority = 0, description = "TC05 - Login with empty fields should not be allowed")
    public void loginWithEmptyFields() {
        switchToFlutter();

        try {
            LoginPage loginPage = new LoginPage(driver);

            String screenshotEmptyFields = ScreenshotUtil.takeScreenshot(driver, "TC05_step_1_empty_fields");
            logger.addScreenCaptureFromPath(screenshotEmptyFields);
            logger.info("✓ Empty fields - no input entered");
            System.out.println("✓ Empty fields screenshot captured");

            loginPage.clickLogin();
            String screenshotAfterClick = ScreenshotUtil.takeScreenshot(driver, "TC05_step_2_after_login_click");
            logger.addScreenCaptureFromPath(screenshotAfterClick);
            logger.info("✓ Login button clicked with empty fields");
            System.out.println("✓ Login clicked with empty fields and screenshot captured");

            assert false : "TC05 Failed - App allows login with empty fields, known bug";

        } catch (Exception e) {
            String errorScreenshot = ScreenshotUtil.takeScreenshot(driver, "TC05_error");
            if (errorScreenshot != null)
                logger.addScreenCaptureFromPath(errorScreenshot);
            logger.info("✗ TC05 FAILED - Known Bug: App allows login with empty fields");
            throw new RuntimeException("TC05 - Empty fields validation not implemented", e);
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            String finalScreenshot = ScreenshotUtil.takeScreenshot(driver, "TC05_final_state");
            if (finalScreenshot != null && logger != null) {
                logger.addScreenCaptureFromPath(finalScreenshot);
                logger.info("✓ Final screenshot captured");
            }
            if (driver != null) {
                java.util.HashMap<String, Object> params = new java.util.HashMap<>();
                String platform = util.ConfigReader.get("platform").trim().toLowerCase();
                if (platform.equals("ios")) {
                    params.put("bundleId", util.ConfigReader.get("ios.bundleId"));
                } else {
                    params.put("appId", util.ConfigReader.get("android.appPackage"));
                }
                driver.executeScript("mobile: terminateApp", params);
                System.out.println("✓ App terminated after TC05");
            }
        } catch (Exception e) {
            System.out.println("⚠ TC05 teardown note: " + e.getMessage());
        }
    }
}