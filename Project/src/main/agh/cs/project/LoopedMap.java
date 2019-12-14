package agh.cs.project;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class LoopedMap implements IWorldMap{
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected Vector2d lowerLeftJungle;
    protected Vector2d upperRightJungle;
    protected static Random generator = new Random();
    protected LinkedList<Animal> animals = new LinkedList<Animal>();
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
    public boolean place(IMapElement element) {
        return false;
    }

    @Override
    public void run(Instruction[] genotype) {
        //generator.nextint(32);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public LinkedList<IMapElement> objectsAt(Vector2d position) {
        return null;
    }
}
