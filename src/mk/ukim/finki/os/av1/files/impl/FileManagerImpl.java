package mk.ukim.finki.os.av1.files.impl;

import mk.ukim.finki.os.av1.exceptions.FileExistsException;
import mk.ukim.finki.os.av1.exceptions.FileNotDirectoryException;
import mk.ukim.finki.os.av1.files.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class FileManagerImpl implements FileManager {

    @Override
    public File getFileFromString(String file) {
        return new File(file);
    }

    @Override
    public File[] getFilesInFolder(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        if (!file.isDirectory()) {
            throw new FileNotFoundException();
        }
        return file.listFiles();
    }

    @Override
    public void printFileNames(File file, PrintStream writer) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        if (!file.isDirectory()) {
            throw new FileNotFoundException();
        }
        File[] files = file.listFiles();

//        //Iterative approach;
//        for (File f : files) {
//            writer.println(f.getName());
//        }
        if (files != null) {
            Arrays.stream(files).forEach(f -> writer.println(f.getName()));
        } else {
            //TODO throw empty folder exception;
            System.out.println("Folder is empty!");
        }
    }

    @Override
    public File[] getFilesFromString(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        if (!file.isDirectory()) {
            throw new FileNotFoundException();
        }
        return file.listFiles();
    }

    @Override
    public String getAbsPath(String relPath) throws FileNotFoundException {
        File file = new File(relPath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        } else return file.getAbsolutePath();
    }

    @Override
    public long getFileSize(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException();
        } else return file.length();
    }

    @Override
    public void printFilePermissions(File f, PrintStream writer) throws FileNotFoundException {
        if (!f.exists()) {
            throw new FileNotFoundException();
        }
        writer.println(String.format("Read: %s", f.canRead()));
        writer.println(String.format("Write: %s", f.canRead()));
        writer.println(String.format("Execute: %s", f.canExecute()));

    }

    @Override
    public void createNewFile(String file) throws FileExistsException, IOException {
        File f = new File(file);
        if (f.exists()) {
            throw new FileExistsException();
        }
        f.createNewFile();
    }

    @Override
    public void createFolder(String folder) throws FileExistsException {
        File f = new File(folder);
        if (f.exists()) {
            throw new FileExistsException();
        }

        f.mkdir();
    }

    @Override
    public File[] filterImagesFilesInDir(String dirPath) throws FileNotFoundException {
        File file = new File(dirPath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        if (!file.isDirectory()) {
            throw new FileNotFoundException();
        }

        File[] filesArray = file.listFiles();

        if (filesArray == null) {
            //TODO throw empty folder exception;
            System.out.println("Folder is empty!");
            return new File[0];
        } else {
            return Arrays.stream(filesArray)
                    .filter(f ->
                            (f.getName().endsWith(".jpg") || f.getName().endsWith(".png"))
                    )
                    .toArray(File[]::new);
        }
    }

    @Override
    public void renameFile(File src, File dest) throws FileNotFoundException, FileExistsException {
        if (!src.exists()) {
            throw new FileNotFoundException();
        }
        if (dest.exists()) {
            throw new FileExistsException();
        }
        src.renameTo(dest);
    }

    @Override
    public Date getLastModified(String file) throws FileNotFoundException {
        File f = new File(file);
        if (!f.exists()) {
            throw new FileNotFoundException();
        }

        return new Date(f.lastModified());
    }

    @Override
    public void filterImagesFilesInDirRec(File file, PrintStream out) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        File[] filesArray = file.listFiles();

        for (File f : filesArray) {
            if (f.isDirectory()) {
                filterImagesFilesInDirRec(f, out);
            }
            if (f.isFile() && (f.getName().endsWith(".jpg") || f.getName().endsWith(".png"))) {
                out.println(f.getAbsolutePath());
            }
        }
    }

    @Override
    public boolean deleteFolder(File folder) throws FileNotFoundException {
        if (!folder.exists()) {
            throw new FileNotFoundException();
        }
        if (!folder.isDirectory()) {
            throw new FileNotFoundException();
        }

        File[] filesArray = folder.listFiles();

        if (filesArray != null) {
            for (File f : filesArray) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                }
                f.delete();
            }
        }

        return folder.delete();
    }
}
