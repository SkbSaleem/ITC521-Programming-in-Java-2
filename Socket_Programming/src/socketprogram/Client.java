package socketprogram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable{
	static Socket socket = null;
	static String host = "localhost";
	static int port = 25000;

	public static void clientSender(String number) {
		try {
			InetAddress address = InetAddress.getByName(host);
			socket = new Socket(address, port);

			//Send the message to the server
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);

			//String number = "2";

			String sendMessage = number + "\n";
			bw.write(sendMessage);
			bw.flush();
			System.out.println("Message sent to the server : "+sendMessage);


		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		finally {
			//Closing the socket
			try {
				socket.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void clientListner() {
		System.out.println("client listener running");
		try {
			InetAddress address = InetAddress.getByName(host);
			socket = new Socket(address, port);

			//Get the return message from the server
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String message = br.readLine();
			System.out.println("Message received from the server : " +message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // 

	@Override
	public void run() {
		String responseLine;
		try {
			while (true) {
				if (responseLine.equals("logout")) { break;} // when server replies "logout", break the close the client input from keyboard
				System.out.println(responseLine);
			}
		} catch (IOException e) {}		
	}





	//public static void main(String args[]) {

	//}
}