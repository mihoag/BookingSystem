package threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import models.Movie;
import models.MovieTime;

public class ClientReadThread extends Thread {
	private ObjectInputStream reader;
	private Socket socket;
	private ClientThread client;

	public ClientReadThread(Socket socket, ClientThread client) {
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
	    Object response;
		try {
			response =  reader.readObject();
			
			if(response instanceof List<?>)
			{
				client.initData((List<Movie>)response);   	
			}
	        while(true)
	        {
	        	response = reader.readObject();
	        	if(response instanceof List<?>)
	        	{
	        	   client.updateData((List<Movie>)response);
	        	}
	        	else if(response instanceof String)
	        	{
	        		if(response.equals("disconnect"))
	        		{
	        			client.showDisconnectedMessage();
	        			break;
	        		}
	        	}
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
		} 	
		}	
}
