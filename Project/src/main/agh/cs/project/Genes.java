package agh.cs.project;

import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.min;

public class Genes {
    Instruction[] instructions;
    //protected static Random generator = new Random();

    public Genes(Instruction[] genes){
        this.instructions=genes;
    }

    public Genes(LoopedMap map){
        this.instructions= new Instruction[32];
        for(int i=0; i<32; i++){
            this.instructions[i]=Instruction.fromNumerical(map.generator.nextInt(8));
        }
        Arrays.sort(this.instructions);
    }

    @Override
    public String toString() {
//        return Arrays.toString(instructions);
        StringBuilder wyn= new StringBuilder();
        for(Instruction instruction: instructions)
            wyn.append(instruction.getNumerical());
        return wyn.toString();
//        return "Genes{" +
//                "instructions=" + Arrays.toString(instructions) +
//                '}';
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
        return Arrays.hashCode(instructions);//hopefully works
    }

    public Genes mutate(Genes other, LoopedMap map){
        int a=map.generator.nextInt(32);
        int b=map.generator.nextInt(32);
        Instruction[] gene=this.instructions.clone();
        if (-min(-a, -b) - min(a, b) >= 0)
            System.arraycopy(other.instructions, min(a, b), gene, min(a, b), -min(-a, -b) - min(a, b));
        Arrays.sort(gene);
        return new Genes(gene);
    }
}
