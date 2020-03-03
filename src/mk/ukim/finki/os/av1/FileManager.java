package mk.ukim.finki.os.av1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

public interface FileManager {
    boolean exists(String path);

    boolean exists(File file);

    long length(File f);

    boolean isFile(String path) throws FileNotFoundException;

    boolean isDirectory(String path) throws FileNotFoundException;

    boolean isHiddenFile(String path) throws FileNotFoundException;

    String getAbsolutePathFromWorkingDirectory();

    File getCurrentWorkingDirectoryAsFile();

    File getParent(File file);

    boolean createDirectoryIfParentExists(String path);

    boolean createDirectoryWithParentDirectories(String path);

    boolean createFile(String path) throws IOException;

    boolean moveAndRenameFile(File file, String newName, String newParent);

    boolean moveFile(File file, String newParent);

    boolean renameFile(File file, String newName);

    boolean copyFile(String pathFrom, String pathTo);

    void printDirectory(File folder, FilenameFilter filenameFilter);

    void printDirectoryRecursive(File folder, FilenameFilter filenameFilter);

    boolean deleteFile(File file);

    boolean deleteDirectoryRecursive(File directory);

    List<File> getAllFilesRecursive(File directory);

}
