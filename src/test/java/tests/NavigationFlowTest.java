package tests;

import base.TestBase;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import pages.LoginPage;
import pages.CatalogPage;
import util.ScreenshotUtil;

public class NavigationFlowTest extends TestBase {

    @Test(priority = 0, description = "TC03 - Navigation from Login to Catalog screen")
    public void navigationFlow() {
        switchToFlutter();

        try {
            LoginPage loginPage = new LoginPage(driver);

            loginPage.enterUsername("test@test.com");
            String screenshotLoginScreen = ScreenshotUtil.takeScreenshot(driver, "TC03_step_1_login_screen");
            logger.addScreenCaptureFromPath(screenshotLoginScreen);
            logger.info("✓ On login screen");
            System.out.println("✓ Login screen screenshot captured");

            loginPage.enterPassword("password");
            String screenshotAfterPassword = ScreenshotUtil.takeScreenshot(driver, "TC03_step_2_credentials");
            logger.addScreenCaptureFromPath(screenshotAfterPassword);
            logger.info("✓ Credentials entered");
            System.out.println("✓ Credentials entered and screenshot captured");

            CatalogPage catalogPage = loginPage.clickLogin();
            String screenshotAfterNav = ScreenshotUtil.takeScreenshot(driver, "TC03_step_3_navigated");
            logger.addScreenCaptureFromPath(screenshotAfterNav);
            logger.info("✓ Navigated from Login to Catalog");
            System.out.println("✓ Navigation triggered and screenshot captured");

            String catalogTitle = catalogPage.getCatalogTitle();
            String screenshotCatalog = ScreenshotUtil.takeScreenshot(driver, "TC03_step_4_catalog_verified");
            logger.addScreenCaptureFromPath(screenshotCatalog);

            assert catalogTitle.equals("Catalog") : "TC03 Failed - Navigation did not reach Catalog screen";
            logger.info("✓ TC03 PASSED - Navigation from Login to Catalog verified");
            System.out.println("✓ TC03 PASSED - Navigation verified");

        } catch (Exception e) {
            String errorScreenshot = ScreenshotUtil.takeScreenshot(driver, "TC03_error");
            if (errorScreenshot != null)
                logger.addScreenCaptureFromPath(errorScreenshot);
            logger.info("✗ TC03 FAILED: " + e.getMessage());
            throw new RuntimeException("TC03 - Navigation test failed", e);
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            String finalScreenshot = ScreenshotUtil.takeScreenshot(driver, "TC03_final_state");
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
                System.out.println("✓ App terminated after TC03");
            }
        } catch (Exception e) {
            System.out.println("⚠ TC03 teardown note: " + e.getMessage());
        }
    }
}