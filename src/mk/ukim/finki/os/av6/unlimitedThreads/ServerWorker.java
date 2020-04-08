package mk.ukim.finki.os.av6.unlimitedThreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerWorker extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Integer clientID;

    public ServerWorker(Socket socket, Integer clientID) throws IOException {
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.clientID = clientID;
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void execute() throws InterruptedException, IOException {
        System.out.println(String.format("Worker for client %d started!", this.clientID));
        out.println("Hello client!");
        while (true) {
            String message = in.readLine();
            if (message == null) {
                break;
            }
            System.out.println(String.format("[CLIENT %d] : %s", this.clientID, message));
        }
    }
}
