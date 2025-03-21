package main;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Parser {
    private BufferedReader reader;
    private File file;



    public Parser(File file) throws IOException {
        this.file = file;
        this.reader = new BufferedReader(new FileReader(file));

    }


    public String[] advance() {
        List<String> list = new ArrayList<>();
        try {
            String line = reader.readLine();
            while (line != null)
            {
                // Trim leading and trailing white spaces
                line = line.trim();
                // Skip empty lines
                if (line.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }
                // remove everything after "//" include.
                int commentIn = line.indexOf("//");
                if (commentIn != -1) {
                    line = line.substring(0, commentIn).trim(); // trim before and after the substring
                }

                // Skip the line if its empty now.
                if (line.isEmpty()) {
                    line = reader.readLine();
                    continue; // Go to the next line
                }

                list.add(line);
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return list.toArray(new String[0]);
    }
    public static String[] translatedFile(String[] inputfile) throws IOException {
        /// reading lines.
        SymbolTable symbolTable=new SymbolTable();
        int NextAvailableAdd = 16;
        List<String> translatedLines = new ArrayList<>();
        int counter = 0;
        for (String line : inputfile){
            ParserSt st=new ParserSt(line);

            if (st.instructionType()=="L_instruction")
            {
                symbolTable.addEntry(st.symobl(), counter);
                //System.out.println("the symbol " +st.symobl() + " has been added with "+counter );
            }
            else {
                counter++;
            }
        }
        for (int i = 0; i < inputfile.length; i++) {
            ParserSt st=new ParserSt(inputfile[i]);

            if (st.instructionType()=="A_instruction")
            {
                if (symbolTable.contains(st.symobl())){
                    int a=symbolTable.getAddress(st.symobl());
                    inputfile[i] ="@"+ String.valueOf(a);
                }
            }
        }
        symbolTable = new SymbolTable();

        for (String line : inputfile) {
            ParserSt st=new ParserSt(line);
            if (st.instructionType()=="A_instruction")
            {
                String symbol = st.symobl(); /// take the symbol/value after @.
                int value;
                if (symbol.matches("[0-9]+"))
                { ///  its numeric
                    value = Integer.parseInt(symbol);
                }
                else { ///  its a symbol or varible
                    if (!symbolTable.contains(symbol)) { ///  the symbol table doesnt contain the current symbol.

                        symbolTable.addEntry(symbol, NextAvailableAdd); /// so add it in the next address available.

                        NextAvailableAdd++; /// increment the next address available index.
                    }

                    value = symbolTable.getAddress(symbol); /// take value from symbol table.
                }
                translatedLines.add("@" + value); ///  and add @'value' to tranlated Lines.

            }

            else if (st.instructionType()=="C_instruction") {

                translatedLines.add(line);///  C-instroction
            }
        }
        return translatedLines.toArray(new String[0]);
    }

}





