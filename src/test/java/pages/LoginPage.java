package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumBy;
import appium_flutter_driver.finder.FlutterElement;
import org.openqa.selenium.WebElement;

// ✅ AFTER — initialize fields after super(driver)
public class LoginPage extends BasePage {

    private final FlutterElement usernameField;
    private final FlutterElement passwordField;
    private final FlutterElement loginButton;

    public LoginPage(AppiumDriver driver) {
        super(driver);  // finder is now ready
        this.usernameField = finder.byValueKey("username");
        this.passwordField = finder.byValueKey("password");
        this.loginButton   = finder.byText("ENTER");

    }

    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    public CatalogPage clickLogin() {
        click(loginButton);
        return new CatalogPage(driver);
    }

    public CatalogPage login(String username, String password) {
        enterUsername(username)
                .enterPassword(password);
        return clickLogin();
    }
}