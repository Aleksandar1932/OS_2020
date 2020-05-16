package mk.ukim.finki.os.ispitni.io.k1g2_2018;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class ExamIO {
    static class Utilities {

        void copyLargeTxtFiles(String from, String to, long size) throws IOException {
            File from_dir = new File(from);
            File to_dir = new File(to);
            File[] files = from_dir.listFiles();

            FileOutputStream fos = new FileOutputStream(to_dir);

            for (File f : files) {
                if (f.isDirectory()) {
                    copyLargeTxtFiles(f.getAbsolutePath(), to, size);
                }

                if (f.isFile() && f.length() > size) {
//                    Files.copy(f.toPath(), to_dir.toPath());
                    Files.copy(f.toPath(), fos);
                    System.out.println(f.getName());
                }
            }

        }


        void serializeData(String destination, List<byte[]> data) {

        }

        byte[] deserializeDataAtPosition(String source, long position, long elementLength) {

            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        Utilities util1 = new Utilities();
        util1.copyLargeTxtFiles(
                "/home/aleksandar/IdeaProjects/OS_2020/src/mk/ukim/finki/os/ispitni/io/k1g2_2018/from_directory",
                "/home/aleksandar/IdeaProjects/OS_2020/src/mk/ukim/finki/os/ispitni/io/k1g2_2018/to_directory",
                -1
        );
    }
}
