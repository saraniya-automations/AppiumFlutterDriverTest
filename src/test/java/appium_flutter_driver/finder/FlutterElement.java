package appium_flutter_driver.finder;

import org.openqa.selenium.remote.RemoteWebElement;

/**
 * FlutterElement extends MobileElement.
 * This gives us click(), sendKeys(), getText() etc for free.
 * The element ID is set as a Base64-encoded JSON string by the Flutter driver
 * when setParent(driver) is called — this is the key insight from Pooja's
 * library.
 */
public class FlutterElement extends RemoteWebElement {
    // MobileElement already has everything we need:
    // click(), sendKeys(), getText(), isDisplayed(), clear() etc.
    // No extra code needed — extending it is enough.
}