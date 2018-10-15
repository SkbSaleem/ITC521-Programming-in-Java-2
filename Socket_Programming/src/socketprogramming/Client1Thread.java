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

public class Client1Thread extends Thread{
	protected Socket socket;
	private ObjectOutputStream outputToServer;
	private ObjectInputStream inputFromServer;

	public Client1Thread(Socket serverSocket) {
		this.socket = serverSocket;
	}

	public void run() {
		try {
			System.out.println("Client started ");

			// Create an object ouput stream
			//outputToFile = new ObjectOutputStream(new FileOutputStream("student.dat", true));

			while (true) {
				// Listen for a new connection request
				//Socket socket = socket.accept();

				// Create an input stream from the socket
				inputFromServer = new ObjectInputStream(socket.getInputStream());
				
				// Create an input stream from the socket
				outputToServer = new ObjectOutputStream(socket.getOutputStream());

				// Read from input
				User object = (User) inputFromServer.readObject();
				
				// Write output
				outputToServer.writeObject(object);

				
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
				inputFromServer.close();
				outputToServer.close();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}


	}
}
