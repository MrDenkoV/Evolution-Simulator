package agh.cs.project;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class LoopedMap implements IWorldMap, IPositionChangeObserver{
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected Vector2d lowerLeftJungle;
    protected Vector2d upperRightJungle;
    protected static Random generator = new Random();
    protected LinkedList<Animal> animals = new LinkedList<>();
    protected Map<Vector2d, LinkedList<IMapElement> > elements = new HashMap<>();


    public LoopedMap(int width, int height, double jungleRatio){
        lowerLeft=new Vector2d(0,0);
        upperRight=new Vector2d(width,height);

        lowerLeftJungle=new Vector2d(width/2-(int)(width*jungleRatio)/2, height/2-(int)(height*jungleRatio)/2);
        upperRightJungle=new Vector2d(width/2+(int)(width*jungleRatio)/2,height/2+(int)(height*jungleRatio)/2);
    }

    @Override
    public String toString() {
        return new MapVisualizer(this).draw(lowerLeft, upperRight, lowerLeftJungle, upperRightJungle);
    }

    @Override
    public boolean placeWeed(Weeds weed) {
        if (isOccupied(weed.getPosition()))
            return false;
        LinkedList<IMapElement> elements = new LinkedList<>();
        elements.add(weed);
        this.elements.put(weed.getPosition(), elements);
        return true;
    }

    @Override
    public void placeAnimal(Animal animal){
        LinkedList<IMapElement> elements;
        if(isOccupied(animal.getPosition())) {
            elements = this.elements.get(animal.getPosition());
            if(elements.getFirst() instanceof Weeds)
                elements.clear();
        }
        else
            elements = new LinkedList<>();
        elements.add(animal);
        this.elements.put(animal.getPosition(), elements);
        animal.addObserver(this);
        this.animals.add(animal);
    }

    public void turn() {
//        kill&&move
//        Turns.move(animals);
//        eat
//        Turns.eat(animals,elements);
//        breed
//        Turns.breed(animals, elements, this);
//        plants
//        Turns.weeds(this);
        Turns.turn(animals, elements, this);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.objectsAt(position)!=null && this.objectsAt(position).size()!=0;
    }

    @Override
    public LinkedList<IMapElement> objectsAt(Vector2d position) {
        return this.elements.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        this.elements.get(oldPosition).remove(animal);
        if(newPosition.equals(new Vector2d(-1,-1))) {
            this.animals.remove(animal);
        }
        else {
            if (elements.get(newPosition) == null) {
                LinkedList<IMapElement> tmp = new LinkedList<>();
                this.elements.put(newPosition, tmp);
            }
            this.elements.get(newPosition).add(animal);
        }
    }
}
