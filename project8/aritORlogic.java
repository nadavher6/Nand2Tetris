
public class aritORlogic {
    private String line;
    private static int count_eq = 0;
    private static int count_gr = 0;
    private static int count_lt = 0;
    private static int count_and = 0;
    private static int count_or = 0;
    private static int count_not = 0;


    public aritORlogic(String line) {
        this.line = line;
    }

    public String[] getAdd() {
        // add operation
        String[] sum = new String[12];
        sum[0] = "//ADD Operation:";
        /// RAM[SP] = RAM[SP-1] + RAM[SP-2]
        sum[1] = "@SP";
        sum[2] = "M=M-1";
        sum[3] = "A=M";
        sum[4] = "D=M";
        sum[5] = "@SP";
        sum[6] = "M=M-1";
        sum[7] = "A=M";
        sum[8] = "D=D+M";
        sum[9] = "M=D";

        ///  INCREMENT COUNTER
        sum[10] = "@SP";
        sum[11] = "M=M+1";

        return sum;
    }

    public String[] getSub() {
        // sub operation
        String[] sum = new String[12];
        sum[0] = "//SUB Operation:";
        /// RAM[SP] = RAM[SP-1] - RAM[SP-2]
        sum[1] = "@SP";
        sum[2] = "M=M-1";
        sum[3] = "A=M";
        sum[4] = "D=M";
        sum[5] = "@SP";
        sum[6] = "M=M-1";
        sum[7] = "A=M";
        sum[8] = "D=M-D";
        sum[9] = "M=D";

        ///  INCREMENT COUNTER
        sum[10] = "@SP";
        sum[11] = "M=M+1";

        return sum;
    }
    public String[] getNeg() {
        // neg operation
        String[] sum = new String[8];
        sum[0] = "//NEG Operation:";
        /// RAM[SP-1] = -RAM[SP-1]
        sum[1] = "@SP";
        sum[2] = "M=M-1";
        sum[3] = "@SP";
        sum[4] = "A=M";
        sum[5] = "M=-M";

        ///  INCREMENT COUNTER
        sum[6] = "@SP";
        sum[7] = "M=M+1";

        return sum;
    }
    public String[] isEq() {
        // add operation
        String[] sum = new String[23];
        sum[0] = "//EQ Operation:";
        /// D = RAM[SP-2] - RAM[SP-1]
        sum[1] = "@SP";
        sum[2] = "M=M-1";
        sum[3] = "A=M";
        sum[4] = "D=M";
        sum[5] = "@SP";
        sum[6] = "M=M-1";
        sum[7] = "A=M";
        sum[8] = "D=D-M";

        /// JUMP IF D=0
        sum[9] = "@EQeq"+String.valueOf(count_eq);
        sum[10] = "D;JEQ";

        /// JUMP TO END IF D!=0 AND RETURN 0
        sum[11] = "@SP";
        sum[12] = "A=M";
        sum[13] = "M=0";
        sum[14] = "@ENDeq"+String.valueOf(count_eq);
        sum[15] = "0;JMP";

        /// ARE EQ SO RETURN -1
        sum[16] = "(EQeq"+String.valueOf(count_eq)+")";
        sum[17] = "@SP";
        sum[18] = "A=M";
        sum[19] = "M=-1";

        /// END OF COMPER -- INCREMENT COUNTER
        sum[20] = "(ENDeq"+String.valueOf(count_eq)+")";
        sum[21] = "@SP";
        sum[22] = "M=M+1";

        return sum;
    }

    public String[] isGt() {
        String[] sum = new String[23];
        /// D = RAM[SP-2] - RAM[SP-1]
        sum[0] = "//GT Operation:";
        sum[1] = "@SP";
        sum[2] = "M=M-1";
        sum[3] = "A=M";
        sum[4] = "D=M";
        sum[5] = "@SP";
        sum[6] = "M=M-1";
        sum[7] = "A=M";
        sum[8] = "D=M-D";

        /// JUMP IF D>0
        sum[9] = "@EQgt"+String.valueOf(count_gr);
        sum[10] = "D;JGT";

        /// JUMP TO END IF D<=0 AND RETURN 0
        sum[11] = "@SP";
        sum[12] = "A=M";
        sum[13] = "M=0";
        sum[14] = "@ENDgt"+String.valueOf(count_gr);
        sum[15] = "0;JMP";

        /// IS GT SO RETURN -1
        sum[16] = "(EQgt"+String.valueOf(count_gr)+")";
        sum[17] = "@SP";
        sum[18] = "A=M";
        sum[19] = "M=-1";

        /// END OF COMPER -- INCREMENT COUNTER
        sum[20] = "(ENDgt"+String.valueOf(count_gr)+")";
        sum[21] = "@SP";
        sum[22] = "M=M+1";
        return sum;
    }

