package driver;

import io.appium.java_client.AppiumDriver;

public class DriverManager {

    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    // Set driver
    public static void setDriver(AppiumDriver driverInstance) {
        driver.set(driverInstance);
    }

    // Get driver
    public static AppiumDriver getDriver() {
        return driver.get();
    }

    // Quit driver
    public static void quitDriver() {

        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}