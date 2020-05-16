package mk.ukim.finki.os.ispitni.networkingFileCounterMishev;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client extends Thread {

    String serverAddress;
    int port;
    String folderToSearch;

    public Client(String serverAddress, int port, String folderToSearch) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.folderToSearch = folderToSearch;
    }

    @Override
    public void run() {
        int numFiles = getFiles(this.folderToSearch);
        sendDataToServer(serverAddress, port, numFiles);
    }

    private static int getFiles(String folderToSearch) {
        File file = new File(folderToSearch);
        File[] files = file.listFiles();
        int numFiles = 0;
        for (File f : files) {
            if (f.isFile() && f.length() < 20 * 1024) {
                numFiles++;
            }
        }
        return numFiles;
    }

    private static void sendDataToServer(String serverAddress, int port, int numFiles) {
        Socket socket = null;
        try {
            socket = new Socket(serverAddress, port);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(numFiles);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
