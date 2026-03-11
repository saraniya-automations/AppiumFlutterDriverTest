//package appium_flutter_driver.finder;
//
//import io.appium.java_client.AppiumDriver;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Dimension;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.Point;
//import org.openqa.selenium.Rectangle;
//import org.openqa.selenium.WebDriverException;
//import org.openqa.selenium.WebElement;
//
//import java.util.List;
//
//public class FlutterElement implements WebElement {
//
//    private final AppiumDriver driver;
//    private final By locator;
//    private final String keyValue;
//    private final String finderType;
//
//    public FlutterElement(AppiumDriver driver, By locator, String keyValue, String finderType) {
//        this.driver = driver;
//        this.locator = locator;
//        this.keyValue = keyValue;
//        this.finderType = finderType;
//    }
//
//    private WebElement getElement() {
//        return driver.findElement(locator);
//    }
//
//    @Override
//    public void click() {
//        getElement().click();
//    }
//
//    @Override
//    public void sendKeys(CharSequence... keysToSend) {
//        WebElement element = getElement();
//        element.click(); // tap to focus first
//        element.sendKeys(keysToSend);
//    }
//
//    @Override
//    public String getText() {
//        return getElement().getText();
//    }
//
//    @Override
//    public boolean isDisplayed() {
//        try {
//            return getElement().isDisplayed();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    @Override
//    public boolean isEnabled() {
//        try {
//            return getElement().isEnabled();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    @Override
//    public boolean isSelected() {
//        return getElement().isSelected();
//    }
//
//    @Override
//    public void clear() {
//        getElement().clear();
//    }
//
//    @Override
//    public String getTagName() {
//        return getElement().getTagName();
//    }
//
//    @Override
//    public String getAttribute(String name) {
//        return getElement().getAttribute(name);
//    }
//
//    @Override
//    public Point getLocation() {
//        return getElement().getLocation();
//    }
//
//    @Override
//    public Dimension getSize() {
//        return getElement().getSize();
//    }
//
//    @Override
//    public Rectangle getRect() {
//        return getElement().getRect();
//    }
//
//    @Override
//    public String getCssValue(String propertyName) {
//        return getElement().getCssValue(propertyName);
//    }
//
//    @Override
//    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
//        return getElement().getScreenshotAs(target);
//    }
//
//    @Override
//    public WebElement findElement(By by) {
//        return getElement().findElement(by);
//    }
//
//    @Override
//    public List<WebElement> findElements(By by) {
//        return getElement().findElements(by);
//    }
//
//    @Override
//    public void submit() {
//        getElement().submit();
//    }
//}

package appium_flutter_driver.finder;

import org.openqa.selenium.remote.RemoteWebElement;

/**
 * FlutterElement extends MobileElement.
 * This gives us click(), sendKeys(), getText() etc for free.
 * The element ID is set as a Base64-encoded JSON string by the Flutter driver
 * when setParent(driver) is called — this is the key insight from Pooja's library.
 */
public class FlutterElement extends RemoteWebElement {
    // MobileElement already has everything we need:
    // click(), sendKeys(), getText(), isDisplayed(), clear() etc.
    // No extra code needed — extending it is enough.
}