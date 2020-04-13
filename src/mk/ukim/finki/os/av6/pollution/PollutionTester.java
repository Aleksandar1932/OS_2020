package mk.ukim.finki.os.av6.pollution;

public class PollutionTester {
    public static void main(String[] args) throws InterruptedException {
        final int SERVER_PORT = 8086;

        UDPServer udpServer = new UDPServer(SERVER_PORT);
        MeasuringStation ms1 = new MeasuringStation("Measuring Station 1", "localhost", SERVER_PORT);
        APIClient apiClient1 = new APIClient("API1", "localhost", SERVER_PORT);
        udpServer.start();
        Thread.sleep(1000);

        ms1.start();
        apiClient1.start();
    }
}
