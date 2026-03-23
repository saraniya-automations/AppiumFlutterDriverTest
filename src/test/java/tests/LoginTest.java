package tests;

import base.TestBase;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import pages.LoginPage;
import pages.CatalogPage;
import util.JsonReader;
import util.ScreenshotUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class LoginTest extends TestBase {

    /**
     * Test login with valid credentials from JSON
     */
    @Test(priority = 0, description = "Login with valid credentials from JSON data")
    public void loginWithValidCredentials() {
        // Switch context to Flutter if needed
        switchToFlutter();

        // Get first test from JSON array
        JsonArray loginTests = JsonReader.getJsonData().getAsJsonArray("loginTests");
        JsonObject firstTest = loginTests.get(0).getAsJsonObject();

        String email = firstTest.get("email").getAsString();
        String password = firstTest.get("password").getAsString();

        try {
            // Perform login
            LoginPage loginPage = new LoginPage(driver);

            // Step 1: Enter username
            loginPage.enterUsername(email);
            String screenshotAfterUsername = ScreenshotUtil.takeScreenshot(driver, "step_1_after_username");
            logger.addScreenCaptureFromPath(screenshotAfterUsername);
            logger.info("✓ Username entered: " + email);
            System.out.println("✓ Username entered and screenshot captured");

            // Step 2: Enter password
            loginPage.enterPassword(password);
            String screenshotAfterPassword = ScreenshotUtil.takeScreenshot(driver, "step_2_after_password");
            logger.addScreenCaptureFromPath(screenshotAfterPassword);
            logger.info("✓ Password entered");
            System.out.println("✓ Password entered and screenshot captured");

            // Step 3: Click login button
            CatalogPage catalogPage = loginPage.clickLogin();
            String screenshotAfterClick = ScreenshotUtil.takeScreenshot(driver, "step_3_after_login_click");
            logger.addScreenCaptureFromPath(screenshotAfterClick);
            logger.info("✓ Login button clicked");
            System.out.println("✓ Login button clicked and screenshot captured");

            // Step 4: Verify catalog title
            String catalogTitle = catalogPage.getCatalogTitle();
            String screenshotAfterVerify = ScreenshotUtil.takeScreenshot(driver, "step_4_catalog_page");
            logger.addScreenCaptureFromPath(screenshotAfterVerify);

            assert catalogTitle.equals("Catalog") : "Login failed, Catalog page not displayed.";
            logger.info("✓ Login successful with email: " + email);
            logger.info("✓ Catalog page verified");
            System.out.println("✓ Login successful and all screenshots captured");
            // catalogPage.clickCart();
            // catalogPage.clickAddButton("Code Smell");

        } catch (Exception e) {
            String errorScreenshot = ScreenshotUtil.takeScreenshot(driver, "error_screenshot");
            if (errorScreenshot != null) {
                logger.addScreenCaptureFromPath(errorScreenshot);
            }
            logger.info("✗ Test failed: " + e.getMessage());
            throw new RuntimeException("Login test failed", e);
        }
    }

    // @AfterMethod
    // public void tearDown() {
    // try {
    // // Take final screenshot
    // String finalScreenshot = ScreenshotUtil.takeScreenshot(driver,
    // "final_state");
    // if (finalScreenshot != null && logger != null) {
    // logger.addScreenCaptureFromPath(finalScreenshot);
    // logger.info("✓ Final screenshot captured");
    // }

    // if (driver != null) {
    // // Terminate the app using mobile command
    // java.util.HashMap<String, Object> params = new java.util.HashMap<>();
    // params.put("bundleId", "com.example.provider_shopper");
    // driver.executeScript("mobile: terminateApp", params);
    // System.out.println("✓ App terminated after test");
    // }
    // } catch (Exception e) {
    // System.out.println("⚠ Note: App termination note: " + e.getMessage());
    // }
    // }

    @AfterMethod
    public void tearDown() {
        try {
            // Take final screenshot
            String finalScreenshot = ScreenshotUtil.takeScreenshot(driver, "final_state");
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
                System.out.println("✓ App terminated after test");
            }
        } catch (Exception e) {
            System.out.println("⚠ Note: App termination note: " + e.getMessage());
        }
    }
}