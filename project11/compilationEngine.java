import java.io.*;

public class compilationEngine {
    private File jackToken;
    private File outputFile;
    private BufferedReader reader;
    private BufferedWriter writer;
    private symbolTable st_class;
    private symbolTable st_mini;
    private String className;
    private tableRow currentRow;
    private String line;
    private VMWriter vmw;
    private int label_count;
    private boolean flag;



    public compilationEngine(File inputFile) {
        this.jackToken = inputFile;
        String newFileName = inputFile.getName();
        newFileName = newFileName.substring(0, newFileName.length() - 5); // Remove file extension
        newFileName += ".vm"; // Add new extension for XML file
        this.outputFile = new File(inputFile.getParent(), newFileName); // Create output file in the same directory

        try {
            this.reader = new BufferedReader(new FileReader(inputFile)); // Initialize reader
            this.writer = new BufferedWriter(new FileWriter(outputFile)); // Initialize writer
            this.line = reader.readLine();

            compile();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
    private static String extractWord(String input) {
        // Use regex to match the text between <keyword> and </keyword>
        if (input.startsWith("<stringConstant>"))
        {
            input= input.replaceAll("<stringConstant> ", "");
            input= input.replaceAll(" </stringConstant>", "");
            return input;
        }
        return input.replaceAll("<.*?>", "").trim();
    }

    public void compile() {
        try {
            while (this.line != null) {
                if (this.line.equals("<tokens>")) {
                    this.line = reader.readLine();
                } else if (this.line.equals("</tokens>")) {
                    this.line = reader.readLine();
                } else if (this.line.equals("<keyword> class </keyword>")) {
                    compileClass();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }
    public void String_convert(String input) throws IOException {
        char [] charArray = input.toCharArray();
        writer.write("push constant "+charArray.length+"\n");
        writer.write("call String.new 1\n");
        for (char c : charArray) {
            int asciiValue = (int) c;
            writer.write("push constant "+asciiValue+"\n");
            writer.write("call String.appendChar 2\n");

        }
     }
    public void compileTerm() throws IOException {

        if (this.line.equals("<keyword> true </keyword>")) {
            writer.write("push constant 1");
            writer.newLine();
            writer.write("neg");
            writer.newLine();
            this.line = reader.readLine();


        }
        else if ((this.line.equals("<keyword> false </keyword>")) ||(this.line.equals("<keyword> null </keyword>"))){
            writer.write("push constant 0");
            writer.newLine();
            this.line = reader.readLine();

        }
        else if ((this.line.equals("<keyword> this </keyword>"))){
            writer.write("push pointer 0");
            writer.newLine();
            this.line = reader.readLine();

        }
        else if (this.line.equals("<symbol> ( </symbol>"))
        {
            this.line = reader.readLine();
            compileExpression();

            this.line = reader.readLine();


        }
        /// / this is if its Integer constants, string constants, or identifiers
        else if (this.line.startsWith("<identifier>") || this.line.startsWith("<integerConstant>") || this.line.startsWith("<stringConstant>")|| keyWord(this.line) ||isUnaryOp(this.line))
        {


            if (isUnaryOp(this.line))
            {
                String unop = extractWord(this.line);
                if (unop.equals("-"))
                {
                    unop="neg";
                }
                else {
                    unop="not";
                }
                this.line = reader.readLine();

                compileTerm();
                writer.write(unop);
                writer.newLine();

            }
            else if (this.line.startsWith("<integerConstant>"))
            {

                writer.write(this.vmw.writePush("constant", Integer.parseInt(extractWord(this.line))));
                this.line = reader.readLine();
            }
            else if (this.line.startsWith("<stringConstant>"))
            {

                String_convert(extractWord(this.line));
                this.line = reader.readLine();
            }
            else if (this.line.startsWith("<identifier>")) {

                String id = extractWord(this.line);
                this.line = reader.readLine();

                if (this.line.equals("<symbol> [ </symbol>")) {
//                    this.flag = true;
                    this.line = reader.readLine();

                    if (st_mini.containsKey(id)) {
                        writer.write(this.vmw.writePush(st_mini.get(id).getKind(), st_mini.get(id).getNum()));
                    } else {
                        writer.write(this.vmw.writePush(st_class.get(id).getKind(), st_class.get(id).getNum()));
                    }
                    compileExpression(); //////////////// IMPLEMENT EXPRESSION//////////////
                    writer.write("add\n");
                    writer.write("pop pointer 1\n");
                    writer.write("push that 0\n");


                    ///  write ].
                    if (this.line.equals("<symbol> ; </symbol>")) {

                    }
                    else {
                        this.line = reader.readLine();
                    }





                }

                else if (this.line.equals("<symbol> . </symbol>")) {
                    int args=0;
                    if (st_mini.containsKey(id))
                    {
                        writer.write(this.vmw.writePush(st_mini.kindOf(id), st_mini.indexOf(id)));

                        id=st_mini.typeOf(id);
                        args++;
                    }
                    else if (st_class.containsKey(id))
                    {
                        writer.write(this.vmw.writePush(st_class.kindOf(id), st_class.indexOf(id)));
                        id=st_class.typeOf(id);
                        args++;
                    }
//                    if (st_mini.containsKey(id)) {
//                        id=st_mini.typeOf(id);
//                    } else if (st_class.containsKey(id)){
//                        id=st_class.typeOf(id);
//                    }
                    int counter=0;
                    this.line = reader.readLine();

                    id += "."+extractWord(this.line);


                    this.line = reader.readLine();//'('

                    this.line = reader.readLine();//the begin of the expression list

                    if (this.line.equals("<symbol> ) </symbol>")) {
                        this.line = reader.readLine();
                    }
                    else
                    {
                        args = compileExpressionList();
                    }


//
//                    while (true)
//                    {
//                        compileExpression();
//
//                        counter ++;
//
//                        if ((this.line.equals("<symbol> ) </symbol>")))
//                        {
//                            break;
//                        }
//                        else
//                        {
//                            this.line = reader.readLine();//','
//                            this.line = reader.readLine();
//                        }
//
//                    }

                    writer.write(this.vmw.writeCall(id,args));
                    //writer.write(this.vmw.writePop("temp",0));





                }

                else if (this.line.equals("<symbol> ( </symbol>")) {
                    int counter=0;
                    this.line = reader.readLine();
                    while (this.line.equals(this.line.equals("<symbol> ) </symbol>"))==false)
                    {
                        compileExpression();
                        counter ++;
                        this.line = reader.readLine();//','
                        this.line = reader.readLine();

                    }
                    writer.write(this.vmw.writeCall(id,counter));

                }

                else {

                    if (st_mini.containsKey(id)) {
                        writer.write(this.vmw.writePush(st_mini.get(id).getKind(), st_mini.get(id).getNum()));
                    } else {
                        writer.write(this.vmw.writePush(st_class.get(id).getKind(), st_class.get(id).getNum()));
                    }
                }
            }





//            else {
//                this.line = reader.readLine();
//                if (this.line.equals("<symbol> . </symbol>")) {
//
//                    compileSubroutineCall();
//                }
//
//                if (this.line.equals("<symbol> [ </symbol>")) {
//
//                    writer.write(tabing + this.line);//'['
//                    writer.newLine();
//                    this.line = reader.readLine();
//                    compileExpression();
//                    writer.write(tabing + this.line);//']'
//                    writer.newLine();
//                    this.line = reader.readLine();
//                }
//            }
//


        }
        ///  close the term.
        return;
    }
    public void compileExpression() throws IOException {
        if (this.line.equals("<symbol> ) </symbol>"))
        {
            return;
        }
        compileTerm();





        while(isOp(this.line))
        {

            String op = extractWord(this.line);
            this.line = reader.readLine();
            compileTerm();

            writer.write(this.vmw.writeArithmetic(op));

        }


//        while (this.line.startsWith("<symbol> ")&& isOp(this.line))
//        {
//            writer.write(tabing+this.line);
//            writer.newLine();
//            ///  read next token and compile term.
//            this.line = reader.readLine();
//
//        }


        return;
    }
    public void compileClassVarDec()
    {
        try {
            //static/field
            String kind;
            if(this.line.equals("<keyword> static </keyword>"))
            {
                kind="static";
            }
            else
            {
                kind="this";
            }
            this.line = reader.readLine();
            String type;
            type = this.extractWord(this.line);//get the type;
            this.line = reader.readLine();//the var name
            this.currentRow= new tableRow(extractWord(this.line),type,kind,this.st_class.getVar_count(kind));//creat new row
            this.st_class.define(this.currentRow);//add the row to the class table

            this.line = reader.readLine();//read the next line if its ',' keep the var dec else its ;
            while(this.line.equals("<symbol> , </symbol>")) {
                this.line = reader.readLine();// get the next var name
                this.currentRow = new tableRow(extractWord(this.line), type, kind, this.st_class.getVar_count(kind));//creat new row
                this.st_class.define(this.currentRow);//add the row to the class table
                this.line = reader.readLine();//read the next line if its ',' keep the var dec else its ;
            }
            this.line = reader.readLine();//read the line after the ';'

        }
        catch (IOException e) {

        }
    }
    public void compileReturnStatements() throws IOException {
        //return
        this.line = reader.readLine();

        ///  check if we have an expression in the return.
        if(!line.equals("<symbol> ; </symbol>")){
            compileExpression();

        }
        else {
            writer.write("push constant 0");
            writer.newLine();
        }

        /// wrties the ;
        writer.write("return");
        writer.newLine();
        this.line = reader.readLine();


        return;

    }

    public void compileSubroutineDec()
    {
        try {
            this.st_mini.reset();
            this.st_mini=new symbolTable();

            //constructor/function/methode

            if(this.line.equals("<keyword> method </keyword>")) {

                this.currentRow = new tableRow("this", extractWord(this.className), "argument", 0);//according to slide 6
                this.st_mini.define(this.currentRow);
            }
                String s=this.extractWord(this.line);
                this.line = reader.readLine();//the type
                String type = this.extractWord(this.line);
                this.line = reader.readLine();//subroutineName
                String subroutineName=this.extractWord(this.line);
                this.line = reader.readLine();// '('

                this.line = reader.readLine();

                compileParameterList();



            //now we are at ')'

            this.line = reader.readLine();//'{'

            compileSubroutineBody(s, type, subroutineName);



            return;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void compileVarDec() {
        try
        {
            //var

            this.line = reader.readLine();//type

            String type = this.extractWord(this.line);
            this.line = reader.readLine();//first var name

            //add the new var to the table
            this.currentRow=new tableRow(extractWord(this.line),type,"local",this.st_mini.getVar_count(type));//according to slide 6
            this.st_mini.define(this.currentRow);
            this.line = reader.readLine();

            boolean flag1=true;
            while(flag1)
            {
                if (this.line.equals("<symbol> ; </symbol>"))
                {

                    flag1=false;
                }
                else
                {
                     //skip the ','
                    this.line = reader.readLine();//next var name

                    this.currentRow=new tableRow(extractWord(this.line),type,"local",this.st_mini.getVar_count("local"));//according to slide 6
                    this.st_mini.define(this.currentRow);
                    this.line = reader.readLine();
                }

            }
            this.line = reader.readLine();
            return;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return;
    }
    public void compileLetStatement() throws IOException {



        this.line = reader.readLine();
        String var_name = this.extractWord(this.line);
        this.line = reader.readLine();//'='or '['
        this.flag=false;

        if (this.line.equals("<symbol> [ </symbol>")) {
            ///  write [
            this.flag=true;
            this.line = reader.readLine();
            if (st_mini.containsKey(var_name)) {
                writer.write(this.vmw.writePush(st_mini.get(var_name).getKind(), st_mini.get(var_name).getNum()));
            } else {
                writer.write(this.vmw.writePush(st_class.get(var_name).getKind(), st_class.get(var_name).getNum()));
            }
            compileExpression(); //////////////// IMPLEMENT EXPRESSION//////////////
            writer.write("add\n");

            ///  write ].

            this.line = reader.readLine();
        }


        //here need the array

        ///  check for start of expression.


        this.line = reader.readLine();//'='



        compileExpression(); //////////////// IMPLEMENT EXPRESSION//////////////

        ///  write ';'
        if (flag)
        {
            writer.write("pop temp 0\n");
            writer.write("pop pointer 1\n");
            writer.write("push temp 0\n");
            writer.write("pop that 0\n");
//            writer.write("pop temp 0\n");
//            writer.write("pop pointer 1\n");
//            writer.write("push temp 0\n");
//            writer.write("pop that 0\n");

        }
        else if (st_mini.containsKey(var_name)) {
            writer.write(this.vmw.writePop(st_mini.get(var_name).getKind(), st_mini.get(var_name).getNum()));
        } else {
            writer.write(this.vmw.writePop(st_class.get(var_name).getKind(), st_class.get(var_name).getNum()));
        }
        this.line = reader.readLine();
        return;
    }
    public void compileStatements() throws IOException {

        while (line.startsWith("<keyword>")) {
            if (line.equals("<keyword> let </keyword>")) {
                compileLetStatement();
            }

            else if (line.equals("<keyword> if </keyword>")) {
                compileIfStatement();
            }
             else if (line.equals("<keyword> while </keyword>")) {
                compileWhileStatement();
            }
             else if (line.equals("<keyword> return </keyword>")) {
                compileReturnStatements();
            }
            else if (line.equals("<keyword> do </keyword>")) {
                compileDoStatement();
            }
             else {
                break;
            }
        }
        return;
    }


        public void compileSubroutineBody(String s,String type,String name) throws IOException {
        try
        {


            // '{'
            this.line = reader.readLine();

            while (this.line.equals("<keyword> var </keyword>"))
            {
                compileVarDec();
            }
            int arg=this.st_mini.getVar_count("local");
            writer.write(this.vmw.writeFunction(this.className+"."+name, arg));
            if (s.equals("method")) {
                writer.write(this.vmw.writePush("argument", 0));
                writer.write(this.vmw.writePop("pointer", 0));
            }

            else if (s.equals("function")) {

            }
            else
            {
                writer.write(this.vmw.writePush("constant", this.st_class.getVar_count("this")));
                writer.write(this.vmw.writeCall("Memory.alloc", 1));
                writer.write(this.vmw.writePop("pointer", 0));
            }





            compileStatements();

            this.line = reader.readLine();





            return;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return;
    }


    public void compileParameterList() {
        try {

            if (this.line.equals("<symbol> ) </symbol>")) {
                return;
            }

            if (this.line.equals("<symbol> ( </symbol>")) {
                this.line = reader.readLine();
            }


            while(!this.line.equals("<symbol> ) </symbol>")) {

                String type = this.extractWord(this.line);
                this.line = reader.readLine();
                String name=this.extractWord(this.line);

                this.currentRow=new tableRow(name,type,"argument",this.st_mini.getVar_count("argument"));//according to slide 6
                this.st_mini.define(this.currentRow);
                this.line = reader.readLine();


                if (this.line.equals("<symbol> , </symbol>")) {;
                    this.line = reader.readLine();
                }
            }
//            this.line = reader.readLine();//read the next line after the ')'
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return;


    }

    public void compileClass() {
        try {

            this.st_class = new symbolTable();
            this.st_mini=new symbolTable();
            this.vmw=new VMWriter();
            this.label_count=0;
            //class
           // this.line = reader.readLine();//class name
            this.line = reader.readLine();

            this.className = extractWord(this.line);//set the class name

            this.line = reader.readLine();  //'{'

            this.line = reader.readLine();


            while ((this.line.equals("<keyword> static </keyword>")) || (this.line.equals("<keyword> field </keyword>"))) {
                compileClassVarDec();

            }
            while ((this.line.equals("<keyword> function </keyword>")) || ((this.line.equals("<keyword> constructor </keyword>"))) || (this.line.equals("<keyword> method </keyword>"))) {
                compileSubroutineDec();

                //skip the '}'





            }

            this.line = reader.readLine();


            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
    public void compileWhileStatement() throws IOException {

       //while
        this.line = reader.readLine();//read the '('


        this.line = reader.readLine();//read the start of the expression


        int label_counter=this.label_count;
        this.label_count+=2;

        writer.write("label L_"+this.className+label_counter);
        writer.newLine();
        compileExpression();
        //')'
        this.line = reader.readLine();//'{'


        writer.write("not");
        writer.newLine();
        writer.write("if-goto L_"+this.className+(label_counter+1));
        writer.newLine();
        this.line = reader.readLine();//read the start of the statment
        compileStatements();
        writer.write("goto L_"+this.className+label_counter);
        writer.newLine();
        writer.write("label L_"+this.className+(label_counter+1));
        writer.newLine();


        this.line = reader.readLine(); // read the '}'


        return;
    }



    public void compileIfStatement() throws IOException {

        //'if'

        this.line = reader.readLine(); //  '('
        this.line = reader.readLine(); //the beggin of the expression

        /// lets compile the condition

        compileExpression();

        ///  ')'
        this.line = reader.readLine();// '{'
        this.line = reader.readLine();// the begin of the statment

        int label_counter=this.label_count;
        this.label_count+=2;
        //as need in page 26
        writer.write("not");
        writer.newLine();
        writer.write("if-goto L_"+this.className+label_counter);
        writer.newLine();
        compileStatements();

        this.line = reader.readLine();/// write the }

        writer.write("goto L_"+this.className+(label_counter+1));
        writer.newLine();
        writer.write("label L_"+this.className+label_counter);
        writer.newLine();
        ///  check if we have an else statment.
        if(line.equals("<keyword> else </keyword>")){
            /// write the else

            this.line = reader.readLine(); //  '{'
            this.line = reader.readLine(); //the begin of the statment
            /// compile the statment/
            compileStatements();

            /// write the }
            this.line = reader.readLine();
        }
        writer.write("label L_"+this.className+(label_counter+1));
        writer.newLine();

        return;
    }
    public void compileDoStatement() throws IOException {

        this.line = reader.readLine();

        compileSubroutineCall();


        writer.write(this.vmw.writePop("temp", 0));

        this.line = reader.readLine();



    }
    public void compileSubroutineCall() throws IOException {
        int args=0;
        String name = this.extractWord(this.line);
        if (st_mini.containsKey(name))
        {
            writer.write(this.vmw.writePush(st_mini.kindOf(name), st_mini.indexOf(name)));

            name=st_mini.typeOf(name);
            args++;
        }
        else if (st_class.containsKey(name))
        {
            writer.write(this.vmw.writePush(st_class.kindOf(name), st_class.indexOf(name)));
            name=st_class.typeOf(name);
            args++;
        }
        this.line = reader.readLine();




        /// / if we have a '.' it's a method call.
        if((this.line.equals("<symbol> . </symbol>")))
        {

            this.line = reader.readLine();


            name += "." + this.extractWord(this.line);

            this.line = reader.readLine();


        }

        else
        {
            String temp=this.className;
            temp+="."+name;
            name=temp;
            args++;
            writer.write(this.vmw.writePush("pointer", 0));
        }
        if(this.line.equals("<symbol> ( </symbol>")) {
            this.line = reader.readLine();
        }
        ///name = this.extractWord(this.line);
        ///this.line = reader.readLine();




        if (this.line.equals("<symbol> ) </symbol>")) {
            this.line = reader.readLine();
        }
        else {
             args += compileExpressionList();
        }
        writer.write(this.vmw.writeCall(name, args));

        ///this.line = reader.readLine();

        return;
    }
    public int compileExpressionList() throws IOException {

        compileExpression();
        int args =1;
        while (this.line.equals("<symbol> , </symbol>")) {

            /// writer.write(this.line); /// ,
            ///writer.newLine();
            this.line = reader.readLine();
            compileExpression();
            args++;
        }

        //')'
        if (this.line.equals("<symbol> ) </symbol>")){

            this.line = reader.readLine();
            return args;
        }
        return args;
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
}
