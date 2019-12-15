package agh.cs.project;

import javax.swing.*;
import java.awt.*;
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
            System.out.println(map.toString());
            for(int i=0; i<JsonReader.animals; i++)
                Animal.generateAnimal(map);
            System.out.println('?');
            System.out.println(map.toString());

            for(int i=0; i<100; i++)
            {
                map.turn();
                System.out.println("Epoch: " + String.valueOf(i));
                System.out.println(map.toString());

            }

            /*WIZUALIZACJA*/
            /*JFrame obj = new JFrame();
            Visualisation vis = new Visualisation();

            obj.setBounds(100, 50, 905, 800);
            obj.setBackground(Color.DARK_GRAY);
            obj.setResizable(false);
            obj.setVisible(true);
            obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            obj.add(vis);*/

        }
        catch (IOException E){
            System.out.println("IO Error - File? Path?");
        } catch (Exception e) {
            System.out.println(e + "Exception");
        }
    }
}
