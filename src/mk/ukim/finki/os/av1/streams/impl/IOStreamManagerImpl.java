package mk.ukim.finki.os.av1.streams.impl;

import mk.ukim.finki.os.av1.streams.IOStreamManager;

import java.io.*;

public class IOStreamManagerImpl implements IOStreamManager {
    @Override
    public void copyFileByteByByte(File from, File to) throws IOException {
        InputStream fis = null;
        OutputStream fos = null;

        try {
            fis = new FileInputStream(from);
            fos = new FileOutputStream(to);

            int c = -1;

            while ((c = fis.read()) != -1) {
                fos.write(c);
                fos.flush();
            }
        } finally {
            fis.close();
            fos.flush();
            fos.close();
        }
    }

    @Override
    public void copyFileByUsingBuffer(String from, String to) throws IOException {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(new File(from)));
            writer = new BufferedWriter(new FileWriter(new File(to)));
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

    @Override
    public void printContentOfTxtFile(File f, PrintStream printer) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            String line = null;

            while ((line = reader.readLine()) != null) {
                printer.println(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    @Override
    public void readContentFromStdInput(OutputStream to) throws IOException {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            writer = new BufferedWriter(new OutputStreamWriter(to));
            String line = null;

            while ((line = reader.readLine()) != null) {
                writer.write(line);
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

    @Override
    public void writeToTextFile(File to, String text, Boolean append) throws IOException {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new StringReader(text));
            writer = new BufferedWriter(new FileWriter(to, append));
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

    @Override
    public void memoryUnsafeTextFileCopy(File from, File to) throws FileNotFoundException, IOException {
        //TODO implement;
    }

    @Override
    public void memorySafeTextFileCopy(File from, File to) throws FileNotFoundException, IOException {
//TODO implement;
    }

    @Override
    public void readFileWithLineNumber(File from, OutputStream is) throws IOException {
//TODO implement;
    }

    @Override
    public void writeBinaryDataToBFile(File to, Object... objects) throws IOException {
//TODO implement;
    }

    @Override
    public void readBinaryDataFromBFile(File from, Object... objects) throws FileNotFoundException, IOException {
//TODO implement;
    }

    @Override
    public void writeToRandomAccessFile(File from) throws IOException {
//TODO implement;
    }

    @Override
    public void readFromRandomAccessFile(File from, PrintStream out) throws IOException {
//TODO implement;
    }

    @Override
    public void rewriteInReverseFile(File from, File to) throws IOException {
//TODO implement;
    }
}
