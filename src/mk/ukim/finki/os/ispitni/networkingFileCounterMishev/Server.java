package mk.ukim.finki.os.ispitni.networkingFileCounterMishev;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    String path;
    int port;
    ServerSocket serverSocket = null;
    BufferedWriter writer = null;

    public Server(String path, int port) {
        this.path = path;
        this.port = port;

    }

    @Override
    public void run() {
        try {
            this.writer = new BufferedWriter(new FileWriter(path, false));
            this.serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = this.serverSocket.accept();
                SocketWorker worker = new SocketWorker(socket, writer);
                worker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
