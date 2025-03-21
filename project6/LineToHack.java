package main;
public class LineToHack {
    private String line;
    public LineToHack(String line) {
        this.line = line;
    }
    public String getnewLine() {
        //get a single line without symbols and make it hack
        ParserSt check=new ParserSt(line);
        if (check.instructionType()=="A_instruction")
        {
            int number = Integer.parseInt(check.symobl());
            String binary = Integer.toBinaryString(number);
            StringBuilder ans = new StringBuilder(binary);
            while (ans.length() < 16)
            {
                ans.insert(0,'0');
            }
            return ans.toString();
        }
        if (check.instructionType()=="C_instruction") {
            Code_API convert = new Code_API();
            StringBuilder ans = new StringBuilder(16);
            ans.append(111);
            ans.append(convert.comp(check.comp()));
            ans.append(convert.dest(check.dest()));
            ans.append(convert.jump(check.jump()));
            return ans.toString();

        }
        return null;
    }

}

