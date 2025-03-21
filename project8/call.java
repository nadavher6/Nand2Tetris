public class call {
    //handle call command
    private String line;
    //private String filename;
    private static int count_call = 0;
    public call(String line) {
        this.line = line;
        //this.filename = filename;
    }
    public String[] getPushReturn ()
    {
        String[] words = this.line.split(" ");
        String [] ans =new String[8];
        ans[0]="//push return label";
        ans[1]="@" +words[1] + "$ret." + count_call ;
        ans[2]="D=A";
        ans[3]="@SP";
        ans[4]="A=M";
        ans[5]="M=D";
        ans[6]="@SP";
        ans[7]="M=M+1";
        return ans;
    }
    public String [] getPushAllVars()
    {
        String[] words ={"LCL","ARG","THIS","THAT"};
        int count=0;
        String [] ans = new String[words.length*8];
        for (int i = 0; i < words.length; i++) {
            ans[count++]="//push "+ words[i];
            ans[count++]="@" + words[i] ;
            ans[count++]="D=M";
            ans[count++]="@SP";
            ans[count++]="A=M";
            ans[count++]="M=D";
            ans[count++]="@SP";
            ans[count++]="M=M+1";
        }
        return ans;
    }
    public String [] getSetARG_LCL(int nArgs) {
          String [] ans = new String[14];
          ans[0]="//ARG=SP-5-nArgs";
          //D=5+Args (Args=2)
          ans[1]="@5";
          ans[2]="D=A";
          ans[3]="@"+nArgs;
          ans[4]="D=D+A";
          //D=RAM[SP]-D
          ans[5]="@SP";
          ans[6]="D=M-D";
          //RAM[ARG]=D
          ans[7]="@ARG";
          ans[8]="M=D";
          ans[9]="//LCL=SP";
          //D=RAM[SP]
          ans[10]="@SP";
          ans[11]="D=M";
          //RAM[LCL]=D
          ans[12]="@LCL";
          ans[13]="M=D";
          return ans;
    }

    public String[] getcall() {
        String[] words = this.line.split(" ");
        syntax s=new syntax("goto "+words[1]);
        int nArgs = Integer.parseInt(words[2]);
        int PushReturnSize=getPushReturn().length;
        int PushAllVarsSize=getPushAllVars().length;
        int SetARGSize=getSetARG_LCL(nArgs).length;
        int GotoSize =s.FINALsyntax().length;
        if (this.line.equals("call Sys.init 0"))
        {
            String[] ans = new String[2 + PushReturnSize + PushAllVarsSize + GotoSize];
            ans[0] = "//" + this.line;
            System.arraycopy(getPushReturn(), 0, ans, 1, PushReturnSize);
            System.arraycopy(getPushAllVars(), 0, ans, 1 + PushReturnSize, PushAllVarsSize);
            System.arraycopy(s.FINALsyntax(), 0, ans, 1 + PushReturnSize + PushAllVarsSize , GotoSize);
            ans[ans.length - 1] = "(" + words[1] + "$ret." + count_call++ + ")";
            return ans;
        }
        else {
            String[] ans = new String[2 + PushReturnSize + PushAllVarsSize + SetARGSize + GotoSize];
            ans[0] = "//" + this.line;
            System.arraycopy(getPushReturn(), 0, ans, 1, PushReturnSize);
            System.arraycopy(getPushAllVars(), 0, ans, 1 + PushReturnSize, PushAllVarsSize);
            System.arraycopy(getSetARG_LCL(nArgs), 0, ans, 1 + PushReturnSize + PushAllVarsSize, SetARGSize);
            System.arraycopy(s.FINALsyntax(), 0, ans, 1 + PushReturnSize + PushAllVarsSize + SetARGSize, GotoSize);
            ans[ans.length - 1] = "(" + words[1] + "$ret." + count_call++ + ")";
            return ans;
        }
    }
}
