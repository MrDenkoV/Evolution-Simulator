package agh.cs.project;

import java.util.LinkedList;

public interface IWorldMap {

    boolean placeWeed(Weeds weed);

    void placeAnimal(Animal animal);

    boolean isOccupied(Vector2d position);

    LinkedList objectsAt(Vector2d position);
}
