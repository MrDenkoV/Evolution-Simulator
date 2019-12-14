package agh.cs.project;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

//https://www.codevoila.com/post/65/java-json-tutorial-and-example-json-java-orgjson?fbclid=IwAR1i5kAna6ma_QsUup5YfBNZDARIfYrevKFUN7N-qtLsGRzQe6JzW3TpYCc

public class JsonReader {
    public static int width;
    public static int height;
    public static int startEnergy;
    public static int moveEnergy;
    public static int plantEnergy;
    public static double jungleRatio;

    public static void readJSON() throws Exception {
        File file = new File("./parameters.json");
        String content = FileUtils.readFileToString(file, "utf-8");

        // Convert JSON string to JSONObject
        JSONObject JsonObject = new JSONObject(content);

        JsonReader.width = JsonObject.getInt("width");
        JsonReader.height = JsonObject.getInt("height");
        JsonReader.startEnergy = JsonObject.getInt("startEnergy");
        JsonReader.moveEnergy = JsonObject.getInt("moveEnergy");
        JsonReader.plantEnergy = JsonObject.getInt("plantEnergy");
        JsonReader.jungleRatio = JsonObject.getDouble("jungleRatio");
    }

}
