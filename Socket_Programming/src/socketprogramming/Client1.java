package socketprogramming;

import java.io.*;
import java.net.*;

public class Client1 {
	static final int PORT = 8001;

	public static void main(String[] args) {
		new Server();
	}

	public Client1() {
		ServerSocket clientSocket = null;
		Socket socket = null;

		try {
			clientSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true) {
			try {
				socket = clientSocket.accept();
			} catch (IOException e) {
				System.out.println("I/O error: " + e);
			}
			// thread for each client
			new ServerThread(socket).start();
		}

	}


}