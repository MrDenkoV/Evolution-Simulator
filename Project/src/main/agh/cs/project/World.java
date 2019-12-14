package agh.cs.project;

import java.io.IOException;

public class World {
    public static void main(String[] args){
        System.out.println("henlo");
        try{
            JsonReader.readJSON();
            LoopedMap map = new LoopedMap(JsonReader.width, JsonReader.height, JsonReader.jungleRatio);
            Weeds.energy=JsonReader.plantEnergy;
            Animal.threshold=JsonReader.startEnergy/2;
            Animal.moveEnergy=JsonReader.moveEnergy;
//            System.out.println(map.toString());

        }
        catch (IOException E){
            System.out.println("IO Error - File? Path?");
        } catch (Exception e) {
            System.out.println(e + "Exception");
        }
    }
}
