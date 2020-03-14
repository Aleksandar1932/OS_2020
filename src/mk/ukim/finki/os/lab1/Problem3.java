package mk.ukim.finki.os.lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Problem3 {
    public static final String filePath = "/home/aleksandar/Desktop/tmp/lab1.problem3";
    public static final int MAX_FILE_SIZE = 50 * 1024; //50KB konstanta;

    public static void main(String[] args) throws FileNotFoundException {
        File f = new File(filePath);

        if (!f.exists()) {
            throw new FileNotFoundException();
        }
        if (!f.isDirectory()) {
            throw new FileNotFoundException();
        }

        File[] filesArray = f.listFiles();
        if (filesArray == null || filesArray.length == 0) {
            System.out.println("Empty folder!");
            //Moze da se frli exception;
            //throw new EmptyFolderException();
        } else {
            Arrays.stream(filesArray)
                    .filter(file -> file.length() > MAX_FILE_SIZE)
                    .forEach(file -> System.out.println(file.getName()));
        }
    }
}
