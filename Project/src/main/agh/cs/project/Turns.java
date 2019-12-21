package agh.cs.project;

import java.util.*;

public class Turns {
    //kill&&move
    private static void move(LinkedList<Animal> animals){
        ArrayList<Animal> animals1 = new ArrayList<>(animals);
        if(Json.debug) System.out.println("BeforeMove "+animals1.size());
        for(Animal animal: animals1)
        {
            animal.move();
            if(Json.debug) System.out.println(animal.getPosition());
        }
        if(Json.debug)
            System.out.println("Move");
    }

    //eat
    private static void eat(LinkedList<Animal> animals, Map<Vector2d, LinkedList<IMapElement>> hashMap, LoopedMap map){
        for(Animal animal: animals){
            LinkedList<IMapElement> tmp=hashMap.get(animal.getPosition());
            if(tmp.getFirst() instanceof Weeds){
                map.weedsCount--;
                tmp.removeFirst();
                LinkedList<Animal> strongest = new LinkedList<>();
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
        if(Json.debug) System.out.println("Eat");
    }

    //breed
    private static void breed(LinkedList<Animal> animals, Map<Vector2d, LinkedList<IMapElement>> hashMap, LoopedMap map){
        boolean[][] bred = new boolean[Json.width+1][Json.height+1];
        for(boolean[] row : bred){
            Arrays.fill(row, false);
        }
        LinkedList<Animal> children = new LinkedList<>();
        Set<Vector2d> kidsPositions = new HashSet<>();

        for(Animal animal: animals){
            if(!bred[animal.getPosition().x][animal.getPosition().y]) {
                bred[animal.getPosition().x][animal.getPosition().y] = true;
                LinkedList<IMapElement> animalsOnPosition;
                animalsOnPosition = hashMap.get(animal.getPosition());
                if (animalsOnPosition.size() > 1) {
                    Animal strongestAnimal=(Animal)animalsOnPosition.get(0);
                    Animal secondStrongestAnimal=(Animal)animalsOnPosition.get(1);
                    if(strongestAnimal.energy<secondStrongestAnimal.energy){
                        Animal tmp=strongestAnimal;
                        strongestAnimal=secondStrongestAnimal;
                        secondStrongestAnimal=tmp;
                    }
                    for(IMapElement element: animalsOnPosition){
                        if(((Animal)element).equals(strongestAnimal) || ((Animal)element).equals(secondStrongestAnimal)) continue;
                        if(secondStrongestAnimal.energy<((Animal) element).energy)
                            secondStrongestAnimal=(Animal) element;
                        if(strongestAnimal.energy<secondStrongestAnimal.energy)
                        {
                            Animal tmp=strongestAnimal;
                            strongestAnimal=secondStrongestAnimal;
                            secondStrongestAnimal=tmp;
                        }
                    }
                    if(secondStrongestAnimal.energy>=Animal.threshold){
                        Vector2d pos=strongestAnimal.getPosition().randAround(map);
                        for(int i=0; i<30; i++){
                            if(!map.isOccupied(pos) && !kidsPositions.contains(pos)) break;
                            pos=strongestAnimal.getPosition().randAround(map);
                        }

                        Animal bby;
                        if(strongestAnimal.isDescendant||secondStrongestAnimal.isDescendant){
                            bby = new Animal(pos, strongestAnimal.getGenes().mutate(secondStrongestAnimal.getGenes(), map), secondStrongestAnimal.energy/4+strongestAnimal.energy/4, map, true);
                            if(strongestAnimal.equals(map.following.getParent())||secondStrongestAnimal.equals(map.following.getParent()))
                                map.following.addKid();
                            else
                                map.following.addDescendant();
                        }
                        else
                            bby = new Animal(pos, strongestAnimal.getGenes().mutate(secondStrongestAnimal.getGenes(), map), secondStrongestAnimal.energy/4+strongestAnimal.energy/4, map);

                        strongestAnimal.addKid();
                        secondStrongestAnimal.addKid();
                        children.add(bby);
                        kidsPositions.add(pos);
//                        map.placeAnimal(bby);
                        strongestAnimal.energy=strongestAnimal.energy*3/4;
                        secondStrongestAnimal.energy=secondStrongestAnimal.energy*3/4;
                    }
                }
            }
        }
        for(Animal bby: children)
            map.placeAnimal(bby);
        if(Json.debug) System.out.println("breed");
    }

    //plants
    private static void weeds(LoopedMap map){
        for(int i=0; i<map.upperRight.x*map.upperRight.y/4+10; i++){
            if(map.placeWeed(new Weeds(map.lowerLeft.randOutside(map.upperRight, map.lowerLeftJungle, map.upperRightJungle, map)))) break;
        }
        if(Json.debug) System.out.println("weed");
        for(int i=0; i<map.upperRightJungle.x*map.upperRightJungle.y/4+10; i++){
            if(map.placeWeed(new Weeds(map.lowerLeftJungle.randInRange(map.upperRightJungle, map)))) break;
        }
        if(Json.debug) System.out.println("plant");
    }

    public static void turn(LinkedList<Animal> animals, Map<Vector2d, LinkedList<IMapElement>> hashmap, LoopedMap map){
        move(animals);
        eat(animals, hashmap, map);
        breed(animals, hashmap, map);
        weeds(map);
        setStatistics(map);
//        System.out.println(animals.size());
    }

    public static void setStatistics(LoopedMap map){
        LinkedList<Animal> animals=map.animals;

        map.statistics.epochsIncrement();
        map.statistics.setCurrentAnimalCount(animals.size());
        map.statistics.setCurrentWeedsCount(map.weedsCount);

        double energy=0;
        double life=0;
        double kids=0;
        double danimals=(double) animals.size();
        Genes genotype;
//        if(genotype)
        for(Animal animal: animals){
            kids+=animal.getKids();
//            life+=animal.getLife();
            energy+=animal.getEnergy();
            animal.incrementAge();
        }
        if(animals.size()>0) {
            map.statistics.setCurrentAvgEnergy(energy / danimals);
            map.statistics.setCurrentKidsCount(kids / danimals);
//            map.statistics.setCurrentAvgLife(life / danimals);
        }

    }
}
