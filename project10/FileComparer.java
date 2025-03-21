import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileComparer {

    public static boolean areFilesIdentical(String filePath1, String filePath2) throws IOException {
        try (BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath1)));
             BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath2)))) {

            // Skip the first line of each file
            String skippedLine1 = reader1.readLine();
            String skippedLine2 = reader2.readLine();
            System.out.println("Skipped line from File 1: " + skippedLine1);
            System.out.println("Skipped line from File 2: " + skippedLine2);

            String line1, line2;
            int lineNumber = 2; // Start from the second line

            while (true) {
                line1 = reader1.readLine();
                line2 = reader2.readLine();

                // Debugging output for the lines read
                System.out.println("File 1, Line " + lineNumber + ": " + line1);
                System.out.println("File 2, Line " + lineNumber + ": " + line2);

                // Check if both lines are null (EOF reached)
                if (line1 == null && line2 == null) {
                    break; // End of files
                }

                // Normalize lines by removing all whitespace
                String normalizedLine1 = (line1 == null) ? "" : line1.replaceAll("\\s+", "");
                String normalizedLine2 = (line2 == null) ? "" : line2.replaceAll("\\s+", "");

                // Debugging output for normalized lines
                System.out.println("Normalized File 1, Line " + lineNumber + ": " + normalizedLine1);
                System.out.println("Normalized File 2, Line " + lineNumber + ": " + normalizedLine2);

                // Compare normalized lines
                if (!normalizedLine1.equals(normalizedLine2)) {
                    System.out.println("Files differ at line " + lineNumber);
                    System.out.println("File1: " + normalizedLine1);
                    System.out.println("File2: " + normalizedLine2);
                    return false; // Files differ
                }

                lineNumber++;
            }
        }
        return true; // Files are identical
    }

    public static void main(String[] args) {
        try {
            String file1 = "/Users/nadavhershkovitz/Downloads/10 5/עותק של 10ArrayTest/MainT.xml";
            String file2 = "/Users/nadavhershkovitz/Downloads/10 5/עותק של 10ArrayTest/עותק של 10ArrayTest.xmlT";

            if (areFilesIdentical(file1, file2)) {
                System.out.println("Files are identical.");
            } else {
                System.out.println("Files are different.");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
