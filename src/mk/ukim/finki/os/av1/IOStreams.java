package mk.ukim.finki.os.av1;

import java.io.*;

public interface IOStreams {
    void copyFileByteByByte(File from, File to) throws FileNotFoundException, IOException;

    void copyFileByUsingBuffer(File from, File to) throws IOException;

    void printContentOfTxtFile(File file, PrintStream pw) throws IOException;

}
