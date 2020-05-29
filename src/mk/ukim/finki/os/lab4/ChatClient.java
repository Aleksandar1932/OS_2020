package mk.ukim.finki.os.lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

class MessageReader extends Thread {
	private static BufferedReader in;
	private static Boolean active;

	public MessageReader(BufferedReader in) {
		MessageReader.in = in;
		active = true;
	}

	public static void stopMessageReader() {
		active = false;
		System.out.println("[MessageReader] has been stopped");
	}

	@Override
	public void run() {
		try {
			startMessageReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void startMessageReader() throws IOException {
		while (active) {
			String message = in.readLine();
			if(message == null){
				System.out.println("Server closed the connection");
				stopMessageReader();
			}
			else {
				System.out.println(String.format("[SERVER] %s", message));
			}
		}
	}
}

public class ChatClient extends Thread {
	static final String ADDRESS = "localhost";
	static final Integer PORT = 6868;

	static final String CLOSE_READER_KEYWORD = "CloseReader";
	static final String CLOSE_CHAT_KEYWORD = "CloseChat";

	@Override
	public void run() {
		try {
			startChatClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void startChatClient() throws IOException {
		InetAddress address = InetAddress.getByName(ADDRESS);
		Socket client = new Socket(address, PORT);
		System.out.println("Connected to server!");

		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);

		BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in));

		MessageReader messageReader = new MessageReader(in);
		messageReader.start();
		while (true) {
			String message = clientInput.readLine();
			if (message.contains(CLOSE_READER_KEYWORD)) {
				MessageReader.stopMessageReader();
			}
			if (message.contains(CLOSE_CHAT_KEYWORD)){
				MessageReader.stopMessageReader();
				client.close();
				break;
			}
			out.println(message);
		}

	}

	public static void main(String[] args) {
		// Start nc -l 6868 in Terminal for testing purposes
		ChatClient client1 = new ChatClient();
		client1.start();
	}
}