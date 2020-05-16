package mk.ukim.finki.os.ispitni.networkingFileCounter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server extends Thread {
    Integer port;
    String logPath;
    Boolean active;
    ServerSocket serverSocket;

    public Server(Integer port, String logPath) {
        this.port = port;
        this.logPath = logPath;
        active = true;
        serverSocket = null;
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
        this.serverSocket = new ServerSocket(port);

        while (active) {
            Socket clientSocket = this.serverSocket.accept();
            //TODO: Spawn server worker
        }
    }
}
