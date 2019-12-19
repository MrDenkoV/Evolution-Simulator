package agh.cs.project;

import java.util.LinkedList;

public class FollowAnimal {
    Animal parent;
    long kidsCount;
    long descendantsCount;

    public FollowAnimal(Animal animal){
        this.parent=animal;
        this.kidsCount=0;
        this.descendantsCount=0;
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

    public Animal getParent(){
        return this.parent;
    }

    public long getKidsCount(){
        return this.kidsCount;
    }

    public long getDescendantsCount(){
        return this.kidsCount;
    }
}
