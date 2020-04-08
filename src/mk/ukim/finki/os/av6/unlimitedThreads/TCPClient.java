package mk.ukim.finki.os.av6.unlimitedThreads;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashSet;

public class TCPClient extends Thread {

    @Override
    public void run() {
        try {
            startTCPClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startTCPClient() throws IOException {
        InetAddress address = InetAddress.getByName("localhost");
        Socket client = new Socket(address, 6868);
        System.out.println("Connected to server");

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        out.println("Hello server!");

        while (true) {
            String message = in.readLine();
            System.out.println(String.format("[SERVER] %s", message));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        int NUM_CLIENTS = 10;
        HashSet<Thread> clients = new HashSet<>();
        for (int i = 0; i < NUM_CLIENTS; i++) {
            clients.add(new TCPClient());
        }
        for (Thread client : clients) {
            client.start();
        }
        for (Thread client : clients) {
            client.join(1000);
        }

        for (Thread t : clients) {
            if (t.isAlive()) {
                t.interrupt();
            }
        }
    }
}
