package main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            //System.out.println("No file path provided. Usage: java Main <path-to-asm>");
            return;
        }

        String path = args[0];
        File file = new File(path);

        if (!file.exists()) {
            //System.out.println("file not found");
            return;
        }

        //get the new file name in hack
        String newFileName = file.getName();
        newFileName = newFileName.substring(0, newFileName.length() - 4);
        newFileName+=".hack";
        File outputFile = new File(file.getParent(), newFileName);

        try {
         Parser parser1 = new Parser(file);
         String[] strings = parser1.translatedFile(parser1.advance());


             //the valid array of strings
             //to binary
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                for (int i = 0; i < strings.length; i++) {
                    LineToHack convert = new LineToHack(strings[i]);
                    String newBinaryLine = convert.getnewLine();
                    writer.write(newBinaryLine);
                    writer.newLine();
                }
            }
            //System.out.println("Processed file saved as: " + outputFile.getAbsolutePath());
         }
         catch (IOException e) {
             //System.err.println("problem!! in:");
             e.printStackTrace();
         }

    }
}

