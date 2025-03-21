package main;
public class ParserSt {
    private String input;

    public ParserSt (String input) {
        this.input = input;


    }

    public String instructionType () {
        if (this.input.charAt(0) == '@') {
            return "A_instruction";
        }
        else if ((this.input.charAt(0) == '(') && (this.input.charAt(this.input.length()-1) == ')')){
            return "L_instruction";
        }
        else return "C_instruction";
    }
    public String symobl () {
        if (instructionType()=="A_instruction") {
            return this.input.substring(1);
        }
        else if (instructionType()=="L_instruction") {
            return this.input.substring(1, this.input.length()-1);
        }
        else return "";
    }
    public String dest () {
        if (instructionType()=="C_instruction") {
            int whereIsEquel = this.input.lastIndexOf('=');
            if (whereIsEquel != -1) {
                return this.input.substring(0, whereIsEquel);
            }
            else return "null";


        }
        else return "null";
    }
    public String comp () {
        if (instructionType()=="C_instruction") {
            int whereIsEquel = this.input.lastIndexOf('=');
            int whereIsTheJump=0;
            if (this.input.contains(";"))
            {
                whereIsTheJump= this.input.lastIndexOf(';');
                return this.input.substring(whereIsEquel+1, whereIsTheJump);
            }
            else return this.input.substring(whereIsEquel+1);

        }
        else return "";
    }
    public String jump () {
        if (instructionType()=="C_instruction")
        {

            int whereIsTheJump=0;
            if (this.input.contains(";"))
            {
                whereIsTheJump= this.input.lastIndexOf(';');
                return this.input.substring(whereIsTheJump+1);
            }
            else return "null";

        }
        else return "null";
    }

}
