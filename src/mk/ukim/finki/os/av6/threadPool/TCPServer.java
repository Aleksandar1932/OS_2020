package mk.ukim.finki.os.av6.threadPool;

import mk.ukim.finki.os.lab3.DiningPhilosophersTest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class TCPServer {
    public static Semaphore workerPool;
    private static Integer clientCounter;

    private static void init() {
        workerPool = new Semaphore(99);
        clientCounter = 0;
    }

    public static void startTCPServer() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(6868);

        while (true) {
            System.out.println("Listening...");
            if (workerPool.tryAcquire()) {
                Socket socket = serverSocket.accept();
                clientCounter++;
                System.out.println("Got conncetion, assigning worker!");
                ServerWorker sw = new ServerWorker(socket, clientCounter);
                sw.start();

            } else {
                System.err.println("Server has reached the client limit, cannot accept new connections!");
            }

        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        init();
        startTCPServer();
    }
}
