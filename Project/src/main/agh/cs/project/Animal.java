package agh.cs.project;

import java.util.LinkedList;

public class Animal implements IMapElement{
    private Vector2d position;
    private Genes genes;
    MapDirection direction;
    protected static int threshold;
    protected static int moveEnergy;
    protected int energy;
    protected LinkedList<IPositionChangeObserver> observers = new LinkedList<>();
    protected boolean isDescendant;
    private LoopedMap map;
    private long kidsCount;
    private long life=0;


    public Animal(Vector2d position, Genes genes, int energy, LoopedMap map, boolean isDescendant){
        this.position=position;
        this.genes=genes;
        this.energy=energy;
        this.direction = MapDirection.N;
        this.direction = direction.fromNumerical(map.generator.nextInt(8));
        this.map=map;
        this.isDescendant=isDescendant;
        this.kidsCount=0;
    }

    public static void generateAnimal(LoopedMap map){
        Vector2d tmp = new Vector2d(map.generator.nextInt(Json.width), map.generator.nextInt(Json.height));
        while(map.isOccupied(tmp))
            tmp = new Vector2d(map.generator.nextInt(Json.width), map.generator.nextInt(Json.height));
        map.placeAnimal(new Animal(tmp, new Genes(map), Json.startEnergy, map, false));
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

    public boolean isDescendant() {
        return isDescendant;
    }

    public void resetFollowing(){ this.isDescendant=false; }

    public void addKid(){
        this.kidsCount++;
    }

    public long getKids(){
        return this.kidsCount;
    }

    public void incrementAge(){
        this.life++;
    }

    public long getLife(){
        return this.life;
    }

    public void move(){
        this.energy-=Animal.moveEnergy;
        if(this.energy<0)
            this.kill();
        else {
            this.direction = this.direction.rotate(genes.instructions[this.map.generator.nextInt(32)].getNumerical());
            Vector2d old = this.position;
            this.position = this.position.add(this.direction.toUnitVector());
            this.positionChange(old, this.position);
        }
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
