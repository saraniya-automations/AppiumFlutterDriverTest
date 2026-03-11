//package pages;
//
//import appium_flutter_driver.finder.FlutterElement;
//import io.appium.java_client.AppiumDriver;
//
//public class LoginPage extends BasePage {
//
//    public LoginPage(AppiumDriver driver) {
//        super(driver);
//    }
//
//    // Locators
//    private FlutterElement usernameField() {
//        return finder.byValueKey("txt_username");
//    }
//
//    private FlutterElement passwordField() {
//        return finder.byValueKey("txt_password");
//    }
//
//    private FlutterElement loginButton() {
//        return finder.byValueKey("button_login");
//    }
//
//    // Actions - using flutter:enterText instead of sendKeys (known Flutter driver requirement)
//    public void enterUsername(String username) {
//        FlutterElement field = usernameField();
//        waitForElement(field);
//        field.click(); // focus the field first
//        driver.executeScript("flutter:enterText", username);
//    }
//
//    public void enterPassword(String password) {
//        FlutterElement field = passwordField();
//        waitForElement(field);
//        field.click(); // focus the field first
//        driver.executeScript("flutter:enterText", password);
//    }
//
//    public void clickLogin() {
//        click(loginButton());
//    }
//
//    // Combined action
//    public void login(String username, String password) {
//        enterUsername(username);
//        enterPassword(password);
//        clickLogin();
//    }
//}

package pages;

import appium_flutter_driver.finder.FlutterElement;
import io.appium.java_client.AppiumDriver;

public class LoginPage extends BasePage {

    public LoginPage(AppiumDriver driver) {
        super(driver);
    }

    // Locators — these keys must match Key("...") values in your Flutter app
    private FlutterElement usernameField() {
        return finder.byValueKey("txt_username");
    }

    private FlutterElement passwordField() {
        return finder.byValueKey("txt_password");
    }

    private FlutterElement loginButton() {
        return finder.byValueKey("button_login");
    }

    // Actions
    public void enterUsername(String username) {
        type(usernameField(), username);
    }

    public void enterPassword(String password) {
        type(passwordField(), password);
    }

    public void clickLogin() {
        click(loginButton());
    }

    // Combined action
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}