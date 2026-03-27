package tests;

import base.TestBase;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import pages.LoginPage;
import pages.CatalogPage;
import util.ScreenshotUtil;

public class LoginButtonInteractionTest extends TestBase {

    @Test(priority = 0, description = "TC04 - Login button triggers correct action")
    public void loginButtonInteraction() {
        switchToFlutter();

        try {
            LoginPage loginPage = new LoginPage(driver);

            String screenshotBeforeClick = ScreenshotUtil.takeScreenshot(driver, "TC04_step_1_before_click");
            logger.addScreenCaptureFromPath(screenshotBeforeClick);
            logger.info("✓ Login screen loaded");
            System.out.println("✓ Login screen loaded and screenshot captured");

            loginPage.enterUsername("test@test.com");
            loginPage.enterPassword("password");
            String screenshotAfterInput = ScreenshotUtil.takeScreenshot(driver, "TC04_step_2_credentials_entered");
            logger.addScreenCaptureFromPath(screenshotAfterInput);
            logger.info("✓ Credentials entered");
            System.out.println("✓ Credentials entered and screenshot captured");

            CatalogPage catalogPage = loginPage.clickLogin();
            String screenshotAfterClick = ScreenshotUtil.takeScreenshot(driver, "TC04_step_3_button_clicked");
            logger.addScreenCaptureFromPath(screenshotAfterClick);
            logger.info("✓ Login button clicked");
            System.out.println("✓ Login button clicked and screenshot captured");

           

        } catch (Exception e) {
            String errorScreenshot = ScreenshotUtil.takeScreenshot(driver, "TC04_error");
            if (errorScreenshot != null)
                logger.addScreenCaptureFromPath(errorScreenshot);
            logger.info("✗ TC04 FAILED: " + e.getMessage());
            throw new RuntimeException("TC04 - Button interaction test failed", e);
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            String finalScreenshot = ScreenshotUtil.takeScreenshot(driver, "TC04_final_state");
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
                System.out.println("✓ App terminated after TC04");
            }
        } catch (Exception e) {
            System.out.println("⚠ TC04 teardown note: " + e.getMessage());
        }
    }
}