package agh.cs.project;

import java.util.Random;

import static java.lang.Math.abs;

public class Vector2d {
    public final int x;
    public final int y;
    protected static Random generator = new Random();

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d((other.x + this.x + JsonReader.width)%JsonReader.width, (other.y + this.y + JsonReader.height)%JsonReader.height);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d((this.x - other.x + JsonReader.width)%JsonReader.width, (this.y - other.y + JsonReader.height)%JsonReader.height);
    }

    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return ((this.x == that.x) && (this.y == that.y));
    }

    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }

    public Vector2d randInRange(Vector2d other){
        return new Vector2d(this.x+generator.nextInt(abs(other.x-this.x)), this.y+generator.nextInt(abs(other.y-this.y)));
    }

    public Vector2d randOutside(Vector2d other, Vector2d lowerLeft, Vector2d upperRight){
        Vector2d tmp=this.randInRange(other);
        while(tmp.follows(lowerLeft) && tmp.precedes(upperRight)){
            tmp=this.randInRange(other);
        }
        return tmp;
    }

    public Vector2d randAround(){
        return this.add(MapDirection.N.rotate(generator.nextInt(8)).toUnitVector());
    }

    @Override
    public int hashCode(){
        int hash=0;
        hash += this.x * 17 + this.y * 107;
        return hash;
    }
}
