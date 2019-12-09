package agh.cs.project;

public class Animal implements IMapElement{
    Vector2d position;
    Instruction[] instructions;
    MapDirection direction;

    public Animal(Vector2d position, Instruction[] instructs){
        this.position=position;
        this.instructions=instructs;
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

    public Instruction[] getInstructions(){
        return this.instructions;
    }

}
