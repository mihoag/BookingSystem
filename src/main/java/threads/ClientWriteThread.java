package threads;

import java.io.Console;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientWriteThread extends Thread {
	private  ObjectOutputStream writer = null;
	private Socket socket;

	public ClientWriteThread(Socket socket) {
		this.socket = socket;
		
		try {
			writer = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException ex) {
			System.out.println("Error getting output stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void run() {
		while(true)
		{
		}
	}
	
	public void sendBookingDataToServer(String bookingInfo)
	{
		try {
			writer.writeObject(bookingInfo);
			if(bookingInfo.equals("disconnected"))
			{
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
