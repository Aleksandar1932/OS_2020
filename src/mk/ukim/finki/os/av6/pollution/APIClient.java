package mk.ukim.finki.os.av6.pollution;

import java.io.IOException;
import java.net.*;

public class APIClient extends Thread {
    String name;
    String host;
    int port;

    public APIClient(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void execute() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] buffer = new byte[1024];

        // Send request to server;
        String messageToSend = "[CL]Dek si be server";
        buffer = messageToSend.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(host), port);
        socket.send(packet);

        // Recieving response from server
        buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        String receivedMessage = new String(packet.getData(), 0, packet.getLength());
        System.out.println(String.format("[%s]: %s", this.name, receivedMessage));
    }
}
