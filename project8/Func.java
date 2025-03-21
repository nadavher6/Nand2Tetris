public class Func {
    //handle function command
    private String line;
    public Func(String line) {this.line=line;}


    public String [] getFunction () {
        ///  SPLIT THE FUNCTION NAME
        String[] part = this.line.split(" ");
        int local_variable = Integer.parseInt(part[2]);
        String func_name = part[1];
        String [] func = new String[2+local_variable*7];
        func[0]= "//"+ func_name;
        func[1] = "("+ func_name + ")";
        int j = 2;
        for (int i = 0;  i < local_variable; i++) {
            func[j++] = "@0";   ///push 0
            func[j++] = "D=A"; /// put 0 in D
            func[j++] = "@SP";
            func[j++] = "A=M";
            func[j++] = "M=D"; /// put 0 in the top of stack
            func[j++] = "@SP";
            func[j++] = "M=M+1"; /// inc SP
        }
        return func;
    }
}
