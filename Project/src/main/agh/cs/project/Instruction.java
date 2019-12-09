package agh.cs.project;

public enum Instruction {
    F,
    FR,
    R,
    BR,
    B,
    BL,
    L,
    FL;

    public int getNumerical(){
        return this.ordinal();
    }

    public Instruction fromNumerical(int instruct){
        return Instruction.values()[instruct];
    }
}
