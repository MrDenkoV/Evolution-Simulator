package agh.cs.project;

import java.util.LinkedList;

public class FollowAnimal {
    private Animal parent;
    private long kidsCount;
    private long descendantsCount;
    private long deathEpoch;
    private long startEpoch;


    public FollowAnimal(Animal animal, long epoch){
        this.parent=animal;
        this.kidsCount=0;
        this.descendantsCount=0;
        animal.isDescendant=true;
        this.deathEpoch =-1;
        this.startEpoch=epoch;
    }

    public void resetFollowing(LinkedList<Animal> animals){
        for(Animal animal: animals)
            animal.resetFollowing();
    }

    public void addDescendant(){
        this.descendantsCount++;
    }

    public void addKid(){
        this.kidsCount++;
        this.addDescendant();
    }

    public void setDeathEpoch(long deathEpoch){
        this.deathEpoch = deathEpoch;
    }

    public long getDeathEpoch(){
        return this.deathEpoch;
    }

    public long getStartEpoch(){
        return this.startEpoch;
    }

    public Animal getParent(){
        return this.parent;
    }

    public long getKidsCount(){
        return this.kidsCount;
    }

    public long getDescendantsCount(){
        return this.descendantsCount;
    }
}
