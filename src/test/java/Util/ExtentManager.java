package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    // Prevent instantiation
    private ExtentManager() { }

    // Get ExtentReports instance (singleton)
    public static ExtentReports getExtentReports() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setDocumentTitle("Appium Test Report");
            spark.config().setReportName("Flutter App Automation Tests");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Optional system info
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Automation Tool", "Appium + Java");
        }
        return extent;
    }

    // Flush report
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}