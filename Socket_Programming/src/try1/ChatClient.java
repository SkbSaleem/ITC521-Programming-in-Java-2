package try1;

import java.net.*;
import java.io.*;

public class ChatClient implements Runnable {
	private Socket socket = null;
	private Thread thread = null;
	private DataInputStream inputStream = null;
	private DataOutputStream outputStream = null;
	private ChatClientThread client = null;

	public ChatClient(String serverName, int serverPort) {
		System.out.println("Establishing connection. Please wait ...");
		try {
			socket = new Socket(serverName, serverPort);
			System.out.println("Connected: " + socket);
			start();
		}
		catch(UnknownHostException uhe) {  
			System.out.println("Host unknown: " + uhe.getMessage());
		}
		catch(IOException ioe) {  
			System.out.println("Unexpected exception: " + ioe.getMessage());
		}
	} // ChatClient end

	public void run() {
		while (thread != null) {
			try {
				outputStream.writeUTF(inputStream.readLine());
				outputStream.flush();
			}
			catch(IOException ioe) {
				System.out.println("Sending error: " + ioe.getMessage());
				stop();
			}
		}
	} // run() end

	public void handle(String msg) {
		if (msg.equals(".bye")) {
			System.out.println("Good bye. Press RETURN to exit ...");
			stop();
		}
		else
			System.out.println(msg);
	} // handle(String msg) end

	public void start() throws IOException {
		inputStream = new DataInputStream(System.in);
		outputStream = new DataOutputStream(socket.getOutputStream());
		if (thread == null) {
			client = new ChatClientThread(this, socket);
			thread = new Thread(this);                   
			thread.start();
		}
	} // start() end

	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
		try {  
			if (inputStream != null) inputStream.close();
			if (outputStream != null) outputStream.close();
			if (socket != null) socket.close();
		}
		catch(IOException ioe) {
			System.out.println("Error closing ...");
		}
		client.close();  
		client.stop();
	} // stop() end

	public static void main(String args[]) {
		ChatClient client = new ChatClient("0.0.0.0", 8002);
	}
}