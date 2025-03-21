public class syntax {
    // handle syntax commands (label,goto,if-goto)
    private String line;
    private static int count_if_goto = 0;
    public syntax(String line)
    {
        this.line=line;
    }
    public String [] getLable ()
    {
        int space=this.line.indexOf(" ");
        String [] ans =new String[2];
        ans[0]="//"+this.line;
        ans[1]="("+this.line.substring(space+1)+")";
        return ans;
    }
    public String [] getGoto ()
    {
        int space=this.line.indexOf(" ");
        String [] ans =new String[3];
        ans[0]="//"+this.line;
        ans[1]="@"+this.line.substring(space+1);
        ans[2]="0;JMP";
        return ans;
    }
    public String [] getIfGoto ()
    {
        int space=this.line.indexOf(" ");
        String [] ans =new String[10];
        //check if Ram[SP] ==0
        ans[0]="//"+this.line;
        ans[1]="@SP";
        ans[2]="M=M-1";
        ans[3]="A=M";
        ans[4]="D=M";
        // JUMP IF D=0
        ans[5]="@if_goto_jump0" + String.valueOf(count_if_goto) ;
        ans[6]="D;JEQ";
        //goto lable
        ans[7]="@"+this.line.substring(space+1);
        ans[8]="0;JMP";
        ans[9]="(if_goto_jump0"+ String.valueOf(count_if_goto) + ")";


        return ans;
    }



    public String [] FINALsyntax() {
        //return the array in asm
        int space=this.line.indexOf(" ");
        if (this.line.substring(0,space).equals("label"))
        {
            return getLable();
        }
        else if (this.line.substring(0,space).equals("goto"))
        {
            return getGoto();
        }
        else if (this.line.substring(0,space).equals("if-goto"))
        {
            count_if_goto++;
            return getIfGoto();
        }

        return null;
    }
}
