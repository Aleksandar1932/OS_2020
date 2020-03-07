package mk.ukim.finki.os.av1;

import java.io.*;

public class IOStreamsImpl implements IOStreams {
    @Override
    public void copyFileByteByByte(File from, File to) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(from);
            fos = new FileOutputStream(to);
            int b = -1;
            while ((b = fis.read()) != -1) {
                fos.write(b);
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.flush();
                fos.close();
            }
        }
    }

    @Override
    public void copyFileByUsingBuffer(File from, File to) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(from));
        BufferedWriter bw = new BufferedWriter(new FileWriter(to));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
        } finally {
            br.close();
            bw.flush();
            bw.close();
        }
    }

    @Override
    public void printContentOfTxtFile(File file, PrintStream pw) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        try {
            while ((line = br.readLine()) != null) {
                pw.println(line);
            }
        } finally {
            br.close();
        }


    }
}
