//package appium_flutter_driver;
//
//import io.appium.java_client.AppiumDriver;
//import org.openqa.selenium.By;
//
//public class FlutterFinder {
//
//    private final AppiumDriver driver;
//
//    public FlutterFinder(AppiumDriver driver) {
//        this.driver = driver;
//    }
//
//    public FlutterElement byValueKey(String key) {
//        // Flutter driver uses accessibility id to match ValueKey
//        return new FlutterElement(driver, By.cssSelector("[key='" + key + "']"), key, "ByValueKey");
//    }
//
//    public FlutterElement byValueKey(int key) {
//        return new FlutterElement(driver, By.cssSelector("[key='" + key + "']"), String.valueOf(key), "ByValueKey");
//    }
//
//    public FlutterElement byText(String text) {
//        return new FlutterElement(driver, By.xpath("//*[@text='" + text + "']"), text, "ByText");
//    }
//
//    public FlutterElement byTooltip(String tooltip) {
//        return new FlutterElement(driver, By.xpath("//*[@tooltip='" + tooltip + "']"), tooltip, "ByTooltipMessage");
//    }
//
//    public FlutterElement byType(String type) {
//        return new FlutterElement(driver, By.xpath("//*[@type='" + type + "']"), type, "ByType");
//    }
//
//    public FlutterElement bySemanticsLabel(String label) {
//        return new FlutterElement(driver, By.xpath("//*[@label='" + label + "']"), label, "BySemanticsLabel");
//    }
//}



//------------------------------------
// package appium_flutter_driver;

// import appium_flutter_driver.finder.FlutterElement;
// import com.google.common.collect.ImmutableMap;
// import org.openqa.selenium.remote.FileDetector;
// import org.openqa.selenium.remote.LocalFileDetector;
// import io.appium.java_client.AppiumDriver;

// /**
//  * FlutterFinder — builds Flutter locators and returns FlutterElement instances.
//  * Pattern learned from Pooja's library (ashwithpoojary98/javaflutterfinder).
//  *
//  * How it works:
//  *  1. We create a FlutterElement with a finder map (finderType + keyValue)
//  *  2. We call setParent(driver) — this wires the element to the Appium session
//  *  3. The Flutter driver on the server encodes this map as Base64 JSON
//  *  4. Commands like click(), sendKeys() are sent using this encoded element ID
//  */
// public class FlutterFinder {

//     private final AppiumDriver driver;
//     private final FileDetector fileDetector;

//     public FlutterFinder(AppiumDriver driver) {
//         this.driver = driver;
//         this.fileDetector = new LocalFileDetector();
//     }

//     /**
//      * Find element by Flutter ValueKey (String)
//      * e.g. Key("txt_username") in your Flutter widget
//      */
//     public FlutterElement byValueKey(String key) {
//         FlutterElement element = new FlutterElement();
//         element.setId(encodeFinderToJson(ImmutableMap.of(
//                 "finderType", "ByValueKey",
//                 "keyValueType", "String",
//                 "keyValueString", key
//         )));
//         element.setParent(driver);
//         element.setFileDetector(fileDetector);
//         return element;
//     }

//     /**
//      * Find element by Flutter ValueKey (int)
//      */
//     public FlutterElement byValueKey(int key) {
//         FlutterElement element = new FlutterElement();
//         element.setId(encodeFinderToJson(ImmutableMap.of(
//                 "finderType", "ByValueKey",
//                 "keyValueType", "int",
//                 "keyValueString", String.valueOf(key)
//         )));
//         element.setParent(driver);
//         element.setFileDetector(fileDetector);
//         return element;
//     }

//     /**
//      * Find element by text content
//      * e.g. Text("Login") in your Flutter widget
//      */
//     public FlutterElement byText(String text) {
//         FlutterElement element = new FlutterElement();
//         element.setId(encodeFinderToJson(ImmutableMap.of(
//                 "finderType", "ByText",
//                 "keyValueString", text
//         )));
//         element.setParent(driver);
//         element.setFileDetector(fileDetector);
//         return element;
//     }

//     /**
//      * Find element by tooltip text
//      */
//     public FlutterElement byTooltip(String tooltipText) {
//         FlutterElement element = new FlutterElement();
//         element.setId(encodeFinderToJson(ImmutableMap.of(
//                 "finderType", "ByTooltipMessage",
//                 "keyValueString", tooltipText
//         )));
//         element.setParent(driver);
//         element.setFileDetector(fileDetector);
//         return element;
//     }

//     /**
//      * Find element by widget type name
//      */
//     public FlutterElement byType(String type) {
//         FlutterElement element = new FlutterElement();
//         element.setId(encodeFinderToJson(ImmutableMap.of(
//                 "finderType", "ByType",
//                 "keyValueString", type
//         )));
//         element.setParent(driver);
//         element.setFileDetector(fileDetector);
//         return element;
//     }

//     /**
//      * Find element by semantics label
//      */
//     public FlutterElement bySemanticsLabel(String label) {
//         FlutterElement element = new FlutterElement();
//         element.setId(encodeFinderToJson(ImmutableMap.of(
//                 "finderType", "BySemanticsLabel",
//                 "keyValueString", label
//         )));
//         element.setParent(driver);
//         element.setFileDetector(fileDetector);
//         return element;
//     }

//     /**
//      * Encodes the finder map as a Base64 JSON string.
//      * This is how the Flutter driver protocol identifies elements.
//      * The server decodes this to find the matching widget in the Dart VM.
//      */
//     private String encodeFinderToJson(ImmutableMap<String, String> finderMap) {
//         StringBuilder json = new StringBuilder("{");
//         finderMap.forEach((k, v) ->
//                 json.append("\"").append(k).append("\":\"").append(v).append("\",")
//         );
//         // Remove trailing comma and close
//         if (json.charAt(json.length() - 1) == ',') {
//             json.deleteCharAt(json.length() - 1);
//         }
//         json.append("}");
//         return java.util.Base64.getEncoder().encodeToString(json.toString().getBytes());
//     }
    
// }




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