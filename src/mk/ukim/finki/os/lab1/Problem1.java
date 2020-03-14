package mk.ukim.finki.os.lab1;

import java.io.File;
import java.io.FileNotFoundException;

public class Problem1 {

    public boolean getXPermission(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException();
        }
        if (!f.isDirectory()) {
            throw new FileNotFoundException();
        }
        return f.canExecute();
    }


    public static void main(String[] args) {

    }
}
