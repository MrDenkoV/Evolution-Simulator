package agh.cs.project;

import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.min;

public class Genes {
    Instruction[] instructions;
    protected static Random generator = new Random();

    public Genes(Instruction[] genes){
        this.instructions=genes;
    }

    public Genes(){
        this.instructions= new Instruction[32];
        for(int i=0; i<32; i++){
            this.instructions[i]=Instruction.fromNumerical(generator.nextInt(8));
        }
    }

    @Override
    public String toString() {
        return "Genes{" +
                "instructions=" + Arrays.toString(instructions) +
                '}';
    }

    public Genes mutate(Genes other){
        int a=generator.nextInt(32);
        int b=generator.nextInt(32);
        Instruction[] gene=this.instructions.clone();
        if (-min(-a, -b) - min(a, b) >= 0)
            System.arraycopy(other.instructions, min(a, b), gene, min(a, b), -min(-a, -b) - min(a, b));
        return new Genes(gene);
    }
}
