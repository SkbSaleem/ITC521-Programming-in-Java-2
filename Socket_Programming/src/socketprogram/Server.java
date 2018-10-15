package socketprogram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;


public class Server {
	private static ServerSocket serverSocket = null; // Defining the server socket.
	private static Socket clientSocket = null; 	// Defining the client socket.

	public static void main(String args[]) throws IOException {
		int portNumber = 8001;
		/* Open a server socket on the portNumber given in the command line argument.
		 */
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println(e);
		}
		while (true) {
			clientSocket = serverSocket.accept();
		}
	}
}







class clientThread extends Thread {
	private Socket clientSocket = null;
	
	// constructor
	public clientThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	} // end constructor clientThread

	public void run() {
		
	}
}