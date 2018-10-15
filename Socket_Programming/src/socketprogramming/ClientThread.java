package socketprogramming;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread{
	protected Socket socket;
	User objectToServer;
	//private ObjectOutputStream outputToServer;
	private ObjectInputStream inputFromServer;

	public ClientThread(Socket clientSocket) {
		this.socket = clientSocket;
		//this.objectToServer = object;
	}
	public void run() {
		try {

			System.out.println("Client started ");

			while (true) {
				// Listen for a new connection request
				//Socket socket = socket.accept();

				// Create an input stream from the socket
				inputFromServer = new ObjectInputStream(socket.getInputStream());
				
				// Create an input stream from the socket
				//outputToServer = new ObjectOutputStream(socket.getOutputStream());

				// Read from input
				User objectFromServer = (User) inputFromServer.readObject();
				
				// Write output
				//outputToServer.writeObject(objectToServer);

				
				//System.out.println("A new student object is stored");
				System.out.println("from server: " + objectFromServer.getName());
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
				//outputToServer.close();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
