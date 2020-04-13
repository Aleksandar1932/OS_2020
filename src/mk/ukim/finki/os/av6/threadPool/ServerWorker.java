package mk.ukim.finki.os.av6.threadPool;

import java.net.Socket;

import static mk.ukim.finki.os.av6.threadPool.TCPServer.workerPool;


public class ServerWorker extends Thread {
    private Socket clientSocket;
    private Integer clientID;

    public ServerWorker(Socket clientSocket, Integer clientID) {
        this.clientSocket = clientSocket;
        this.clientID = clientID;
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("[WORKER %d] has finished", clientID));
        workerPool.release();
    }

    private void execute() throws InterruptedException {
        System.out.println(String.format("[WORKER %d] is working", clientID));
        sleep(10000);
    }
}
