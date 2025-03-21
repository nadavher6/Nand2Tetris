import javax.sound.midi.SysexMessage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CompilationEngine {

    private File inputFile;
    private File outputFile;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String line;
    private static int tab=0;


    public CompilationEngine(File inputFile) {
        this.inputFile = inputFile;
        String newFileName = inputFile.getName();
        newFileName = newFileName.substring(0, newFileName.length() - 5); // Remove file extension
        newFileName += ".xml"; // Add new extension for XML file
        this.outputFile = new File(inputFile.getParent(), newFileName); // Create output file in the same directory

        try {
            this.reader = new BufferedReader(new FileReader(inputFile)); // Initialize reader
            this.writer = new BufferedWriter(new FileWriter(outputFile)); // Initialize writer

            this.line = reader.readLine();
            compile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                if (reader != null) {
                    reader.close(); // Close reader
                }
                if (writer != null) {
                    writer.close(); // Close writer
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void compile() {
        try {


            while (this.line != null) {
                if (this.line.equals("<tokens>")) {
                    this.line = reader.readLine();
                }
                else if (this.line.equals("</tokens>")) {
                    this.line = reader.readLine();
                }
                else if (this.line.equals("<keyword> var </keyword>"))
                {
                    compileVarDec();
                }
                else if (this.line.equals("<keyword> class </keyword>"))
                {
                    compileClass();
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }
    public void compileClass() {
        try
        {

            tab++;
            String tabing="";
            for (int i = 0; i < tab; i++)
            {
                tabing+="\t";
            }

            writer.write("<class>");
            writer.newLine();

            writer.write(tabing+this.line);//class
            writer.newLine();

            this.line = reader.readLine();

            compileclassName();
            writer.write(tabing+this.line);//'{'
            writer.newLine();
            this.line = reader.readLine();

            while((this.line.equals("<keyword> static </keyword>")) || (this.line.equals("<keyword> field </keyword>")))
            {
                compileClassVarDec();
            }
            while((this.line.equals("<keyword> function </keyword>"))||((this.line.equals("<keyword> constructor </keyword>")))||(this.line.equals("<keyword> method </keyword>")))
            {
                compileSubroutineDec();

            }
            writer.write(tabing+this.line);//'}'
            writer.newLine();
            writer.write("</class>");
            writer.newLine();
            this.line = reader.readLine();
            tab--;


            return;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return;
    }
    public void compileClassVarDec()
    {
        try {
            tab++;
            String tabing = "";
            for (int i = 0; i < tab; i++) {
                tabing += "\t";
            }

            writer.write(tabing + "<classVarDec>");
            writer.newLine();
            writer.write(tabing + this.line);//static/field
            writer.newLine();
            this.line = reader.readLine();
            compileType();
            compilevarName();
            while(this.line.equals("<symbol> , </symbol>"))
            {

                writer.write(tabing + this.line);//static/field
                writer.newLine();
                this.line = reader.readLine();
                compilevarName();
            }

            writer.write(tabing + this.line);//;
            writer.newLine();
            writer.write(tabing + "</classVarDec>");
            writer.newLine();
            this.line = reader.readLine();
            tab--;


        }
        catch (IOException e) {

        }
    }
    public void compileType()
    {
        try {
            tab++;
            String tabing = "";
            for (int i = 0; i < tab; i++) {
                tabing += "\t";
            }
            //writer.write(tabing + "<type>");
            ///writer.newLine();
            writer.write(tabing + this.line);//constructor/function/methode
            writer.newLine();
            // writer.write(tabing + "</type>");
            //writer.newLine();
            this.line = reader.readLine();
            tab--;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    public void compileclassName()
    {
        try {
            tab++;
            String tabing = "";
            for (int i = 0; i < tab; i++) {
                tabing += "\t";
            }
            // writer.write(tabing + "<className>");
            //writer.newLine();
            writer.write(tabing + this.line);//identifier
            writer.newLine();
            //writer.write(tabing + "</className>");
            //writer.newLine();
            this.line = reader.readLine();
            tab--;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
    public void compilevarName()
    {

        try {
            tab++;
            String tabing = "";
            for (int i = 0; i < tab; i++) {
                tabing += "\t";
            }
            //writer.write(tabing + "<varName>");
            //writer.newLine();
            writer.write(tabing + this.line);//identifier
            writer.newLine();
            //writer.write(tabing + "</varName>");
            //writer.newLine();
            this.line = reader.readLine();

            tab--;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
    public void compilesubroutineName()
    {
        try {
            tab++;
            String tabing = "";
            for (int i = 0; i < tab; i++) {
                tabing += "\t";
            }
            // writer.write(tabing + "<subroutineName>");
            //writer.newLine();
            writer.write(tabing + this.line);//identifier
            writer.newLine();
            //writer.write(tabing + "</subroutineName>");
            //writer.newLine();
            this.line = reader.readLine();
            tab--;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    public void compileSubroutineDec()
    {
        try {
            tab++;
            String tabing = "";
            for (int i = 0; i < tab; i++) {
                tabing += "\t";
            }

            writer.write(tabing + "<subroutineDec>");
            writer.newLine();
            writer.write(tabing+this.line);//constructor/function/methode
            writer.newLine();
            this.line = reader.readLine();
            if(this.line.equals("<keyword> void </keyword>"))
            {
                writer.write(tabing+this.line);//void
                writer.newLine();
                this.line = reader.readLine();
            }
            else
            {
                compileType();  //  type---(int / char / boolean / class name )
            }
            compilesubroutineName();
            writer.write(tabing+this.line);// '('
            writer.newLine();
            this.line = reader.readLine();

            compileParameterList();


            writer.write(tabing+this.line);// ')'
            writer.newLine();
            this.line = reader.readLine();


            compileSubroutineBody();
            writer.write(tabing+"</subroutineDec>");
            writer.newLine();


            tab--;
            return;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void compileParameterList() {
        try {
            tab++;
            String tabing = "";
            for (int i = 0; i < tab; i++) {
                tabing += "\t";
            }
            writer.write(tabing + "<parameterList>");
            writer.newLine();

            if (this.line.equals("<symbol> ) </symbol>")) {

                writer.write(tabing + "</parameterList>");
                writer.newLine();
//                this.line = reader.readLine();
                tab--;
                return;
            }

            while(!this.line.equals("<symbol> ) </symbol>")) {

                compileType();

                compilevarName();

                if (this.line.equals("<symbol> , </symbol>")) {
                    writer.write(tabing + this.line);
                    writer.newLine();
                    this.line = reader.readLine();
                }
            }
            writer.write(tabing + "</parameterList>");
            writer.newLine();
            tab--;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return;


    }

    public void compileVarDec() {
        try
        {
            tab++;
            String tabing="";
            for (int i = 0; i < tab; i++)
            {
                tabing+="\t";
            }

            writer.write(tabing+"<varDec>");
            writer.newLine();
            writer.write(tabing+this.line);
            writer.newLine();
            this.line = reader.readLine();
            boolean flag=true;
            while(flag)
            {

                if (this.line.equals("<symbol> ; </symbol>"))
                {

                    flag=false;
                }
                else
                {
                    writer.write(tabing+this.line);
                    writer.newLine();
                    this.line = reader.readLine();
                }

            }

            writer.write(tabing+this.line);
            writer.newLine();
            writer.write(tabing+"</varDec>");
            writer.newLine();
            this.line = reader.readLine();
            tab--;


            return;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return;
    }
    public void compileDoStatement() {
        try
        {
            tab++;
            String tabing="";
            for (int i = 0; i < tab; i++)
            {
                tabing+="\t";
            }



            writer.write(tabing+"<doStatement>");
            writer.newLine();
            writer.write(tabing+this.line);//do
            writer.newLine();
            this.line = reader.readLine();

            compileSubroutineCall();
            writer.write(tabing+this.line);
            writer.newLine();
            writer.write(tabing+"</doStatement>");
            writer.newLine();
            this.line = reader.readLine();
            tab--;



            return;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return;
    }
    public void compileExpressionList() {
        try {
            tab++;
            String tabing = "";
            for (int i = 0; i < tab; i++) {
                tabing += "\t";
            }

            writer.write(tabing + "<expressionList>");
            writer.newLine();

            compileExpression();

            while (this.line.equals("<symbol> , </symbol>")) {

                writer.write(tabing+this.line);
                writer.newLine();
                this.line = reader.readLine();
                compileExpression();
            }
            writer.write(tabing+"</expressionList>");
            writer.newLine();
            tab--;
        }
        catch (IOException e)
        {

        }
    }
    public void compileSubroutineCall() {
        try
        {
            tab++;
            String tabing="";
            for (int i = 0; i < tab; i++)
            {
                tabing+="\t";
            }
            ///writer.write(tabing+"<SubroutineCall>");
            ///writer.newLine();
            if((this.line.equals("<symbol> . </symbol>")))
            {

            }
            else {
                writer.write(tabing + this.line);//'subrotinename/class name/ var name'
                writer.newLine();
                this.line = reader.readLine();
            }


            if (this.line.equals("<symbol> ( </symbol>")) {
                writer.write(tabing + this.line);//'('
                writer.newLine();
                this.line = reader.readLine();
                compileExpressionList();
                writer.write(tabing + this.line);//')'
                writer.newLine();
            }
            else
            {

                writer.write(tabing + this.line);//'.'
                writer.newLine();
                this.line = reader.readLine();

                writer.write(tabing + this.line);//'sub routine name'
                writer.newLine();
                this.line = reader.readLine();


                writer.write(tabing + this.line);//'('
                writer.newLine();
                this.line = reader.readLine();


                compileExpressionList();


                writer.write(tabing + this.line);//')'
                writer.newLine();
            }



            //writer.write(tabing+"</SubroutineCall>");
            //writer.newLine();
            this.line = reader.readLine();
            tab--;


            return;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return;
    }


    public void compileStatements() throws IOException {
        tab++;
        String tabing="";
        for (int i = 0; i < tab; i++)
        {
            tabing+="\t";
        }

        writer.write(tabing+"<statements>");
        writer.newLine();

        while (line.startsWith("<keyword>")) {
            if (line.equals("<keyword> let </keyword>")) {
                compileLetStatement();

            } else if (line.equals("<keyword> if </keyword>")) {
                compileIfStatement();
            } else if (line.equals("<keyword> while </keyword>")) {
                compileWhileStatement();
            } else if (line.equals("<keyword> return </keyword>")) {
                compileReturnStatements();
            }
            else if (line.equals("<keyword> do </keyword>")) {
                compileDoStatement();
            }else{
                break;
            }
        }

        writer.write(tabing+"</statements>");
        writer.newLine();
        tab--;
        return;
    }
    public void compileLetStatement() throws IOException {
        tab++;
        String tabing="";
        for (int i = 0; i < tab; i++)
        {
            tabing+="\t";
        }

        /// DECLAIR type:
        writer.write(tabing+"<letStatement>");
        writer.newLine();
        ///  WRITE FIRST LINE

        writer.write(tabing+this.line);//let
        writer.newLine();
        ///  read the varName

        this.line = reader.readLine();
        compilevarName();


        if (this.line.equals("<symbol> [ </symbol>")) {


            ///  write [
            writer.write(tabing+this.line);
            writer.newLine();

            this.line = reader.readLine();


            compileExpression(); //////////////// IMPLEMENT EXPRESSION//////////////



            ///  write ].
            writer.write(tabing+this.line);
            writer.newLine();

            this.line = reader.readLine();
        }


        //here need the array

        writer.write(tabing+this.line);//'=''
        writer.newLine();

        ///  check for start of expression.
        this.line = reader.readLine();


        compileExpression(); //////////////// IMPLEMENT EXPRESSION//////////////
        ///  write ';'
        writer.write(tabing+this.line);
        writer.newLine();
        ///  close the let///
        writer.write(tabing+"</letStatement>");
        writer.newLine();

        this.line = reader.readLine();
        tab--;
        return;
    }
    public void compileExpression() throws IOException {
        boolean flag = false;
        tab++;
        String tabing="";



        for (int i = 0; i < tab; i++)
        {
            tabing+="\t";
        }
        if (this.line.equals("<symbol> ) </symbol>"))
        {
            return;
        }
        writer.write(tabing + "<expression>");
        writer.newLine();

        compileTerm();


        while (this.line.startsWith("<symbol> ")&& isOp(this.line))
        {
            writer.write(tabing+this.line);
            writer.newLine();
            ///  read next token and compile term.
            this.line = reader.readLine();
            compileTerm();
        }
            writer.write(tabing+"</expression>");
            writer.newLine();
        tab--;
        return;
    }
    private boolean isOp(String line) {
        if (line.contains(" ; "))
        {
            return false;
        }
        if (line.equals("<symbol> / </symbol>")) {
            return true;
        }
        return line.contains("+") || line.contains("-") || line.contains("*") ||
                line.contains("&amp;") || line.contains("|") ||
                line.contains("&lt;") || line.contains("&gt;") || line.contains("=");
    }
    private boolean isUnaryOp(String line) {
        return line.contains("-") || line.contains("~");
    }
    private boolean keyWord(String line) {
        return line.contains("true") || line.contains("false") || line.contains("null") || line.contains("this");
    }
    public void compileTerm() throws IOException {

        tab++;
        String tabing="";
        for (int i = 0; i < tab; i++)
        {
            tabing+="\t";
        }



        if (this.line.equals("<symbol> ( </symbol>"))
        {
            writer.write(tabing+"<term>");
            writer.newLine();
            writer.write(tabing+this.line);
            writer.newLine();
            this.line = reader.readLine();
            compileExpression();
            writer.write(tabing+this.line);
            writer.newLine();
            this.line = reader.readLine();
            writer.write(tabing+"</term>");
            writer.newLine();

        }
        /// / this is if its Integer constants, string constants, or identifiers
        else if (this.line.startsWith("<identifier>") || this.line.startsWith("<integerConstant>") || this.line.startsWith("<stringConstant>")|| keyWord(this.line) ||isUnaryOp(this.line)) {


            writer.write(tabing+"<term>");
            writer.newLine();
            writer.write(tabing+this.line);


            writer.newLine();
            if (isUnaryOp(this.line))
            {
                this.line = reader.readLine();
                compileTerm();

            }
            else {


                this.line = reader.readLine();


                if (this.line.equals("<symbol> . </symbol>")) {

                    compileSubroutineCall();
                }

                if (this.line.equals("<symbol> [ </symbol>")) {

                    writer.write(tabing + this.line);//'['
                    writer.newLine();
                    this.line = reader.readLine();
                    compileExpression();
                    writer.write(tabing + this.line);//']'
                    writer.newLine();
                    this.line = reader.readLine();
                }
            }


            writer.write(tabing+"</term>");
            writer.newLine();
        }
        ///  close the term.

        tab--;

        return;
    }
    public void compileIfStatement() throws IOException {
        tab++;
        String tabing="";
        for (int i = 0; i < tab; i++)
        {
            tabing+="\t";
        }

        writer.write(tabing+"<ifStatement>");
        writer.newLine();

        /// write 'if'
        writer.write(tabing+this.line);
        writer.newLine();
        this.line = reader.readLine();


        /// write '('


        writer.write(tabing+this.line);
        writer.newLine();
        this.line = reader.readLine();

        /// lets compile the condition

        compileExpression();



        /// write ')'
        writer.write(tabing+this.line);
        writer.newLine();

        this.line = reader.readLine();


        /// write the {


        writer.write(tabing+this.line);
        writer.newLine();
        this.line = reader.readLine();

        /// compile the expression/
        compileStatements();


        /// write the }
        writer.write(tabing+this.line);
        writer.newLine();
        this.line = reader.readLine();


        ///  check if we have an else statment.
        if(line.equals("<keyword> else </keyword>")){
            /// write the else
            writer.write(tabing+this.line);
            writer.newLine();
            this.line = reader.readLine();

            /// wroite the {
            writer.write(tabing+this.line);
            writer.newLine();
            this.line = reader.readLine();

            /// compile the statment/
            compileStatements();

            /// write the }
            writer.write(tabing+this.line);
            writer.newLine();
            this.line = reader.readLine();
        }
        writer.write(tabing+"</ifStatement>");
        writer.newLine();
        tab--;
        return;
    }
    public void compileReturnStatements() throws IOException {

        tab++;
        String tabing="";
        for (int i = 0; i < tab; i++)
        {
            tabing+="\t";
        }

        writer.write(tabing+"<returnStatement>");
        writer.newLine();

        /// write the 'return'
        writer.write(tabing+this.line);
        writer.newLine();

        this.line = reader.readLine();
        ///  check if we have an expression in the return.
        if(!line.equals("<symbol> ; </symbol>")){
            compileExpression();
        }

        /// wrties the ;
        writer.write(tabing+this.line);
        writer.newLine();

        writer.write(tabing+"</returnStatement>");
        writer.newLine();
        this.line = reader.readLine();

        tab--;
        return;

    }

    public void compileWhileStatement() throws IOException {
        tab++;
        String tabing="";
        for (int i = 0; i < tab; i++)
        {
            tabing+="\t";
        }

        writer.write(tabing+"<whileStatement>");
        writer.newLine();

        /// writess the 'while'
        writer.write(tabing+this.line);
        writer.newLine();
        this.line = reader.readLine();

        /// write the (
        writer.write(tabing+this.line);
        writer.newLine();

        this.line = reader.readLine();

        ///  compile the condition in the while loop
        compileExpression();


        ///  write the )
        writer.write(tabing+this.line);
        writer.newLine();
        this.line = reader.readLine();

        ///  wtire the {
        writer.write(tabing+this.line);
        writer.newLine();

        this.line = reader.readLine();


        compileStatements();


        ///  write the }
        writer.write(tabing+this.line);
        writer.newLine();
        writer.write(tabing+ "</whileStatement>");
        writer.newLine();

        this.line = reader.readLine();
        tab--;
        return;
    }



    public void compileSubroutineBody() {
        try
        {
            tab++;
            String tabing = "";
            for (int i = 0; i < tab; i++) {
                tabing += "\t";
            }
            writer.write(tabing + "<subroutineBody>");
            writer.newLine();
            writer.write(tabing +this.line);//'{'
            writer.newLine();
            this.line = reader.readLine();
            while (this.line.equals("<keyword> var </keyword>"))
            {
                compileVarDec();
            }
            compileStatements();
            writer.write(tabing+this.line);// '}'
            writer.newLine();
            writer.write(tabing+"</subroutineBody>");
            writer.newLine();
            this.line = reader.readLine();
            tab--;


            return;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return;
    }

}