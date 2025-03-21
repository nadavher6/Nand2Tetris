public class PP_tester {
    public static void arrTostring (String[] arr)
    {
        for ( int i = 0; i < arr.length; i++ )
        {
            System.out.println (arr[i]);
        }
    }
    public static void  test1 (String line )
    {
        //check the split
        System.out.println ("for the input : "+  line);
        System.out.println ("THE SPLIT IS" );
        pushORpop P=new pushORpop(line);
        System.out.println ("the push/pop is : " +P.getpushORpop() );
        System.out.println ("the segment is : " +P.getsegment() );
        System.out.println ("the I : "+ P.getI() );
        System.out.println("_".repeat(25)); // Prints 18 underscores
    }
    public static void test2 (String line)
    {
        //push constant i
        System.out.println ("for the input : "+  line);
        pushORpop P=new pushORpop(line);
        arrTostring(P.pushConstantInAsm());
    }
    public static void test3 (String line)
    {
        //pop segment {local,argument ,this, that } i
        System.out.println ("for the input : "+  line);
        pushORpop P=new pushORpop(line);
        arrTostring(P.popSegmentInAsm());
    }
    public static void test4 (String line)
    {
        //push segment {local,argument ,this, that } i
        System.out.println ("for the input : "+  line);
        pushORpop P=new pushORpop(line);
        arrTostring(P.pushSegmentInAsm());
    }
    public static void finalTestPP (String line)
    {
        System.out.println ("for the input : "+  line);
        pushORpop P=new pushORpop(line,"XXX");
        arrTostring(P.FINALpushOrPopInAsm());
    }
    public static void finalTestAL (String line)
    {
        System.out.println ("for the input : "+  line);
        aritORlogic A = new aritORlogic(line);
        arrTostring(A.FINALaritORlogic());
    }
    public static void main(String[] args) {
        String str1 = "pop local 3";
        String str2 = "push constant 1744";
        String str3 = "push local 4";
        String str4 = "push constant 17";
        String str5 = "pop temp 3";
        String str6 = "pop pointer 1";
        String str7 = "push static 2";
        String str8 = "pop that 6";
        String str9 = "pop static 8";
        String str10 = "and";
        //pushORpop P=new pushORpop(str7,"filename");
        //test1 (str1);
        //test1 (str2);
        //test2(str2);
        //test3(str5);
        //test4(str4);
        //finalTestPP(str4);
        finalTestAL(str10);


    }
}
