import java.io.*;
import java.util.Scanner;

public class Main {
    private static void processJackFile(File file) {
        String newFileName = file.getName();
        newFileName = newFileName.substring(0, newFileName.length() - 5) + ".xmlT";
        String soloName = newFileName;
        File outputFile = new File(file.getParent(), newFileName);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            Parser workLineParser;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                writer.write("<tokens>");
                writer.newLine();

                while (line != null) {
                    workLineParser = new Parser(line, soloName);
                    if (workLineParser.isValidLine()) {
                        String xmlLine = workLineParser.lineInXml();
                        writer.write(xmlLine);
                    }
                    line = reader.readLine();
                }

                reader.close();
                writer.write("</tokens>");
            }

            compilationEngine compilationEngine = new compilationEngine(outputFile);
            outputFile.delete();
        } catch (IOException e) {
            System.err.println("Error processing file: " + file.getName());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String path = args[0];
        //System.out.println(path);

        //String path = "/Users/nadavhershkovitz/Desktop/Runi/project9/project9/hiiii";
        File file = new File(path);

        if (!file.exists()) {
            //if file not exist
            System.out.println("file not found");
            return;
        }
        else if (file.isDirectory()) {
            File[] jackFiles = file.listFiles(subfile -> subfile.isFile() && subfile.getName().endsWith(".jack"));

            if (jackFiles == null || jackFiles.length == 0) {

                System.out.println("No .jack files found in the directory.");


                return;
            }

            String newFileName = file.getName() + ".xmlT";
            File outputFile = new File(file, newFileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile)))
            {
                for (File jackFile : jackFiles) {
                    processJackFile(jackFile);
                }
                outputFile.delete();

            }
            catch (IOException e) {
                System.err.println("Error reading file");
                e.printStackTrace();
            }




        }


        else if (file.isFile()) {

            if (file.getName().endsWith(".jack")) {

                //get the new file name in hack
                String newFileName = file.getName();
                newFileName = newFileName.substring(0, newFileName.length() - 5);
                String soloName = newFileName;
                newFileName += ".xmlT";
                File outputFile = new File(file.getParent(), newFileName);
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line = reader.readLine();
                    Parser workLineParser;
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                        writer.write("<tokens>");
                        writer.newLine();
                        while (line != null) {
                            workLineParser = new Parser(line, soloName);
                            if (workLineParser.isValidLine()) {
                                //if the line is valid convert it to 'xml'
                                String XmlLine = workLineParser.lineInXml();

                                writer.write(XmlLine);
                                //writer.newLine();
                            }
                            else
                            {
                                //line is a comment or empty lines so dont convert it and do nothing
                            }
                            line = reader.readLine();
                        }
                        reader.close();
                        writer.write("</tokens>");
                    }
                    compilationEngine compilationEngine = new compilationEngine(outputFile);
                    outputFile.delete();
                } catch (IOException e) {
                    //System.err.println("problem!! in:");
                    e.printStackTrace();
                }
            }
            else return;
        }


    }
}