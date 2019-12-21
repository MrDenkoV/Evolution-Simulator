package agh.cs.project;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class World {
    public static void main(String[] args){
        System.out.println("henlo");
        try{
            Json.readJSON();
            LoopedMap leftMap = new LoopedMap(Json.width, Json.height, Json.jungleRatio);
            LoopedMap rightMap = new LoopedMap(Json.width, Json.height, Json.jungleRatio);
            Weeds.energy= Json.plantEnergy;
            Animal.threshold= Json.startEnergy/2;
            Animal.moveEnergy= Json.moveEnergy;

            for(int i = 0; i< Json.animals; i++){
                Animal.generateAnimal(leftMap);
                Animal.generateAnimal(rightMap);
            }
            leftMap.setStatistics();
            rightMap.setStatistics();

//            System.out.println(map.toString());
//            for(int i = 0; i< Json.animals; i++)
//                Animal.generateAnimal(map);
//            System.out.println('?');
//            System.out.println(map.toString());

            /*for(Animal animal: map.animals){
                System.out.println(animal.position.toString()+' '+String.valueOf(animal.energy)+' '+String.valueOf(animal.direction));
                System.out.println(animal.genes);
            }*/

            /*for(int i=0; i<10; i++)
            {
                map.turn();
                System.out.println("Epoch: " + String.valueOf(i));
                System.out.println(map.toString());

            }*/
            //System.out.println(map.lowerLeftJungle);
            //System.out.println(map.upperRightJungle);
            /*WIZUALIZACJA*/
            JFrame obj = new JFrame("Simulation");
            Visualisation vis = new Visualisation(leftMap, rightMap);

            obj.setBounds(50, 50, 2*905, 800);
            obj.setBackground(Color.DARK_GRAY);
            obj.setResizable(false);
            obj.setVisible(true);
            obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            obj.add(vis);

            while(true){
                while(!Visualisation.drawn)
                    Thread.sleep(5);
                if(!Visualisation.paused) {
                    leftMap.turn();
                    rightMap.turn();
                }
                Visualisation.drawn=false;
                obj.repaint();
                Thread.sleep(50);
            }
        }
        catch (IOException E){
            System.out.println("IO Error - File? Path?");
        } catch (Exception e) {
            System.out.println(e + "Exception");
        }
    }
}
