package mk.ukim.finki.os.lab1;

import java.io.*;

public class Problem5 {

    public static void writeToStdOutput(String inputFilePath) throws IOException {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
            writer = new BufferedWriter(new OutputStreamWriter(System.out));
            String line = null;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
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
        writeToStdOutput("/home/aleksandar/Desktop/tmp/lab1.problem5/testFile.txt");
    }
}
