package agh.cs.project;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Turns {
    //kill&&move
    public void move(LinkedList<Animal> animals){
        for(Animal animal: animals)
            animal.move();
    }

    //eat
    public void eat(LinkedList<Animal> animals, HashMap<Vector2d, LinkedList<IMapElement>> map){
        for(Animal animal: animals){
            LinkedList<IMapElement> tmp=map.get(animal.getPosition());
            if(tmp.getFirst() instanceof Weeds){
                tmp.removeFirst();
                LinkedList<Animal> strongest = new LinkedList<Animal>();
                int most=0;
                for(IMapElement element: tmp){
                    if(((Animal) element).energy>most){
                        most=((Animal) element).energy;
                        strongest.clear();
                        strongest.add((Animal) element);
                    }
                    else if(((Animal) element).energy == most){
                        strongest.add((Animal) element);
                    }
                }
                for(Animal eater: strongest){
                    eater.energy+=Weeds.energy/strongest.size();
                }
            }
        }
    }

    //breed
    public void breed(LinkedList<Animal> animals, HashMap<Vector2d, LinkedList<IMapElement>> map, LoopedMap MAP){
        boolean[][] bred = new boolean[JsonReader.width][JsonReader.height];
        for(boolean[] row : bred){
            Arrays.fill(row, false);
        }
        for(Animal animal: animals){
            if(!bred[animal.getPosition().x][animal.getPosition().y]) {
                bred[animal.getPosition().x][animal.getPosition().y] = true;
                LinkedList<IMapElement> tmp;
                tmp = map.get(animal.position);
                if (tmp.size() > 1) {
                    Animal A=(Animal)tmp.get(0);
                    Animal B=(Animal)tmp.get(1);
                    if(A.energy<B.energy){
                        Animal c=A;
                        A=B;
                        B=c;
                    }
                    for(IMapElement element: tmp){
                        if(((Animal)element).equals(A) || ((Animal)element).equals(B)) continue;
                        if(B.energy<((Animal) element).energy)
                            B=(Animal) element;
                        if(A.energy<B.energy)
                        {
                            Animal c=A;
                            A=B;
                            B=c;
                        }
                    }
                    if(B.energy>=Animal.threshold){
                        Vector2d pos=A.position.randAround();
                        for(int i=0; i<20; i++){
                            if(!MAP.isOccupied(pos)) break;
                            pos=A.position.randAround();
                        }
                        Animal bby = new Animal(pos, A.genes.mutate(B.genes), B.energy/4+A.energy/4, MAP);
                        MAP.placeAnimal(bby);
                        A.energy=A.energy*3/4;
                        B.energy=B.energy*3/4;
                    }
                }
            }
        }
    }
    //plants

    public void weeds(LoopedMap map){
        for(int i=0; i<map.upperRight.x*map.upperRight.y/10; i++){
            if(map.placeWeed(new Weeds(map.lowerLeft.randOutside(map.upperRight, map.lowerLeftJungle, map.upperRightJungle)))) break;
        }
        for(int i=0; i<map.upperRightJungle.x*map.upperRightJungle.y/10; i++){
            if(map.placeWeed(new Weeds(map.lowerLeftJungle.randInRange(map.upperRightJungle)))) break;
        }
    }

    public void turn(LinkedList<Animal> animals, HashMap<Vector2d, LinkedList<IMapElement>> hashmap, LoopedMap map){
        move(animals);
        eat(animals, hashmap);
        breed(animals, hashmap, map);
        weeds(map);
    }
}