    public String[] isLt() {
        /// D = RAM[SP-2] - RAM[SP-1]
        String[] sum = new String[23];
        sum[0] = "//LT Operation:";
        sum[1] = "@SP";
        sum[2] = "M=M-1";
        sum[3] = "A=M";
        sum[4] = "D=M";
        sum[5] = "@SP";
        sum[6] = "M=M-1";
        sum[7] = "A=M";
        sum[8] = "D=M-D";

        /// JUMP IF D<0
        sum[9] = "@EQlt"+String.valueOf(count_lt);
        sum[10] = "D;JLT";

        /// JUMP TO END IF D>=0 AND RETURN 0
        sum[11] = "@SP";
        sum[12] = "A=M";
        sum[13] = "M=0";
        sum[14] = "@ENDlt"+String.valueOf(count_lt);
        sum[15] = "0;JMP";

        /// IS LT SO RETURN -1
        sum[16] = "(EQlt"+String.valueOf(count_lt)+")";
        sum[17] = "@SP";
        sum[18] = "A=M";
        sum[19] = "M=-1";

        /// END OF COMPER -- INCREMENT COUNTER
        sum[20] = "(ENDlt"+String.valueOf(count_lt)+")";
        sum[21] = "@SP";
        sum[22] = "M=M+1";
        return sum;
    }

    public String[] isAnd() {
        String[] sum = new String[11];
        sum[0] = "//AND Operation:";
        sum[1] = "@SP";
        sum[2] = "M=M-1";
        sum[3] = "A=M";
        sum[4] = "D=M";
        sum[5] = "@SP";
        sum[6] = "M=M-1";
        sum[7] = "A=M";
        sum[8] = "M=D&M";
        /*sum[9] = "@NOTZEROand"+String.valueOf(count_and);
        sum[10] = "D;JNE";
        sum[11] = "@SP";
        sum[12] = "A=M";
        sum[13] = "M=0";
        sum[14] = "@ENDand"+String.valueOf(count_and);
        sum[15] = "0;JMP";
        sum[16] = "(NOTZEROand"+String.valueOf(count_and)+")";
        sum[17] = "@SP";
        sum[18] = "A=M";
        sum[19] = "M=-1";
        sum[20] = "(ENDand"+String.valueOf(count_and)+")";*/
        sum[9] = "@SP";
        sum[10] = "M=M+1";
        return sum;
    }

    public String[] isOr() {
        String[] sum = new String[11];
        sum[0] = "//OR Operation:";
        sum[1] = "@SP";
        sum[2] = "M=M-1";
        sum[3] = "A=M";
        sum[4] = "D=M";
        sum[5] = "@SP";
        sum[6] = "M=M-1";
        sum[7] = "A=M";
        sum[8] = "M=D|M";
        /*sum[9] = "@NOTZEROor"+String.valueOf(count_or);
        sum[10] = "D;JNE";
        sum[11] = "@SP";
        sum[12] = "A=M";
        sum[13] = "M=0";
        sum[14] = "@ENDor"+String.valueOf(count_or);
        sum[15] = "0;JMP";
        sum[16] = "(NOTZEROor"+String.valueOf(count_or)+")";
        sum[17] = "@SP";
        sum[18] = "A=M";
        sum[19] = "M=-1";
        sum[20] = "(ENDor"+String.valueOf(count_or)+")";*/
        sum[9] = "@SP";
        sum[10] = "M=M+1";
        return sum;
    }

    public String[] isNot() {
        String[] sum = new String[7];
        sum[0] = "//Not Operation:";
        sum[1] = "@SP";
        sum[2] = "M=M-1";
        sum[3] = "A=M";
        sum[4] = "M=!M";
        /*sum[5] = "@ISZEROnot"+String.valueOf(count_not);
        sum[6] = "D;JEQ";
        sum[7] = "@SP";
        sum[8] = "A=M";
        sum[9] = "M=0";
        sum[10] = "@ENDnot"+String.valueOf(count_not);
        sum[11] = "0;JMP";
        sum[12] = "(ISZEROnot"+String.valueOf(count_not)+")";
        sum[13] = "@SP";
        sum[14] = "A=M";
        sum[15] = "M=-1";
        sum[16] = "(ENDnot"+String.valueOf(count_not)+")";*/
        sum[5] = "@SP";
        sum[6] = "M=M+1";

        return sum;
    }

    public String[] FINALaritORlogic() {
        if(this.line.equals("add")){
            return getAdd();
        }
        else if(this.line.equals("sub")){

            return getSub();

        } else if (this.line.equals("neg")) {
            return getNeg();

        } else if (this.line.equals("eq")) {
            count_eq++;
            return isEq();

        } else if (this.line.equals("gt")) {
            count_gr++;
            return isGt();

        } else if (this.line.equals("lt")) {
            count_lt++;
            return isLt();

        } else if (this.line.equals("and")) {
            count_and++;
            return isAnd();

        } else if (this.line.equals("or")) {
            count_or++;
            return isOr();

        } else if (this.line.equals("not")) {
            count_not++;
            return isNot();
        }
        return null;
    }
}
