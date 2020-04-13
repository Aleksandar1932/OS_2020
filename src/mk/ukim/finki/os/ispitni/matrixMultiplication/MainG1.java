package mk.ukim.finki.os.ispitni.matrixMultiplication;

//public class MainG1 {
//}

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;


class Reader extends Thread {
    final String matrixFile;
    int[][] matrix;
    BufferedReader in = null;


    Reader(String matrixFile) throws FileNotFoundException {
        this.matrixFile = matrixFile;

        File f = new File(matrixFile);
        if (f.isFile()) {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(matrixFile)));
        } else {
            throw new FileNotFoundException("Ne e prosledeno fajl");
        }
    }

    @Override
    public void run() {
        // todo: complete this method according to the text description

        try {
            // todo: The variable in should provide the readLine() method
            int n = Integer.parseInt(in.readLine().trim());
            this.matrix = new int[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = Integer.parseInt(in.readLine().trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class Writer extends Thread {

    private final String outputPath;
    private final int[][] matrix;
    private BufferedWriter pw;

    Writer(String outputPath, int[][] matrix) throws IOException {
        this.outputPath = outputPath;
        this.matrix = matrix;

        File f = new File(outputPath);
        if (f.isFile()) {
            pw = new BufferedWriter(new FileWriter(new File(outputPath)));
        } else {
            throw new FileNotFoundException();
        }
    }


    @Override
    public void run() {
        int n = matrix.length;
        try {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    pw.write(matrix[i][j]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                try {
                    pw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class Transformer extends Thread {
    final int[][] matrix;
    final int row;
    final int column;

    int result;

    static Semaphore numberOfRuns = new Semaphore(15);

    Transformer(int[][] matrix, int row, int column) {
        this.matrix = matrix;
        this.row = row;
        this.column = column;
    }

    @Override
    public void run() {
        try {
            numberOfRuns.acquire();
            int n = matrix.length;
            for (int k = 0; k < n; k++) {
                result += matrix[row][k] * matrix[k][column];
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            numberOfRuns.release();
        }

    }
}

class FileScanner extends Thread {

    static Semaphore lock = new Semaphore(1);
    final File directoryToScan;
    final List<File> matrixFiles = new ArrayList<>();

    FileScanner(File directoryToScan) {
        this.directoryToScan = directoryToScan;
    }

    public void run() {
        try {
            List<FileScanner> scanners = new ArrayList<>();

            File[] files = directoryToScan.listFiles();

            for (File f : files) {
                if (f.isFile() && f.getName().endsWith(".mat")) {
                    lock.acquire();
                    matrixFiles.add(f);
                    lock.release();
                }
                if (f.isDirectory()) {
                    FileScanner fs = new FileScanner(f);
                    scanners.add(fs);
                }
            }

            for (FileScanner scanner : scanners) {
                scanner.start();
            }
            for (FileScanner scanner : scanners) {
                scanner.join(1000);
            }
            System.out.println("Done scanning: " + directoryToScan.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


public class MainG1 {

    public static void main(String[] args) throws IOException, InterruptedException {
        List<Transformer> transformers = new ArrayList<>();

        Reader reader = new Reader("/home/aleksandar/IdeaProjects/OS_2020/src/mk/ukim/finki/os/ispitni/matrixMultiplication/matrix.mat");
        // todo: execute file reading in background
        reader.start();
        reader.join();
        // todo: wait for the matrix to be read

        // todo: transform the matrix
        int n = reader.matrix.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Transformer t = new Transformer(reader.matrix, i, j);
                transformers.add(t);
                // todo: start the background execution

            }
        }

        for (Transformer t : transformers) {
            t.start();
        }
        for (Transformer t : transformers) {
            t.join(1000);
        }
        // todo: wait for all transformers to finish

        int[][] result = new int[n][n];
        for (Transformer t : transformers) {
            result[t.row][t.column] = t.result;
        }

        Writer writer = new Writer("out.bin", result);
        // todo: execute file writing in background
        writer.start();

        FileScanner scanner = new FileScanner(new File("data"));
        // todo: execute file scanning in background
        scanner.start();

        // todo: wait for the scanner to finish and show the results
        scanner.join(1000);
        for (File matrixFile : scanner.matrixFiles) {
            System.out.println(matrixFile.getAbsolutePath());
        }

    }
}
