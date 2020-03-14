package mk.ukim.finki.os.lab1;

import java.io.*;

public class Problem4 {
    //public static final String filePath = "/home/aleksandar/Desktop/tmp/lab1.problem4";

    //Interval za golemina na fajl
    public static final int BOTTOM_BOUND = 1024; //1KB
    public static final int UPPER_BOUND = 100 * 1024; //100 KB

    public static String readFilePathFromStdInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    public static void filterFilesInFolder(File folder) {
        File[] filesArray = folder.listFiles();

        for (File file : filesArray) {
            if (file.isDirectory()) {
                filterFilesInFolder(file);
            }

            if (file.isFile() && (file.length() > BOTTOM_BOUND && file.length() < UPPER_BOUND)
                    && (file.getName().endsWith(".txt") || file.getName().endsWith(".out"))) {
                System.out.println(file.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String filePath = readFilePathFromStdInput();
        File f = new File(filePath);

        if (!f.exists()) {
            throw new FileNotFoundException();
        }
        if (!f.isDirectory()) {
            throw new FileNotFoundException();
        }

        filterFilesInFolder(f);
    }
}
