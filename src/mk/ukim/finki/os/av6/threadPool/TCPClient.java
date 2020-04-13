package mk.ukim.finki.os.av6.threadPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

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
    }
}
