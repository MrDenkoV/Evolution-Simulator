package agh.cs.project;

import java.util.Random;

public class Animal implements IMapElement{
    Vector2d position;
    Instruction[] instructions;
    MapDirection direction;
    protected static int threshold;
    protected static int moveEnergy;
    protected int energy;
    protected static Random generator = new Random();

    public Animal(Vector2d position, Instruction[] instructs, int energy){
        this.position=position;
        this.instructions=instructs;
        this.energy=energy;
        this.direction = MapDirection.N;
        this.direction = direction.fromNumerical(generator.nextInt(9));
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

}
