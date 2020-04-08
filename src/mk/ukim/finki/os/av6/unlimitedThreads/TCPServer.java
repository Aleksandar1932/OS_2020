package mk.ukim.finki.os.av6.unlimitedThreads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static Integer clientsCounter = 0;

    public static void startTCPServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(6868);

        while (true) {
            System.out.println("Listening...");
            Socket socket = serverSocket.accept();
            System.out.println("Got conncetion!");
            clientsCounter++;
            ServerWorker serverWorker = new ServerWorker(socket, clientsCounter);
            serverWorker.start();
        }
    }

    public static void main(String[] args) throws IOException {
        startTCPServer();
    }
}
