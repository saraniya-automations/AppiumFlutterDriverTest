package appium_flutter_driver;

import appium_flutter_driver.finder.FlutterElement;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.LocalFileDetector;
import io.appium.java_client.AppiumDriver;

import java.util.LinkedHashMap;

public class FlutterFinder {

    private final AppiumDriver driver;
    private final FileDetector fileDetector;

    public FlutterFinder(AppiumDriver driver) {
        this.driver = driver;
        this.fileDetector = new LocalFileDetector();
    }

    public FlutterElement byValueKey(String key) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("finderType", "ByValueKey");
        map.put("keyValueType", "String");
        map.put("keyValueString", key);
        FlutterElement element = new FlutterElement();
        element.setId(encodeFinderToJson(map));
        element.setParent(driver);
        element.setFileDetector(fileDetector);
        return element;
    }

    public FlutterElement byValueKey(int key) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("finderType", "ByValueKey");
        map.put("keyValueType", "int");
        map.put("keyValueString", String.valueOf(key));
        FlutterElement element = new FlutterElement();
        element.setId(encodeFinderToJson(map));
        element.setParent(driver);
        element.setFileDetector(fileDetector);
        return element;
    }

    // public FlutterElement byText(String text) {
    //     LinkedHashMap<String, String> map = new LinkedHashMap<>();
    //     map.put("finderType", "ByText");
    //     map.put("keyValueString", text);
    //     FlutterElement element = new FlutterElement();
    //     element.setId(encodeFinderToJson(map));
    //     element.setParent(driver);
    //     element.setFileDetector(fileDetector);
    //     return element;
    // }

    public FlutterElement byText(String text) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("finderType", "ByText");
        map.put("text", text);  // ✅ was "keyValueString", should be "text"
        FlutterElement element = new FlutterElement();
        element.setId(encodeFinderToJson(map));
        element.setParent(driver);
        element.setFileDetector(fileDetector);
        return element;
    }

    public FlutterElement byTooltip(String tooltipText) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("finderType", "ByTooltipMessage");
        map.put("keyValueString", tooltipText);
        FlutterElement element = new FlutterElement();
        element.setId(encodeFinderToJson(map));
        element.setParent(driver);
        element.setFileDetector(fileDetector);
        return element;
    }

    public FlutterElement byType(String type) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("finderType", "ByType");
        map.put("keyValueString", type);
        FlutterElement element = new FlutterElement();
        element.setId(encodeFinderToJson(map));
        element.setParent(driver);
        element.setFileDetector(fileDetector);
        return element;
    }

    public FlutterElement bySemanticsLabel(String label) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("finderType", "BySemanticsLabel");
        map.put("keyValueString", label);
        FlutterElement element = new FlutterElement();
        element.setId(encodeFinderToJson(map));
        element.setParent(driver);
        element.setFileDetector(fileDetector);
        return element;
    }

    private String encodeFinderToJson(LinkedHashMap<String, String> finderMap) {
        StringBuilder json = new StringBuilder("{");
        finderMap.forEach((k, v) ->
                json.append("\"").append(k).append("\":\"").append(v).append("\",")
        );
        if (json.charAt(json.length() - 1) == ',') {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("}");
        // return java.util.Base64.getEncoder().encodeToString(json.toString().getBytes());
        return java.util.Base64.getEncoder().encodeToString(json.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    public FlutterElement byAncestor(FlutterElement of, FlutterElement matching) {
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    map.put("finderType", "Ancestor");
    map.put("of", of.getId());
    map.put("matching", matching.getId());
    FlutterElement element = new FlutterElement();
    element.setId(encodeFinderToJson(map));
    element.setParent(driver);
    element.setFileDetector(fileDetector);
    return element;
}
}