package agh.cs.project;

import java.util.LinkedList;
import java.util.Random;

public class Animal implements IMapElement{
    Vector2d position;
    Genes genes;
    MapDirection direction;
    protected static int threshold;
    protected static int moveEnergy;
    protected int energy;
    protected static Random generator = new Random();
    protected LinkedList<IPositionChangeObserver> observers = new LinkedList<>();
    private IWorldMap map;


    public Animal(Vector2d position, Genes genes, int energy, IWorldMap map){
        this.position=position;
        this.genes=genes;
        this.energy=energy;
        this.direction = MapDirection.N;
        this.direction = direction.fromNumerical(generator.nextInt(8));
        this.map=map;
    }

    public Animal(Vector2d position, Genes genes, int energy){
        this.position=position;
        this.genes=genes;
        this.energy=energy;
        this.direction = MapDirection.N;
        this.direction = direction.fromNumerical(generator.nextInt(8));
    }

    public static void generateAnimal(LoopedMap map){
        Vector2d tmp = new Vector2d(generator.nextInt(JsonReader.width), generator.nextInt(JsonReader.height));
        while(map.isOccupied(tmp))
            tmp = new Vector2d(generator.nextInt(JsonReader.width), generator.nextInt(JsonReader.height));
        map.placeAnimal(new Animal(tmp, new Genes(), JsonReader.startEnergy));
    }

    @Override
    public String toString(){
        switch(this.direction){
            case N: return "^";
            case NE: return String.valueOf((char)8599);
            case E: return ">";
            case SE: return String.valueOf((char)8600);
            case S: return "v";
            case SW: return String.valueOf((char)8601);
            case W: return "<";
            default: return String.valueOf((char)8598);
        }
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    public int getEnergy(){ return this.energy; }

    public Genes getGenes(){ return this.genes; }

    public void move(){
        this.energy-=Animal.moveEnergy;
        if(this.energy<0)
            this.kill();
        this.direction=this.direction.rotate(genes.instructions[generator.nextInt(32)].getNumerical());
        Vector2d old=this.position;
        this.position=this.position.add(this.direction.toUnitVector());
        this.positionChange(old, this.position);
    }

    public void kill(){
        this.positionChange(this.position, new Vector2d(-1,-1));
    }

    void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }

    void positionChange(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver observer: observers)
            observer.positionChanged(oldPosition, newPosition, this);
    }
}
