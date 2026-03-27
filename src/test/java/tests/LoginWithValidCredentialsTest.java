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

public class LoginWithValidCredentialsTest extends TestBase {

    @Test(priority = 0, description = "TC01 - Login with valid credentials from JSON data")
    public void loginWithValidCredentials() {
        switchToFlutter();

        JsonArray loginTests = JsonReader.getJsonData().getAsJsonArray("loginTests");
        JsonObject firstTest = loginTests.get(0).getAsJsonObject();
        String email = firstTest.get("email").getAsString();
        String password = firstTest.get("password").getAsString();

        try {
            LoginPage loginPage = new LoginPage(driver);

            loginPage.enterUsername(email);
            String screenshotAfterUsername = ScreenshotUtil.takeScreenshot(driver, "TC01_step_1_username");
            logger.addScreenCaptureFromPath(screenshotAfterUsername);
            logger.info("✓ Username entered: " + email);
            System.out.println("✓ Username entered and screenshot captured");

            loginPage.enterPassword(password);
            String screenshotAfterPassword = ScreenshotUtil.takeScreenshot(driver, "TC01_step_2_password");
            logger.addScreenCaptureFromPath(screenshotAfterPassword);
            logger.info("✓ Password entered");
            System.out.println("✓ Password entered and screenshot captured");

            CatalogPage catalogPage = loginPage.clickLogin();
            String screenshotAfterClick = ScreenshotUtil.takeScreenshot(driver, "TC01_step_3_login_click");
            logger.addScreenCaptureFromPath(screenshotAfterClick);
            logger.info("✓ Login button clicked");
            System.out.println("✓ Login button clicked and screenshot captured");

            String catalogTitle = catalogPage.getCatalogTitle();
            String screenshotAfterVerify = ScreenshotUtil.takeScreenshot(driver, "TC01_step_4_catalog_page");
            logger.addScreenCaptureFromPath(screenshotAfterVerify);

            assert catalogTitle.equals("Catalog") : "TC01 Failed - Catalog page not displayed.";
            logger.info("✓ TC01 PASSED - Login successful with email: " + email);
            System.out.println("✓ TC01 PASSED - Login successful");

        } catch (Exception e) {
            String errorScreenshot = ScreenshotUtil.takeScreenshot(driver, "TC01_error");
            if (errorScreenshot != null)
                logger.addScreenCaptureFromPath(errorScreenshot);
            logger.info("✗ TC01 FAILED: " + e.getMessage());
            throw new RuntimeException("TC01 - Login test failed", e);
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            String finalScreenshot = ScreenshotUtil.takeScreenshot(driver, "TC01_final_state");
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
                System.out.println("✓ App terminated after TC01");
            }
        } catch (Exception e) {
            System.out.println("⚠ TC01 teardown note: " + e.getMessage());
        }
    }
}