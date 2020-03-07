package mk.ukim.finki.os.av1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManagerImpl implements FileManager {
    @Override
    public boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    @Override
    public boolean exists(File file) {
        return file.exists();
    }

    private void validateIfFileExists(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException(String.format("File with path: %s doesn't exist!", file.getAbsolutePath()));
        }
    }

    @Override
    public long length(File f) {
        return f.length();
    }

    @Override
    public boolean isFile(String path) throws FileNotFoundException {
        File file = new File(path);
        this.validateIfFileExists(file);
        return file.isFile();
    }

    @Override
    public boolean isDirectory(String path) throws FileNotFoundException {
        File file = new File(path);
        this.validateIfFileExists(file);
        return file.isDirectory();
    }

    @Override
    public boolean isHiddenFile(String path) throws FileNotFoundException {
        File file = new File(path);
        this.validateIfFileExists(file);
        return file.isHidden();
    }

    @Override
    public String getAbsolutePathFromWorkingDirectory() {
        File file = new File(".");
        return file.getAbsolutePath();
    }

    @Override
    public File getCurrentWorkingDirectoryAsFile() {
        return new File(".");
    }

    @Override
    public File getParent(File file) {
        return file.getParentFile();
    }

    @Override
    public boolean createDirectoryIfParentExists(String path) {
        File file = new File(path);
        return file.mkdir();
    }

    @Override
    public boolean createDirectoryWithParentDirectories(String path) {
        File file = new File(path);
        return file.mkdirs();
    }

    @Override
    public boolean createFile(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            System.out.println("File exists!");
            return false;
        } else {
            return file.createNewFile();
        }
    }

    @Override
    public boolean moveAndRenameFile(File file, String newName, String newParent) {
        File parent = new File(newParent);
        if (!parent.isDirectory()) {
            System.out.println(String.format("Parent with path: %s is not a directory!", parent.getAbsolutePath()));
            return false;
        }

        File renamedFile = new File(parent, newName);
        if (renamedFile.exists()) {
            System.out.println("File already exists!");
            return false;
        }
        return file.renameTo(renamedFile);
    }

    @Override
    public boolean moveFile(File file, String newParent) {
        return this.moveAndRenameFile(file, file.getName(), newParent);
    }

    @Override
    public boolean renameFile(File file, String newName) {
        return this.moveAndRenameFile(file, newName, file.getParent());
    }

    @Override
    public boolean copyFile(String pathFrom, String pathTo) {
        //TODO implement
        return false;
    }

    @Override
    public void printDirectory(File folder, FilenameFilter filenameFilter) {
        //TODO implement
    }

    @Override
    public void printDirectoryRecursive(File folder, FilenameFilter filenameFilter) {
        //TODO implement
    }

    @Override
    public boolean deleteFile(File file) {

        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    f.delete();
                }
                if (f.isDirectory()) {
                    deleteFile(f);
                }

            }
        }
        return file.delete();
    }

    @Override
    public boolean deleteDirectoryRecursive(File directory) {
        //TODO implement
        return false;
    }

    @Override
    public List<File> getAllFilesRecursive(File directory) {
        //TODO implement
        return null;
    }
}
