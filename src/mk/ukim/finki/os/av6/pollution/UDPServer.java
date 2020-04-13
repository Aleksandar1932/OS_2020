package mk.ukim.finki.os.av6.pollution;

import jdk.jfr.DataAmount;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.time.LocalDate;
import java.util.*;

public class UDPServer extends Thread {
    int port;
    DatagramSocket server;
    boolean active;
    Map<String, List<Measurement>> measurements;

    UDPServer(int port) {
        this.port = port;
        this.server = null;
        this.active = true;
        this.measurements = new HashMap<>();

        measurements.put("PM10", new ArrayList<Measurement>());
        measurements.put("CO2", new ArrayList<Measurement>());
        measurements.put("PM25", new ArrayList<Measurement>());
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
        server = new DatagramSocket(port);

        while (active) {
            boolean sendFlag = true;

            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
            server.receive(packet);
            String receivedMessage = new String(packet.getData(), 0, packet.getLength());
            SocketAddress socketAddress = packet.getSocketAddress();

            String clientType = getClientType(receivedMessage);
            //If client is MeasuringStation getClientType will return MS
            if (clientType.equals("MS")) {
                //Process the measurements
                List<Measurement> receivedMeasurements = getListOfMeasurements(receivedMessage);
                storeMeasurements(receivedMeasurements);
                buffer = "OK".getBytes();
            } else if (clientType.equals("CL")) {
                //Serve the client
                buffer = "Ja me be glup klient".getBytes();
            } else {
                sendFlag = false;
                System.err.println(String.format("Undefined client type is sending requests. Client : %s", socketAddress.toString()));
            }

            //If client is Client getClientType will return CL
            //Return what it wants


            if (sendFlag) {
                DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, socketAddress);
                server.send(sendPacket);
            }

        }
    }

    private synchronized void storeMeasurements(List<Measurement> receivedMeasurements) {
        receivedMeasurements
                .forEach(m -> measurements.get(m.getParticleName()).add(m));
    }

    private List<Measurement> getListOfMeasurements(String rawMessage) {
        List<Measurement> measurements = new ArrayList<>();

        //`[MS];PM10:30:timestamp;CO2:50:timestamp;PM25:14:timestamp`
        Arrays.stream(rawMessage.split(";"))
                .skip(1)
                .forEach(measurementLine -> measurements.add(measurementGenerator(measurementLine)));

        return measurements;
    }

    private Measurement measurementGenerator(String measurementLine) {
        String[] args = measurementLine.split(":");
        return new Measurement(args[0], Integer.parseInt(args[1]), args[2]);
    }

    private String getClientType(String message) {
        if (message.startsWith("[MS]")) {
            return "MS";
        } else if (message.startsWith("[CL]")) {
            return "CL";
        } else {
            return "Invalid Client"; //TODO: replace with InvalidClientException;
        }
    }
}
