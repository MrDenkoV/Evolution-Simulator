package agh.cs.project;

public class LoopedMap implements IWorldMap{
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected Vector2d lowerLeftJungle;
    protected Vector2d upperRightJungle;

    public LoopedMap(){
        lowerLeft=new Vector2d(0,0);
        upperRight=new Vector2d(100,30);
        lowerLeftJungle=new Vector2d(45,55);
        upperRightJungle=new Vector2d(10,20);
    }

    @Override
    public boolean place(IMapElement element) {
        return false;
    }

    @Override
    public void run(Instruction[] genotype) {
        //Random generator = new Random();
        //generator.nextint(32);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return null;
    }
}
