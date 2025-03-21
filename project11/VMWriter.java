import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VMWriter {
    private StringBuilder output;

    public VMWriter() throws IOException {
        output = new StringBuilder();
    }

    public String writePush(String seg, int index){
        return "push " + seg + " " + index + "\n";
    }

    public String writePop(String seg, int index){
        return "pop " + seg + " " + index + "\n";
    }

    ///  op = add, sub, neg, eq, gt, lt, and, or, not.
    public String  writeArithmetic(String op){
        if
        (op.equals("*"))
        {
            return "call Math.multiply "+ 2+ " \n";
        }
        else if (op.equals("/"))
        {
            return "call Math.divide "+2+ " \n";
        }
        else {
            return getOp(op);
        }
    }

    public void writeLabel(String label){
        output.append("label " + label + "\n");
    }

    public void writeGoto(String label){
        output.append("goto " + label + "\n");
    }

    public void writeIf(String label){
        output.append("if-goto " + label + "\n");
    }

    public String writeCall(String name, int nArgs){
        return "call " + name + " " + nArgs + "\n";
    }


    public String writeFunction(String name, int nVars){
        return "function " + name + " " + nVars + "\n";
    }
    public void writeReturn(){
        output.append("return\n");
    }


    public static String getOp(String op){
        StringBuffer buf = new StringBuffer();
        String out = null;
        if (op.equals("+"))
        {
            out = "add";
        }
        else if (op.equals("-")) {
            out = "sub";
        }

       else if (op.equals("&amp;")) {
            out = "and";
        } else if (op.equals("|")) {
            out = "or";
        } else if (op.equals("&lt;")) {
            out = "lt";
        } else if (op.equals("&gt;")) {

            out = "gt";
        } else if (op.equals("=")) {
            out = "eq";
        } else if (op.equals("~")) {
            out = "not";
        }


        return out+"\n";
    }
    public String getOutput() {
        return output.toString();
    }
}
