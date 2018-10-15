package socketprogramming;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
	protected Socket socket;
	private ObjectOutputStream outputToClient;
	private ObjectInputStream inputFromClient;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			System.out.println("Server started ");

			// Create an object ouput stream
			//outputToFile = new ObjectOutputStream(new FileOutputStream("student.dat", true));

			while (true) {
				// Listen for a new connection request
				//Socket socket = socket.accept();

				// Create an input stream from the socket
				inputFromClient = new ObjectInputStream(socket.getInputStream());
				
				// Create an input stream from the socket
				outputToClient = new ObjectOutputStream(socket.getOutputStream());

				// Read from input
				User object = (User) inputFromClient.readObject();
				
				// Write output
				outputToClient.writeObject(object);

				
				System.out.println("A new student object is stored");
				System.out.println(object.getName());
			}
		}
		catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				inputFromClient.close();
				outputToClient.close();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}


	}
}
