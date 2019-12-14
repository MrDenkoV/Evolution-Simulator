package agh.cs.project;

public class Weeds implements IMapElement{
    protected Vector2d position;
    protected static int energy;

    public Weeds(Vector2d position){
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String toString(){
        return "W";
    }
}
