//package pages;
//
//import appium_flutter_driver.finder.FlutterElement;
//import appium_flutter_driver.FlutterFinder;
//import io.appium.java_client.AppiumDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//public class BasePage {
//
//    protected AppiumDriver driver;
//    protected FlutterFinder finder;
//    protected WebDriverWait wait;
//
//    public BasePage(AppiumDriver driver) {
//        this.driver = driver;
//        this.finder = new FlutterFinder(driver);
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//    }
//
//    protected void waitForElement(FlutterElement element) {
//        wait.until(d -> element.isDisplayed());
//    }
//
//    protected void click(FlutterElement element) {
//        waitForElement(element);
//        element.click();
//    }
//
//    protected void type(FlutterElement element, String text) {
//        waitForElement(element);
//        element.sendKeys(text);
//    }
//
//    protected String getText(FlutterElement element) {
//        waitForElement(element);
//        return element.getText();
//    }
//}
package pages;

import appium_flutter_driver.FlutterFinder;
import appium_flutter_driver.finder.FlutterElement;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected AppiumDriver driver;
    protected FlutterFinder finder;
    protected WebDriverWait wait;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.finder = new FlutterFinder(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void waitForElement(FlutterElement element) {
        // flutter:waitFor tells the Flutter driver to wait until the widget is visible
        driver.executeScript("flutter:waitFor", element, 30000);
    }

    protected void click(FlutterElement element) {
        waitForElement(element);
        element.click();
    }

    protected void type(FlutterElement element, String text) {
        waitForElement(element);
        element.click();       // focus the field first
        element.sendKeys(text);
    }

    protected String getText(FlutterElement element) {
        waitForElement(element);
        return element.getText();
    }
}