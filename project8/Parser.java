
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Parser {
    //Parser get a valid line and return it on 'asm'
    //notice that if Parser get a invalid - in the Main function - we will skip the line and no add it to the new file
    private String line;
    private String filename;

    public Parser(String line, String filename) {
        this.line = line;
        this.filename = filename;
    }

    public boolean isValidLine() {
        //check if the line is valid
        // if the line have space than update 'this.line' to be without spaces
        //System.out.println("checking line :"+this.line);
        this.line = this.line.trim();
        //System.out.println("after trim :"+this.line);
        if (this.line.isEmpty()) {
            //System.out.println("empty line");
            return false;
        }
        int comment = this.line.indexOf("//");
        if (comment != -1) {
            this.line = this.line.substring(0, comment).trim();
            return isValidLine();
            //System.out.println("invalid line");

        }
        return true;
    }

    public boolean isPushOrPop() {
        //return the type of the string
        return ((this.line.contains("pop")) || (this.line.contains("push")));
    }

    public boolean isSyntax() {
        return (this.line.contains("label")) || (this.line.contains("if") || this.line.contains("goto"));
    }
    public boolean VM_call() {
        return (this.line.contains("call"));
    }
    public boolean VM_function()
    {
        return (this.line.contains("function"));
    }
    public boolean VM_return()
    {
        return (this.line.contains("return"));
    }

    public String[] lineInAsm() {
        /** return the final version of the line in 'asm'
         notice that every line will be represented as an array of string
         exaple :
         push constant 17 will be
         arr[0] = "//push constant 17"
         arr[1] = "@17"
         arr[2]="D=A"
         */
        if (isPushOrPop()) {
            pushORpop P = new pushORpop(this.line, this.filename);
            return P.FINALpushOrPopInAsm();
        }
        else if (isSyntax()) {
            syntax S=new syntax(this.line);
            return S.FINALsyntax();
        }
        else if (VM_call()) {
            call c=new call (this.line);
            return c.getcall();
        }
        else if (VM_function()) {
            Func f=new Func (this.line);
            return f.getFunction();
        }
        else if (VM_return()) {
            Return r=new Return (this.line);
            return r.getReturn();
        }

        else {
            aritORlogic P = new aritORlogic(this.line);
            return P.FINALaritORlogic();
        }

    }
}