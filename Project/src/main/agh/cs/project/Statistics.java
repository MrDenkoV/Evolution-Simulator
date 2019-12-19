package agh.cs.project;

import java.util.HashMap;
import java.util.Map;

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
    private Genes genotype;
    private Map<Genes, Long> genotypes = new HashMap<>();
    private long mostCommonGenotype;

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
        this.mostCommonGenotype=0;
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

    public Genes getGenotype(){
        return this.genotype;
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

    public void setGenotype(Genes genotype){
        this.genotype=genotype;
    }

    public void addGenotype(Genes genotype){
        long occurrences = 1;
        if(this.genotypes.get(genotype)==null)
            this.genotypes.put(genotype, 1L);
        else{
            occurrences=this.genotypes.get(genotype);
            this.genotypes.remove(genotype);
            this.genotypes.put(genotype, occurrences+1);
        }
        if(this.mostCommonGenotype<occurrences){
            this.mostCommonGenotype=occurrences;
            this.setGenotype(genotype);
        }
    }

    public void setCurrentAvgEnergy(double avgEnergy){
        this.currentAvgEnergy=avgEnergy;
        this.totalAvgEnergy+=avgEnergy;
    }

    public void setCurrentAvgLife(double avgLife){
        this.currentAvgLife=avgLife;
        this.totalAvgEnergy+=avgLife;
    }

    public void setCurrentKidsCount(double kidsCount){
        this.currentKidsCount=kidsCount;
        this.totalKidsCount+=kidsCount;
    }

    public void saveStatistics() throws Exception {
        double depochs = (double) this.epochs;
        Json.writeJSON(this.epochs, totalAnimalCount/depochs, totalWeedsCount/depochs, genotype, totalAvgEnergy/depochs, totalAvgLife/depochs, totalKidsCount/depochs);
    }

}
