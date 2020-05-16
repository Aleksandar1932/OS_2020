package mk.ukim.finki.os.ispitni.networkingFileCounterMishev;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;


public class SocketWorker extends Thread {
    private Socket socket = null;
    private BufferedWriter writer = null;

    public SocketWorker(Socket socket, BufferedWriter writer) {
        this.socket = socket;
        this.writer = writer;
    }

    @Override
    public void run() {
        try {
            receiveData(this.socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveData(Socket socket) throws IOException {
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int numFiles = dis.readInt();
        synchronized (SocketWorker.class) {
            writer.write(String.format("%s %d %d", socket.getInetAddress().getHostAddress(),
                    socket.getPort(), numFiles));
            writer.newLine();
            writer.flush();
        }

    }
}
