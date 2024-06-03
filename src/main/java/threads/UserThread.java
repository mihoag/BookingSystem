package threads;

import java.io.*;
import java.net.*;
import java.util.*;

import models.MovieTime;
import service.MovieTimeService;
import service.UserService;
import views.ConfigServerScreen;

/**
 * This thread handles connection for each connected client, so the server
 * can handle multiple clients at the same time
 */
public class UserThread extends Thread {
	private Socket socket;
	private ServerThread server;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	private MovieTimeService service;

	public UserThread(Socket socket, ServerThread server) 
	{
		this.socket = socket;
		this.server = server;
		service = new MovieTimeService();
	}
	
	public void run() {
		try {
			writer = new ObjectOutputStream(socket.getOutputStream()); 	
		     // Send initial data and send to client
			List<MovieTime> listMovieTimes = service.getMovieTimes();
			writer.writeObject(listMovieTimes);
			writer.flush();
		
			reader = new ObjectInputStream(socket.getInputStream());
			do {
			   String bookingInfo = (String) reader.readObject();
			   if(bookingInfo.equals("disconnected"))
			   {
				   break;
			   }
			   service.bookMovieSeat(bookingInfo);
			   List<MovieTime> listUpdateMovieTime = service.getMovieTimes();
			   server.broadcast(listUpdateMovieTime);
			   server.updateView(listUpdateMovieTime);
			} while (true);
			
			socket.close();
			server.removeUser(this);
			server.updateUserNum();
		} catch (IOException ex) {
			System.out.println("Error in UserThread: " + ex.getMessage());
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	  * Sends a object to the client.
	*/
	
	void sendUpdatedData(List<MovieTime> data) {
		try {
			writer.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(String message)
	{
		try {
			writer.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}