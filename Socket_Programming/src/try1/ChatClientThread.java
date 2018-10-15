package try1;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClientThread extends Thread {
	private Socket socket = null;
	private ChatClient client = null;
	private DataInputStream streamIn = null;

	// Constructor
	public ChatClientThread(ChatClient _client, Socket _socket) {
		client = _client;
		socket = _socket;
		open();  
		start();
	}
	
	public void open() {
		try {
			streamIn  = new DataInputStream(socket.getInputStream());
		}
		catch(IOException ioe) {
			System.out.println("Error getting input stream: " + ioe);
			client.stop();
		}
	} // open() end
	
	public void close() {
		try {
			if (streamIn != null) streamIn.close();
		}
		catch(IOException ioe) {
			System.out.println("Error closing input stream: " + ioe);
		}
	} // close() end

	public void run() {
		while (true) {
			try {
				client.handle(streamIn.readUTF());
			}
			catch(IOException ioe) {
				System.out.println("Listening error: " + ioe.getMessage());
				client.stop();
			}
		}
	} // run() end
}