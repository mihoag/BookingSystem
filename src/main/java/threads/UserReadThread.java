package threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import models.MovieTime;

public class UserReadThread extends Thread {
	private ObjectInputStream reader;
	private Socket socket;
	private ClientThread client;

	public UserReadThread(Socket socket, ClientThread client) {
		this.socket = socket;
		this.client = client;

		try {
			reader = new ObjectInputStream(socket.getInputStream());
		} catch (IOException ex) {
			System.out.println("Error getting input stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void run() {
		List<MovieTime> response;
		try {
			response = (List<MovieTime>) reader.readObject();
	        client.initData(response);   
	        while(true)
	        {
	        	response = (List<MovieTime>) reader.readObject();
	            client.updateData(response);
	        }
			//client.test(response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		       
		while (true) {
			
		}
	}
	
	
}
