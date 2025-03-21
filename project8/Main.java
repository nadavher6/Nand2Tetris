import java.io.*;

public class Main {
    public static String [] end_less_loop ()
    {
        String [] arr = new String[4];
        arr[0]= "//finish the file with end less loop";
        arr[1] = "(THIS_IS_THE_END_LESS_LOOP)";
        arr[2] = "@THIS_IS_THE_END_LESS_LOOP";
        arr[3] = "0;JMP";
        return arr;
    }
    public static void main(String[] args) {
        /**if (args.length == 0) {
         System.out.println("No file path provided. Usage: java Main <path-to-asm>");
         return;
         }
         */

        String path = args[0];
        //String path = "/Users/nadavhershkovitz/Downloads/08/StaticsTest";
        File file = new File(path);

        if (!file.exists()) {
            //if file not exist
            System.out.println("file not found");
            return;
        }
        else if (file.isDirectory()) {
            //if its a directory
            // take all the files that end with .vm
            // return a file with the nameOfDirectory.asm
            // the new file have all the subfiles in the same file
            File[] vmFiles = file.listFiles(subfile -> subfile.isFile() && subfile.getName().endsWith(".vm"));
            boolean sysex=false;
            for (File vmFile : vmFiles) {
                if (vmFile.getName().endsWith("Sys.vm"))
                {
                    sysex = true;
                }
            }

            if (vmFiles == null) {
                //no sub files have been found
                return;
            }
            String newFileName = file.getName();
            String soloName = newFileName;
            newFileName += ".asm";
            File outputFile = new File(file, newFileName);
            try {

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                    if (sysex==true) {
                        writer.write("//THIS IS THE START\n");
                        writer.write("@256\nD=A\n@SP\nM=D");
                        writer.newLine();
                        call c = new call("call Sys.init 0");
                        String[] callArr = c.getcall();

                        for (String cArr : callArr) {
                            //run on every line of the new array of 'asm' commands and put every string in the new line
                            writer.write(cArr);
                            writer.newLine();
                        }
                    }
//                    writer.write("@THIS_IS_THE_END_LESS_LOOP\n0;JMP" );
//                    writer.newLine();
                    for (File vmFile : vmFiles) {

                        writer.write("//start of " + vmFile.getName() );
                        writer.newLine();
                        //System.out.println("start read :" + vmFile.getName());

                        BufferedReader reader = new BufferedReader(new FileReader(vmFile));
                        String line = reader.readLine();
                        Parser workLineParser;
                        while (line != null) {
                            workLineParser = new Parser(line, vmFile.getName());
                            if (workLineParser.isValidLine()) {
                                //if the line is valid convert it to 'asm'
                                String[] arr = workLineParser.lineInAsm();
                                for (String newAsmLine : arr) {
                                    //run on every line of the new array of 'asm' commands and put every string in the new line
                                    writer.write(newAsmLine);
                                    writer.newLine();
                                }
                            }
                            else {
                                //line is a comment or empty lines so dont convert it and do nothing
                            }
                            line = reader.readLine();
                        }
                        reader.close();
                    }
                    for (String newAsmLine : end_less_loop()) {
                        //add endless loop in the end
                        writer.write(newAsmLine);
                        writer.newLine();
                    }
                }
            }
            catch (IOException e) {
                //System.err.println("problem!! in:");
                e.printStackTrace();
            }

        }

        else if (file.isFile()) {

            if (file.getName().endsWith(".vm")) {

                //get the new file name in hack
                String newFileName = file.getName();
                newFileName = newFileName.substring(0, newFileName.length() - 3);
                String soloName = newFileName;
                newFileName += ".asm";
                File outputFile = new File(file.getParent(), newFileName);
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line = reader.readLine();
                    Parser workLineParser;
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                        while (line != null) {
                            workLineParser = new Parser(line, soloName);
                            if (workLineParser.isValidLine()) {
                                //if the line is valid convert it to 'asm'
                                String[] arr = workLineParser.lineInAsm();
                                for (String newAsmLine : arr) {
                                    //run on every line of the new array of 'asm' commands and put every string in the new line
                                    writer.write(newAsmLine);
                                    writer.newLine();
                                }
                            }
                            else {
                                //line is a comment or empty lines so dont convert it and do nothing
                            }
                            line = reader.readLine();
                        }

                        reader.close();
                        for (String newAsmLine : end_less_loop()) {
                            //add endless loop in the end
                            writer.write(newAsmLine);
                            writer.newLine();
                        }
                    }
                } catch (IOException e) {
                    //System.err.println("problem!! in:");
                    e.printStackTrace();
                }
            }
            else return;
        }
    }
}
