package agh.cs.project;

import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.min;

public class Genes {
    Instruction[] instructions;

    public Genes(Instruction[] genes){
        this.instructions=genes;
    }

    public Genes(LoopedMap map){
        this.instructions= new Instruction[32];
        for(int i=0; i<32; i++){
            this.instructions[i]=Instruction.fromNumerical(map.generator.nextInt(8));
        }
        check(this.instructions);
        Arrays.sort(this.instructions);
    }

    @Override
    public String toString() {
        StringBuilder wyn= new StringBuilder();
        for(Instruction instruction: instructions)
            wyn.append(instruction.getNumerical());
        return wyn.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genes genes = (Genes) o;
        return Arrays.equals(instructions, genes.instructions);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(instructions);
    }

    private static void check(Instruction[] gene){
        Integer[] occurrences = new Integer[8];
        for(int i=0; i<8; i++)
            occurrences[i]=0;
        for(Instruction instruction: gene){
            occurrences[instruction.getNumerical()]++;
        }
        int missing=0;
        for(int i=0; i<8; i++)
            if(occurrences[i]!=0)
                missing++;
        if(missing==0)
            return;
        for(int i=0; i<32; i++){
            if(occurrences[gene[i].getNumerical()]>1){
                for(int j=0; j<8; j++){
                    if(occurrences[j]==0){
                        occurrences[j]++;
                        gene[i]=Instruction.fromNumerical(j);
                        missing--;
                    }
                }
            }
            if(missing==0)
                return;
        }
    }

    public Genes mutate(Genes other, LoopedMap map){
        int a=map.generator.nextInt(31);
        int b=map.generator.nextInt(31);
        Instruction[] gene=this.instructions.clone();
        System.arraycopy(other.instructions, min(a, b), gene, min(a, b), -min(-a, -b) - min(a, b)+1);
        check(gene);
        Arrays.sort(gene);
        return new Genes(gene);
    }
}
