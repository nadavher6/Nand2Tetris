
public class Parser {
    //Parser get a valid line and return it on 'xml'
//notice that if Parser get a invalid - in the Main function - we will skip the line and no add it to the new file
    private static boolean flag=true;
    private String line;
    private String filename;
    static boolean inBlockComment = false;


    public Parser(String line, String filename) {
        this.line = line;
        this.filename = filename;
    }

    public boolean isValidLine() {
        // Remove leading and trailing spaces
        this.line = this.line.trim();

        if (inBlockComment) {
            if (this.line.contains("*/")) {
                this.line = this.line.substring(this.line.indexOf("*/") + 2).trim();
                inBlockComment = false;
            } else {

                return false;
            }
        }

        if (this.line.startsWith("/**")) {
            inBlockComment = true;
            if (this.line.contains("*/")) {
                this.line = this.line.substring(this.line.indexOf("*/") + 2).trim();
                inBlockComment = false;
            } else {
                return false;
            }
        }

        int singleLineComment = this.line.indexOf("//");
        if (singleLineComment != -1) {
            this.line = this.line.substring(0, singleLineComment).trim();
        }

        int startBlockComment = this.line.indexOf("/**");
        int endBlockComment = this.line.indexOf("*/");
        if (startBlockComment != -1 && endBlockComment != -1 && endBlockComment > startBlockComment) {
            this.line = this.line.substring(0, startBlockComment) + this.line.substring(endBlockComment + 2);
            this.line = this.line.trim();
        } else if (startBlockComment != -1) {
            this.line = this.line.substring(0, startBlockComment).trim();
            inBlockComment = true;
        }

        this.line = this.line.trim();

        if (this.line.isEmpty()) {
            return false;
        }


        return true;
    }



    public String lineInXml() {
        Tokenizer  tokenizer = new Tokenizer();
        return tokenizer.tokenizing(this.line);
    }
}