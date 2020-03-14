package mk.ukim.finki.os.lab1;

import java.io.File;
import java.io.FileNotFoundException;

public class Problem2 {
    public static final String filePath = "/home/aleksandar/Desktop/tmp/lab1.problem2";

    public static void main(String[] args) throws FileNotFoundException {
        File f = new File(filePath);
        if (!f.isDirectory()) {
            throw new FileNotFoundException();
        }
        File[] files = f.listFiles();

        for (File file : files) {
            if (!file.isFile()) {
                continue;
            }
            if (file.isFile() && file.getName().contains("finki")) {
                System.out.println(file.getAbsolutePath());
            }
        }
    }
}
