package threads;

import java.io.Console;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class UserWriteThread extends Thread {
	private ObjectOutputStream writer;
	private Socket socket;
	private ClientThread client;
	private String bookingInfo;

	public UserWriteThread(Socket socket, ClientThread client) {
		this.socket = socket;
		this.client = client;
		try {
			writer = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException ex) {
			System.out.println("Error getting output stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void bookingInfo(String info)
	{
		this.bookingInfo = info;
	}

	public void run() {
	    try {
			writer.writeObject(bookingInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
