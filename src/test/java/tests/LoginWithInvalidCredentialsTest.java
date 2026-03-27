package tests;

import base.TestBase;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import pages.LoginPage;
import pages.CatalogPage;
import util.ScreenshotUtil;

public class LoginWithInvalidCredentialsTest extends TestBase {

    @Test(priority = 0, description = "TC02 - Login with invalid credentials")
    public void loginWithInvalidCredentials() {
        switchToFlutter();

        try {
            LoginPage loginPage = new LoginPage(driver);

            loginPage.enterUsername("invalid@test.com");
            String screenshotAfterUsername = ScreenshotUtil.takeScreenshot(driver, "TC02_step_1_username");
            logger.addScreenCaptureFromPath(screenshotAfterUsername);
            logger.info("✓ Invalid username entered");
            System.out.println("✓ Invalid username entered and screenshot captured");

            loginPage.enterPassword("wrongpassword");
            String screenshotAfterPassword = ScreenshotUtil.takeScreenshot(driver, "TC02_step_2_password");
            logger.addScreenCaptureFromPath(screenshotAfterPassword);
            logger.info("✓ Invalid password entered");
            System.out.println("✓ Invalid password entered and screenshot captured");

            loginPage.clickLogin();
            String screenshotAfterClick = ScreenshotUtil.takeScreenshot(driver, "TC02_step_3_login_click");
            logger.addScreenCaptureFromPath(screenshotAfterClick);
            logger.info("✓ Login button clicked with invalid credentials");
            System.out.println("✓ Login button clicked and screenshot captured");

            boolean isLoginButtonPresent = loginPage.isLoginButtonPresent();
            if (isLoginButtonPresent) {
                logger.info("✓ TC02 PASSED: Login button is still present, indicating login failure as expected");
                System.out.println("✓ TC02 PASSED: Login button is still present, indicating login failure as expected");
                        assert false : "TC02 Failed - Login button should not be present after attempting login with invalid credentials";
            } else {
                throw new AssertionError(
                        "✗ TC02 FAILED: Login button is not present after attempting login with invalid credentials");
            }

        } catch (Exception e) {
            String errorScreenshot = ScreenshotUtil.takeScreenshot(driver, "TC02_error");
            if (errorScreenshot != null)
                logger.addScreenCaptureFromPath(errorScreenshot);
            logger.info("✗ TC02 FAILED: " + e.getMessage());
            throw new RuntimeException("TC02 - Invalid credentials test failed", e);
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            String finalScreenshot = ScreenshotUtil.takeScreenshot(driver, "TC02_final_state");
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
                System.out.println("✓ App terminated after TC02");
            }
        } catch (Exception e) {
            System.out.println("⚠ TC02 teardown note: " + e.getMessage());
        }
    }
}