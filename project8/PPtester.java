public class PPtester {
    public static void printarr (String [] arr)
    {
        for (int i = 0; i < arr.length; i++)
        {
            System.out.println(arr[i]);
        }
    }
   public static void test1 (String line)
   {
       Parser P =new Parser(line,"Class1.vm");
       String [] arr =P.lineInAsm();
       printarr(arr);
   }

    public static void main(String[] args) {
       String str1 = "label END_LOOP";
       String str2 = "goto END_LOOP";
       String str3 = "if-goto END_LOOP";
       String str4 = "call Foo.mult 2";
       String str5 = "function Foo.mult 3";
       String str6 = "return";
       String str7 = "call Sys.init 0";
       String str8 = "pop static 0";

       test1(str4);
        //test1(str8);
        //test1(str6);

    }
}
