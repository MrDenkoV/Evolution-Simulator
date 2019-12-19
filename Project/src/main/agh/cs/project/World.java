package agh.cs.project;

import java.io.IOException;

public class World {
    public static void main(String[] args){
        System.out.println("henlo");
        try{
            Json.readJSON();
            LoopedMap map = new LoopedMap(Json.width, Json.height, Json.jungleRatio);
            Weeds.energy= Json.plantEnergy;
            Animal.threshold= Json.startEnergy/2;
            Animal.moveEnergy= Json.moveEnergy;

            System.out.println(map.toString());
            for(int i = 0; i< Json.animals; i++)
                Animal.generateAnimal(map);
            System.out.println('?');
            System.out.println(map.toString());

            /*for(Animal animal: map.animals){
                System.out.println(animal.position.toString()+' '+String.valueOf(animal.energy)+' '+String.valueOf(animal.direction));
                System.out.println(animal.genes);
            }*/

            for(int i=0; i<100; i++)
            {
                map.turn();
                System.out.println("Epoch: " + String.valueOf(i));
                System.out.println(map.toString());

            }
            //System.out.println(map.lowerLeftJungle);
            //System.out.println(map.upperRightJungle);
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
