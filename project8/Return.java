public class Return {
    //handle return command
    private String line;
    private static int countRetAddr=0;
    public Return(String line) {this.line=line;}

    public String[] getEnd_frame() {
        String[] asm = new String[6];
        asm[0] = "//return command";
        asm[1] = "@LCL";
        asm[2] = "A=M";
        asm[3] = "D=A";
        asm[4] = "@endFrame";
        asm[5] = "M=D";
        return asm;
    }
    public String[] getReturn_Addr() {
        String[] asm = new String[5];
        asm[0] = "@5";
        asm[1] = "A=D-A";
        asm[2] = "D=M";
        asm[3] = "@retAddr"+countRetAddr;
        asm[4] = "M=D";
        return asm;
    }
    public String[] getPop_Arg() {
        String[] ans = new String[8];
        ans[0]="@SP";
        ans[1]="M=M-1";
        ans[2] ="@SP";
        ans[3]="A=M";
        ans[4]="D=M";
        ans[5] ="@ARG";
        ans[6]="A=M";
        ans[7]="M=D";
        return ans;
    }
    public String[] getMove_SP() {
        String[] ans = new String[5];
        ans[0] = "@ARG";
        ans[1] = "M=M+1";
        ans[2] = "D=M";
        ans[3] = "@SP";
        ans[4] = "M=D";
        return ans;
    }
    public String [] getSetAllVars()
    {
        String[] words ={"THAT","THIS","ARG","LCL",};
        int count=0;
        int k=1;
        String [] ans = new String[words.length*9];
        for (int i = 0; i < words.length; i++) {
            ans[count++]="//SET "+ words[i];
            ans[count++]="@endFrame";
            ans[count++]="D=M";
            ans[count++]="@"+k++;
            ans[count++]="D=D-A";
            //D=RAM[D]
            ans[count++]="A=D";
            ans[count++]="D=M";
            ans[count++]="@"+ words[i];
            ans[count++]="M=D";
        }
        return ans;
    }
    public String [] getGoTo()
    {
        String[] ans = new String[5];
        ans[0]="//goto retAddr";
        ans[1]="@retAddr"+countRetAddr;
        ans[2]="A=M";
        ans[3]="D=A";
        ans[4]="D;JMP";
        return ans;
    }
    public String[] getReturn() {

        int getEnd_frameSize=getEnd_frame().length;
        int getReturn_AddrSize=getReturn_Addr().length;
        int getMove_SPSize=getMove_SP().length;
        int getSetAllVarsSize=getSetAllVars().length;
        int getPop_ArgSize=getPop_Arg().length;
        int getGoToSize=getGoTo().length;

        String [] ans =new String[2+getEnd_frameSize+getReturn_AddrSize+getMove_SPSize+getSetAllVarsSize+getPop_ArgSize+getGoToSize];
        ans[0]="//"+this.line;
        System.arraycopy(getEnd_frame(), 0, ans, 1, getEnd_frameSize);
        System.arraycopy(getReturn_Addr(), 0, ans, 1+getEnd_frameSize,getReturn_AddrSize);
        System.arraycopy(getPop_Arg(), 0, ans, 1+getEnd_frameSize+getReturn_AddrSize,getPop_ArgSize);
        System.arraycopy(getMove_SP(), 0, ans, 1+getEnd_frameSize+getReturn_AddrSize+getPop_ArgSize,getMove_SPSize);
        System.arraycopy(getSetAllVars(), 0, ans, 1+getEnd_frameSize+getReturn_AddrSize+getPop_ArgSize+getMove_SPSize,getSetAllVarsSize);
        System.arraycopy(getGoTo(),0,ans,1+getEnd_frameSize+getReturn_AddrSize+getPop_ArgSize+getMove_SPSize+getSetAllVarsSize,getGoToSize);
        ans[ans.length-1]="//END";
        countRetAddr++;
        return ans;
    }

}
