package agh.cs.project;

import java.util.Arrays;

public class OptionsParser {
    public static Instruction[] parse(String[] args){
        Instruction[] res = new Instruction[args.length];
        int x=0;
        for(String arg:args){
            if(x>31)
                throw new IllegalArgumentException(Arrays.toString(args) + " longer than 32");
            switch(arg){
                case "0":
                    res[x++]= Instruction.F;
                    break;
                case "1":
                    res[x++]= Instruction.FR;
                    break;
                case "2":
                    res[x++]= Instruction.R;
                    break;
                case "3":
                    res[x++]= Instruction.BR;
                    break;
                case "4":
                    res[x++]= Instruction.B;
                    break;
                case "5":
                    res[x++]= Instruction.BL;
                    break;
                case "6":
                    res[x++]= Instruction.L;
                    break;
                case "7":
                    res[x++]= Instruction.FL;
                    break;
                default:
                    throw new IllegalArgumentException(arg + " is not legal move specification");
            }
        }
        if(x<31)
            throw new IllegalArgumentException(Arrays.toString(args) + " shorter than 32");
        return Arrays.copyOfRange(res, 0, x);
    }
}