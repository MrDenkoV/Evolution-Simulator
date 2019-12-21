package agh.cs.project;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class Json {
    public static int width;
    public static int height;
    public static int startEnergy;
    public static int moveEnergy;
    public static int plantEnergy;
    public static double jungleRatio;
    public static int animals;

    public static void readJSON() throws Exception {
        File file = new File("./parameters.json");
//        File file = new File("Project/parameters.json");
        String content = FileUtils.readFileToString(file, "utf-8");

        // Convert JSON string to JSONObject
        JSONObject JsonObject = new JSONObject(content);

        Json.width = JsonObject.getInt("width");
        Json.height = JsonObject.getInt("height");
        Json.startEnergy = JsonObject.getInt("startEnergy");
        Json.moveEnergy = JsonObject.getInt("moveEnergy");
        Json.plantEnergy = JsonObject.getInt("plantEnergy");
        Json.jungleRatio = JsonObject.getDouble("jungleRatio");
        Json.animals = JsonObject.getInt("startingAnimals");
    }

    public static void writeJSON(String side, long epochs, double averageAnimals, double averageWeeds, Genes genotype, double averageEnergy, double averageLife, double averageKids) throws Exception{
        JSONObject JsonObj = new JSONObject();
        JsonObj.put("averageAnimals: ", averageAnimals);
        JsonObj.put("averageWeeds", averageWeeds);
        JsonObj.put("dominatingGenotype", genotype);
        JsonObj.put("averageEnergy", averageEnergy);
        JsonObj.put("averageEnergy", averageEnergy);
        JsonObj.put("averageLife", averageLife);
        JsonObj.put("averageKids", averageKids);

        String name=side+"Epochs"+String.valueOf(epochs)+".json";

        File file = new File(name);
        FileUtils.writeStringToFile(file, JsonObj.toString(4), "utf-8");
    }

}
