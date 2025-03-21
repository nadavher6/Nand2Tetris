
public class pushORpop {
    private String line;
    private String filename;
    public pushORpop(String line ) {
        this.line=line;
        this.filename="";
    }
    public pushORpop(String line, String filename ) {
        this.line=line;
        this.filename=filename;
    }
    public String helper (int number)
    {
        String[] arr=line.split(" ");
        if (arr.length!=3)
        {
            System.out.println("INVALID INPUT");
            return null;
        }
        else {
            return arr[number];
        }
    }
    public String getpushORpop() {
        // return if its push or pop
        return helper(0);
    }
    public String getsegment() {
        //return the segment
        return helper(1);
    }
    public String getSegmentPointer() {
        if (getsegment().equals("local"))
        {
            return "LCL";
        }
        else if (getsegment().equals("argument"))
        {
            return "ARG";
        }
        else if (getsegment().equals("this"))
        {
            return "THIS";
        }
        else if (getsegment().equals("that"))
        {
            return "THAT";
        }
        else return null;
    }
    public String getI() {
        //return the I value
        return helper(2);
    }
    public String [] pushConstantInAsm() {
        //only push constant i
        //PAGE 22
        String [] ans =new String[8];
        ans[0]="//"+this.line;
        ans[1]="@"+getI();
        ans[2]="D=A";
        ans[3]="@SP";
        ans[4]="A=M";
        ans[5]="M=D";
        ans[6]="@SP";
        ans[7]="M=M+1";
        return ans;
    }
    public String [] pushTempInAsm() {
        //only push temp i
        //PAGE 35
        String [] ans =new String[12];
        ans[0]="//"+this.line;
        ans[1]="@"+getI();
        ans[2]="D=A";
        ans[3]="@5";
        ans[4]="D=D+A";
        ans[5]="A=D";
        ans[6]="D=M";
        ans[7]="@SP";
        ans[8]="A=M";
        ans[9]="M=D";
        ans[10]="@SP";
        ans[11]="M=M+1";
        return ans;
    }
    public String [] pushSegmentInAsm() {
        ////only push {local,argument,this,that}
        //PAGE 30
        String [] ans =new String[15];
        ans[0]="//"+this.line;
        ans[1]="@"+getSegmentPointer();
        ans[2]="D=M";
        ans[3]="@"+getI();
        ans[4]="D=D+A";
        ans[5]="@addr";
        ans[6]="M=D";
        ans[7]="@addr";
        ans[8]="A=M";
        ans[9]="D=M";
        ans[10]="@SP";
        ans[11]="A=M";
        ans[12]="M=D";
        ans[13]="@SP";
        ans[14]="M=M+1";
        return ans;
    }
    public String [] pushPointerInAsm() {
        ////only push pointer
        //PAGE 30
        String [] ans =new String[12];
        ans[0]="//"+this.line;
        ans[1]="@"+getSegmentPointer();
        ans[2]="D=M";
        ans[3]="@"+getI();
        ans[4]="D=D+A";
        ans[5]="@addr";
        ans[6]="M=D";
        ans[7]="@SP";
        ans[8]="A=M";
        ans[9]="M=D";
        ans[10]="@SP";
        ans[11]="M=M+1";
        return ans;
    }
    public String [] xxx() {
        //convert a static line to xxx.i
        String[] ans =new String[2];
        if (getsegment().equals("static")) ;
        {
            ans[0] = getpushORpop() ;
            ans[1] =this.filename + "." + getI();
        }
        return ans;
    }
    public String [] pushStaticInAsm() {
        String line[] = xxx();
        String ans[] =new String[10];
        ans[0]="//"+this.line;
        ans[1]="@"+line[1];
        ans[2]="D=A";
        ans[3]="A=D";
        ans[4]="D=M";
        ans[5]="@SP";
        ans[6]="A=M";
        ans[7]="M=D";
        ans[8]="@SP";
        ans[9]="M=M+1";
        return ans;
    }

