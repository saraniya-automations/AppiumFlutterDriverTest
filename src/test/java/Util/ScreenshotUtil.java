package util;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String takeScreenshot(AppiumDriver driver, String name) {
        try {
            File srcFile = driver.getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String path = System.getProperty("user.dir") + "/screenshots/" + name + "_" + timestamp + ".png";
            FileUtils.copyFile(srcFile, new File(path));
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}