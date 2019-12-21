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
    protected Random generator = new Random();
    protected LinkedList<Animal> animals = new LinkedList<>();
    protected Map<Vector2d, LinkedList<IMapElement> > elements = new HashMap<>();
    protected FollowAnimal following;
    protected boolean isFollowed=false;
    protected Statistics statistics=new Statistics();
    protected long weedsCount=0;


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
        this.weedsCount++;
        return true;
    }

    @Override
    public void placeAnimal(Animal animal){
        LinkedList<IMapElement> elements;
        if(isOccupied(animal.getPosition())) {
            elements = this.elements.get(animal.getPosition());
            if(elements.getFirst() instanceof Weeds) {
                elements.clear();
                this.weedsCount--;
            }
        }
        else
            elements = new LinkedList<>();
        elements.add(animal);
        this.elements.put(animal.getPosition(), elements);
        animal.addObserver(this);
        this.animals.add(animal);
        this.statistics.addGenotype(animal);
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

    public void setStatistics(){
        Turns.setStatistics(this);
    }

    public boolean isClicked(int x, int y){
        LinkedList<IMapElement> atPosition = this.elements.get(new Vector2d(x, y));
        if(atPosition==null || atPosition.size()==0 || atPosition.getFirst() instanceof Weeds)
            return false;
        Animal strongest=(Animal)atPosition.getFirst();
        for(IMapElement element: atPosition){
            Animal animal = (Animal) element;
            if(strongest.getEnergy()<animal.getEnergy())
                strongest=animal;
        }
        startFollowing(strongest);
        return true;
    }

    public void startFollowing(Animal animal){
        this.isFollowed=true;
        this.following=new FollowAnimal(animal, this.statistics.getEpochs());
    }

    public void resetFollowing(){
        this.isFollowed=false;
        this.following.resetFollowing(this.animals);
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
            this.statistics.removeGenotype(animal);
            this.statistics.setCurrentAvgLife(animal.getLife());
            if(this.isFollowed && this.following.getParent().equals(animal))
                this.following.setDeathEpoch(this.statistics.getEpochs());
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
