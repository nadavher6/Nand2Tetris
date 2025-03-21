public class Tokentester {
    public static void main(String[] args) {
        String s = "if(x<0){ ";
        Tokenizer t = new Tokenizer();
        System.out.println(t.tokenizing(s));
    }
}