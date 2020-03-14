package mk.ukim.finki.os.lab1;

import java.io.*;

public class Problem6 {
    public static final String inputFilePath = "/home/aleksandar/Desktop/tmp/lab1.problem6/input.txt";
    public static final String outputFilePath = "/home/aleksandar/Desktop/tmp/lab1.problem6/output.txt";

    public static int countVowelsInLine(String line) {
        int counter = 0;
        char[] characters = line.toLowerCase().toCharArray();
        for (char c : characters) {
            if (c == 'а' || c == 'е' || c == 'и' || c == 'о' || c == 'у') {
                counter++;
            }
        }
        return counter;
    }

    public static void countVowels(File inputFile, File outputFile) throws IOException {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(inputFile));
            writer = new BufferedWriter(new FileWriter(outputFile));

            String line = null;

            while ((line = reader.readLine()) != null) {
                writer.write(String.format("%d", countVowelsInLine(line)));
                writer.newLine();
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);

        if (!inputFile.exists() || !inputFile.isFile()) {
            throw new FileNotFoundException();
        }

        if (!outputFile.exists() || !outputFile.isFile()) {
            throw new FileNotFoundException();
        }
        countVowels(inputFile, outputFile);
    }
}