package mk.ukim.finki.os.av6.threadPool;

import java.util.HashSet;

public class TCPClientsSpawner {
    private static int NUM_CLIENTS = 100;

    public static void main(String[] args) throws InterruptedException {
        System.out.println(String.format("Spawning %d clients", NUM_CLIENTS));
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
    }
}
