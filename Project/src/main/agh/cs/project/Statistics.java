package agh.cs.project;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static java.lang.Math.max;

public class Statistics{
    private long epochs;
    private long currentAnimalCount;
    private long totalAnimalCount;
    private long currentWeedsCount;
    private long totalWeedsCount;
    private double currentAvgEnergy;
    private double totalAvgEnergy;
    private double currentAvgLife;
    private double totalAvgLife;
    private double currentKidsCount;
    private double totalKidsCount;
    private Genes mostCommonGenotype;
    private Map<Genes, HashSet<Animal> > genotypes = new HashMap<>();
    private long mostCommonGenotypeCounter;
    private long deadAnimals;

    public Statistics(){
        this.epochs=0;
        this.currentAnimalCount=0;
        this.totalAnimalCount=0;
        this.currentWeedsCount=0;
        this.totalWeedsCount=0;
        this.currentAvgEnergy=0;
        this.totalAvgEnergy=0;
        this.currentAvgLife=0;
        this.totalAvgLife=0;
        this.currentKidsCount=0;
        this.totalKidsCount=0;
        this.mostCommonGenotypeCounter=0;
        this.deadAnimals=0;
    }

    public void epochsIncrement(){
        this.epochs++;
    }

    public long getEpochs(){
        return this.epochs;
    }

    public long getCurrentAnimalCount(){
        return this.currentAnimalCount;
    }

    public long getCurrentWeedsCount(){
        return this.currentWeedsCount;
    }

    public Genes getMostCommonGenotype(){
        return this.mostCommonGenotype;
    }

    public double getCurrentAvgEnergy() {
        return this.currentAvgEnergy;
    }

    public double getCurrentAvgLife(){
        return this.currentAvgLife;
    }

    public double getCurrentKidsCount() {
        return this.currentKidsCount;
    }

    public void setCurrentAnimalCount(long animals){
        this.currentAnimalCount=animals;
        this.totalAnimalCount+=animals;
    }

    public void setCurrentWeedsCount(long weeds){
        this.currentWeedsCount=weeds;
        this.totalWeedsCount+=weeds;
    }

    public void setMostCommonGenotype(Genes mostCommonGenotype){
        this.mostCommonGenotype = mostCommonGenotype;
    }

    public void addGenotype(Animal animal){
//        long occurrences = 1;
        Genes genotype = animal.getGenes();
        if(this.genotypes.get(genotype)==null) {
            HashSet<Animal> animals= new HashSet<>();
            animals.add(animal);
            this.genotypes.put(genotype, animals);
        }
        else{
//            occurrences=this.genotypes.get(genotype);
//            this.genotypes.remove(genotype);
//            this.genotypes.put(genotype, occurrences+1);
            this.genotypes.get(genotype).add(animal);
        }
//        if(this.mostCommonGenotype<occurrences){
//            this.mostCommonGenotype=occurrences;
        long occurrences = this.genotypes.get(genotype).size();
        if(this.mostCommonGenotypeCounter <occurrences){
            this.setMostCommonGenotype(genotype);
            this.mostCommonGenotypeCounter =occurrences;
        }
    }

    public void resetGenotypes(){
        for(Genes genotype: this.genotypes.keySet()){
            long sizes=this.genotypes.get(genotype).size();
            if(this.mostCommonGenotypeCounter>sizes){
                this.mostCommonGenotypeCounter=sizes;
                this.mostCommonGenotype=genotype;
            }
        }
    }

    public void removeGenotype(Animal animal){
        Genes genotype = animal.getGenes();
        this.genotypes.get(genotype).remove(animal);
        this.mostCommonGenotypeCounter--;
        if(this.mostCommonGenotype.equals(genotype))
            this.resetGenotypes();
    }

    public void incrementDeadAnimals(){
        this.deadAnimals++;
    }

    public void setCurrentAvgEnergy(double avgEnergy){
        this.currentAvgEnergy=avgEnergy;
        this.totalAvgEnergy+=avgEnergy;
    }

    public void setCurrentAvgLife(double avgLife){
        double tmp=this.currentAvgLife*this.deadAnimals;
//        this.currentAvgLife=avgLife;
//        this.totalAvgLife+=(avgLife+tmp);
//        this.currentAvgLife=((double) avgLife+this.totalAvgEnergy)/deadAnimals;
        this.incrementDeadAnimals();
        this.currentAvgLife=(avgLife+tmp)/deadAnimals;
//        this.totalAvgLife+=currentAvgLife;
    }

    public void increaseTotalAvgLife(){
        this.totalAvgLife+=this.currentAvgLife;
    }

    public void setCurrentKidsCount(double kidsCount){
        this.currentKidsCount=kidsCount;
        this.totalKidsCount+=kidsCount;
    }

    public void saveStatistics(String side) throws Exception {
        double depochs = (double) this.epochs;
        double danimals=(double) max(this.deadAnimals, 1);
        Json.writeJSON(side, this.epochs, totalAnimalCount/depochs, totalWeedsCount/depochs, mostCommonGenotype, totalAvgEnergy/depochs, totalAvgLife/danimals, totalKidsCount/depochs);
    }

}