    public String [] popSegmentInAsm() {
        //only pop {local,argument,this,that}
        String [] ans =new String[15];
        ans[0]="//"+this.line;
        ans[1]="@"+getSegmentPointer();
        ans[2]="A=M";
        ans[3]="D=A";
        ans[4]="@"+getI();
        ans[5]="D=D+A";
        ans[6]="@addr";
        ans[7]="M=D";
        ans[8]="@SP";
        ans[9]="M=M-1";
        ans[10]="A=M";
        ans[11]="D=M";
        ans[12]="@addr";
        ans[13]="A=M";
        ans[14]="M=D";
        return ans;
    }
    public String [] popPpinterInAsm() {
        //only pop pointer
        String [] ans =new String[14];
        ans[0]="//"+this.line;
        ans[1]="@"+getSegmentPointer();
        ans[2]="D=A";
        ans[3]="@"+getI();
        ans[4]="D=D+A";
        ans[5]="@addr";
        ans[6]="M=D";
        ans[7]="@SP";
        ans[8]="M=M-1";
        ans[9]="A=M";
        ans[10]="D=M";
        ans[11]="@addr";
        ans[12]="A=M";
        ans[13]="M=D";
        return ans;
    }
    public String [] popTempInAsm() {
        //only pop temp i
        String [] ans =new String[14];
        ans[0]="//"+this.line;
        ans[1]="@5";
        //ans[2]="A=M";
        ans[2]="D=A";
        ans[3]="@"+getI();
        ans[4]="D=D+A";
        ans[5]="@addr";
        ans[6]="M=D";
        ans[7]="@SP";
        ans[8]="M=M-1";
        ans[9]="A=M";
        ans[10]="D=M";
        ans[11]="@addr";
        ans[12]="A=M";
        ans[13]="M=D";
        return ans;
    }
    public String [] popStaticInAsm() {
        String line[] = xxx();
        String [] ans =new String[8];
        ans[0]="//"+this.line;
        ans[1]="@SP";
        ans[2]="M=M-1";
        //ans[1]="@"+line[1];
        ans[3]="@SP";
        ans[4]="A=M";
        ans[5]="D=M";
        ans[6]="@"+line[1];
        ans[7]="M=D";
        return ans;

    }

    public String [] FINALpushOrPopInAsm() {
        //return the array in asm

        if (this.getpushORpop().equals("push"))
        {
            //if its a push command

            if (this.getsegment().equals("pointer"))
            {
                //if its a pointer change 'this.line' acorrding to slide 38

                if (this.getI().equals("0"))
                {
                    //System.out.println ("line change from  : " +this.line );
                    this.line="push this 0";
                    return pushPointerInAsm();

                }
                else
                {
                    //System.out.println ("line change from  : " +this.line );
                    this.line="push that 0";
                    return pushPointerInAsm();
                }
            }

            if (this.getsegment().equals("local")||(getsegment().equals("argument"))||(getsegment().equals("this"))||(getsegment().equals("that")))
            {
                return pushSegmentInAsm();
            }
            else if (this.getsegment().equals("constant"))
            {
                return pushConstantInAsm();
            }
            else if (this.getsegment().equals("temp"))
            {
                return pushTempInAsm();
            }
            else if (this.getsegment().equals("static"))
            {
                return pushStaticInAsm();
            }
        }
        else
        {
            //if its a pop command
            if (this.getsegment().equals("pointer"))
            {
                //if its a pointer change 'this.line' acorrding to slide 38

                if (this.getI().equals("0"))
                {
                    //System.out.println ("line change from  : " +this.line );
                    this.line="pop this 0";
                    return popPpinterInAsm();
                }
                else
                {
                    // System.out.println ("line change from  : " +this.line );
                    this.line="pop that 0";
                    return popPpinterInAsm();
                }
            }
            else if (this.getsegment().equals("static"))
            {
                return popStaticInAsm();

            }
            if (this.getsegment().equals("local")||(getsegment().equals("argument"))||(getsegment().equals("this"))||(getsegment().equals("that")))
            {
                return popSegmentInAsm();
            }
            else if (this.getsegment().equals("temp"))
            {
                return popTempInAsm();
            }


        }
        return null;
    }

}
