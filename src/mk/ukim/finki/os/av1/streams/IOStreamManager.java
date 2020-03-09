package mk.ukim.finki.os.av1.streams;

import java.io.*;

public interface IOStreamManager {
    void copyFileByteByByte(File from, File to) throws IOException;

    void copyFileByUsingBuffer(String from, String to) throws IOException;

    void printContentOfTxtFile(File f, PrintStream printer) throws IOException;

    void readContentFromStdInput(OutputStream to) throws IOException;

    void writeToTextFile(File to, String text, Boolean append) throws IOException;

    void memoryUnsafeTextFileCopy(File from, File to) throws FileNotFoundException, IOException;

    void memorySafeTextFileCopy(File from, File to) throws FileNotFoundException, IOException;

    void readFileWithLineNumber(File from, OutputStream is) throws IOException;

    void writeBinaryDataToBFile(File to, Object... objects) throws IOException;

    void readBinaryDataFromBFile(File from, Object... objects) throws FileNotFoundException, IOException;

    void writeToRandomAccessFile(File from) throws IOException;

    void readFromRandomAccessFile(File from, PrintStream out) throws IOException;

    void rewriteInReverseFile(File from, File to) throws IOException;

}
