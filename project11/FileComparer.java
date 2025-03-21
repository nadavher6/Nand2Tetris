import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class FileComparer {

    public static boolean areFilesIdentical(String filePath1, String filePath2, Set<Integer> linesToIgnore) throws IOException {
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

                // Check if both lines are null (EOF reached)
                if (line1 == null && line2 == null) {
                    break; // End of files
                }

                // Skip comparison for ignored line numbers
                if (linesToIgnore.contains(lineNumber)) {
                    lineNumber++;
                    continue;
                }

                // Skip lines starting with specific keywords
                if (startsWithKeyword(line1) || startsWithKeyword(line2)) {
                    lineNumber++;
                    continue;
                }

                // Normalize lines by removing all whitespace
                String normalizedLine1 = (line1 == null) ? "" : line1.replaceAll("\\s+", "");
                String normalizedLine2 = (line2 == null) ? "" : line2.replaceAll("\\s+", "");

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

    // Helper method to check if a line starts with specific keywords
    private static boolean startsWithKeyword(String line) {
        if (line == null) {
            return false;
        }
        line = line.trim(); // Remove leading/trailing whitespace
        return line.startsWith("if-goto") || line.startsWith("goto") || line.startsWith("label");
    }

    public static void main(String[] args) {
        try {
            String file1 = "/Users/nadavhershkovitz/Downloads/nand2tetris/projects/11/PPtester/this.vm";
            String file2 = "/Users/nadavhershkovitz/Downloads/nand2tetris/projects/11/PPtester/my.vm";

            // Specify line numbers to ignore
            Set<Integer> linesToIgnore = new HashSet<>();
            linesToIgnore.add(3); // Example: Ignore line 3
            linesToIgnore.add(5); // Example: Ignore line 5

            if (areFilesIdentical(file1, file2, linesToIgnore)) {
                System.out.println("Files are identical.");
            } else {
                System.out.println("Files are different.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
