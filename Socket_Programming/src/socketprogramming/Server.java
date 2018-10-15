package socketprogramming;

import java.io.*;
import java.net.*;

public class Server {
	static final int PORT = 8001;

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true) {
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println("I/O error: " + e);
			}
			// thread for each client
			new ServerThread(socket).start();
		}

	}


}