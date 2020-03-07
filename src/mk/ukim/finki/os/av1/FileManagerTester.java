package mk.ukim.finki.os.av1;

import java.io.File;
import java.io.IOException;

public class FileManagerTester {
    public static void main(String[] args) throws IOException {
        FileManagerImpl fm = new FileManagerImpl();
        IOStreamsImpl iom = new IOStreamsImpl();

        String fileToDeletePath = "/home/aleksandar/Desktop/tmp/testdelete";
        String inputPath = "/home/aleksandar/Desktop/tmp/input.txt";
        String outputPath = "/home/aleksandar/Desktop/tmp/out.txt";

        File fileToDelete = new File(fileToDeletePath);
        File in = new File(inputPath);
        File out = new File(outputPath);

        // === TESTING FileManagerImpl ===
        System.out.println(fm.deleteFile(fileToDelete));

        // === TESTING IOStreamsImpl ===
//        iom.copyFileByteByByte(in, out); //Byte by byte coping
//        iom.copyFileByUsingBuffer(in, out); //Buffered coping
//        iom.printContentOfTxtFile(in, System.out); //Print file content to standard output
    }
}
