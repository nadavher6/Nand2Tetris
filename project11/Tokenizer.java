import java.util.Set;
import java.util.HashSet;
public class Tokenizer {

    public Tokenizer() {

    }
    public String V0(String input ,String x) {
        if ((input.charAt(0) == '"')&&(input.charAt(input.length()-1)=='"'))
        {
            input = input.substring(1, input.length()-1);
        }
        else if(input.equals("<")){
            input = input.replace("<","&lt;");
        }
        else if (input.equals(">")) {
            input = input.replace(">","&gt;");
        }
        else if (input.equals("\"")) {
            input = input.replace("\"","&quot;");
        }
        else if (input.equals("&")) {
            input =  input.replace("&","&amp;");
        }


        return "<"+x+"> "+ input +" </" + x+">";
    }
    public String symSpace(String input) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            String check = isCon(Character.toString(c));
            if (check == "symbol") {
                output.append(' ');
                output.append(c);
                output.append(' ');
            }
            else {
                output.append(c);
            }
        }
        return output.toString();
    }
    public String tokenizing(String row) {

        StringBuilder output = new StringBuilder();
        StringBuilder tokenBuf = new StringBuilder();
        row=symSpace(row);
        String token = tokenBuf.toString();
        String x = isCon(token);

        for (int i = 0; i < row.length(); i++) {

            char c = row.charAt(i);

            if ((c == ' ') && (!tokenBuf.isEmpty())&&(tokenBuf.charAt(0)!='"'))
            {
                tokenBuf.append(c);
                token = tokenBuf.toString();
                x = isCon(token);
                output.append(V0(token.substring(0,token.length()-1),x)).append("\n");
                tokenBuf.setLength(0);
                /// System.out.println("idenified: "+x+" for token: "+ token);
            }
            else if ((c == ' ') && (!tokenBuf.isEmpty())&&(tokenBuf.charAt(0)=='"'))
            {
                tokenBuf.append(c);
                token = tokenBuf.toString();



            }
            else if (c == ' ')
            {

            }


            else
            {
                tokenBuf.append(c);
                token = tokenBuf.toString();
                x = isCon(token);
                //System.out.println("idenified: "+x+" for token: "+ token);
                if (!x.equals("no"))
                {

                    output.append(V0(token,x)).append("\n");
                    tokenBuf.setLength(0);
                }
            }
        }
        return output.toString();
    }


    public String isCon(String input) {

        if (input.length() == 0) {
            return "no";
        }
        Set<String> keyword = Set.of(
                "class", "constructor", "function",
                "method", "field", "static", "var", "int",
                "char", "boolean", "void", "true", "false",
                "null", "this", "let", "do" ,"if" ,"else" , "while" ,"return"
        );
        if ( keyword.contains(input))
        {
            return "keyword";
        }
        ///////////////////////////////
        keyword = Set.of("{", "}", "(", ")", "[", "]", ".", ",", ";", "+", "-",
                "*", "/", "&", "|", "<", ">", "=", "~"
        );
        if ( keyword.contains(input))
        {
            return "symbol";
        }

        if ((input.charAt(0) == '"')&&(input.charAt(input.length()-1)=='"')&&(input.length()>1))
        {
            return "stringConstant";
        }
        ///////////////////////////////
        if ((input.charAt(input.length()-1)==' ')&&(input.charAt(0) != '"'))
        {
            try {
                String substring = input.substring(0,input.length()-1);
                Integer.parseInt(substring);
                return "integerConstant";
            }
            catch (NumberFormatException e)
            {
            }
            return "identifier";
        }


        ///////////////////////////////
        return "no";

    }

}