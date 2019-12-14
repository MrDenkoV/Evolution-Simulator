package agh.cs.project;

import java.util.Random;

import static java.lang.Math.min;

public class Genes {
    Instruction[] genes;
    protected static Random generator = new Random();

    public Genes(Instruction[] genes){
        this.genes=genes;
    }

    public Genes mutate(Genes other){
        int a=generator.nextInt(32);
        int b=generator.nextInt(32);
        Instruction[] gene=this.genes.clone();
        if (-min(-a, -b) - min(a, b) >= 0)
            System.arraycopy(other.genes, min(a, b), gene, min(a, b), -min(-a, -b) - min(a, b));
        return new Genes(gene);
    }
}
