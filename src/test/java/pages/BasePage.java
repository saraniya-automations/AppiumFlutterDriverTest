package pages;

import appium_flutter_driver.FlutterFinder;
import appium_flutter_driver.finder.FlutterElement;
// import io.github.ashwith.flutter.FlutterFinder;
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
        driver.executeScript("flutter:waitFor", element, 30000L);
    }

    protected void click(FlutterElement element) {
        // waitForElement(element);
        element.click();
    }

    protected void type(FlutterElement element, String text) {
        // waitForElement(element);
        element.click(); // focus the field first
        element.sendKeys(text);
    }

    protected String getText(FlutterElement element) {
        waitForElement(element);
        return element.getText();
    }

    protected boolean isElementPresent(FlutterElement element) {
        try {
            waitForElement(element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}