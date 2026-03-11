//package tests;
//
//import Util.TestBase;
//import com.aventstack.extentreports.Status;
//import org.openqa.selenium.TimeoutException;
//import org.testng.annotations.Test;
//import pages.LoginPage;
//
//public class AppiumTestDemo extends TestBase {
//
//    @Test
//    public void test1() {
//        try {
//            // Wait for app to load before switching context
//            Thread.sleep(5000);
//
//            switchContext("FLUTTER");
//            logger = extent.createTest("FLUTTER Switch Context");
//            logger.log(Status.PASS, "FLUTTER Switch Context passed");
//
//            // Additional wait after context switch for Flutter to be ready
//            Thread.sleep(3000);
//
//            LoginPage loginPage = new LoginPage(driver);
//            loginPage.login("user@yopmail.com", "123456");
//
//            Thread.sleep(3000);
//            logger.log(Status.PASS, "Flutter data input & Click test passed");
//            Thread.sleep(5000);
//
//        } catch (TimeoutException | InterruptedException e) {
//            logger.log(Status.FAIL, "Flutter data input & Click test Failed");
//            logger.log(Status.FAIL, e);
//        }
//    }
//}

package tests;

import Util.TestBase;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.Test;
import pages.LoginPage;

public class AppiumTestDemo extends TestBase {

    @Test
    public void test1() {
        try {
            // Wait for app to fully load before switching context
            Thread.sleep(5000);

            switchContext("FLUTTER");
            logger = extent.createTest("FLUTTER Switch Context");
            logger.log(Status.PASS, "FLUTTER Switch Context passed");

            // Uncomment this line to print all widget keys in your app
            // System.out.println("PAGE SOURCE: " + driver.getPageSource());

            Thread.sleep(2000);

            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("user@yopmail.com", "123456");

            Thread.sleep(3000);
            logger.log(Status.PASS, "Flutter data input & Click test passed");
            Thread.sleep(3000);

        } catch (TimeoutException | InterruptedException e) {
            logger.log(Status.FAIL, "Flutter data input & Click test Failed");
            logger.log(Status.FAIL, e);
        }
    }
}