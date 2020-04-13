package mk.ukim.finki.os.av6.pollution;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;

public class MeasuringStation extends Thread {
    String name;
    String host;
    int port;

    public MeasuringStation(String name, String host, int port) {
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

        // Sending data to server
        String messageToSend = "[MS];PM10:30:noTime;CO2:50:noTime;PM25:14:noTime";
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
