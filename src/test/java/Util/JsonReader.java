package util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonReader {

    private static JsonObject jsonData;

    static {
        try {
            InputStream is = JsonReader.class
                    .getClassLoader()
                    .getResourceAsStream("testData/testData.json");

            if (is == null) {
                throw new RuntimeException("testData.json file not found");
            }

            String json = IOUtils.toString(is, StandardCharsets.UTF_8);
            jsonData = JsonParser.parseString(json).getAsJsonObject();

        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON test data", e);
        }
    }

    // Get value by key (optional)
    public static String getTestData(String key) {
        JsonElement element = jsonData.get(key);
        return element != null ? element.getAsString() : null;
    }

    // ✅ Add this public method to get the full JSON object (needed for array-based tests)
    public static JsonObject getJsonData() {
        return jsonData;
    }

    // ✅ Optional helper to get array from JSON
    public static JsonArray getTestDataArray(String key) {
        JsonElement element = jsonData.get(key);
        return element != null && element.isJsonArray() ? element.getAsJsonArray() : null;
    }
}