package Util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class JsonReader {
    public static JsonObject getJsonData() throws IOException, ParseException{
        File fileName = new File("testData//testData.json");
        String json = FileUtils.readFileToString(fileName,"UTF-8");
        Object obj = new JsonParser().parse(json);
        JsonObject jsonObject = (JsonObject) obj;
        return jsonObject;
    }
}
