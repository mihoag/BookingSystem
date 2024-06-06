package threads;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JOptionPane;

import models.Movie;
import models.MovieTime;
import views.BookingUserScreen;

public class ClientThread extends Thread {
   private BookingUserScreen view;
   private final String hostname = "localhost";
   private final Integer port = 3000;
   public ClientReadThread userReadThread;
   public ClientWriteThread userWriteThread;
   public Socket socket;
   
   public ClientThread(BookingUserScreen view)
   {
	   this.view = view;
   }
   
    @Override
	public void run() {
	   // TODO Auto-generated method stub
	   try {
			socket = new Socket(hostname, port);
			System.out.println("Connected to the chat server");
			
			userReadThread = new ClientReadThread(socket, this);
			userReadThread.start();
			
			userWriteThread = new ClientWriteThread(socket);
			userWriteThread.start();
		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O Error: " + ex.getMessage());
		}
	}
    
    
    public void initData(List<Movie> movies)
    {
    	view.initData(movies);
    }
    
    public void updateData(List<Movie> movies)
    {
        view.updateData(movies);
    }
    
    public void showDisconnectedMessage()
    {
    	view.showInfoDisconnect();
    }
    
}
