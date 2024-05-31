package threads;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JOptionPane;

import models.MovieTime;
import views.BookingUserScreen;

public class ClientThread extends Thread {
   private BookingUserScreen view;
   private final String hostname = "localhost";
   private final Integer port = 3000;
   private UserReadThread userReadThread;
   public UserWriteThread userWriteThread;
   
   public ClientThread(BookingUserScreen view)
   {
	   this.view = view;
   }
   
    @Override
	public void run() {
	   // TODO Auto-generated method stub
	   try {
			Socket socket = new Socket(hostname, port);
			System.out.println("Connected to the chat server");
			
			userReadThread = new UserReadThread(socket, this);
			userReadThread.start();
			userWriteThread = new UserWriteThread(socket, this);
		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O Error: " + ex.getMessage());
		}
	}
    
    
    public void initData(List<MovieTime> movieTimes)
    {
    	view.initData(movieTimes);
    }
    
    public void updateData(List<MovieTime> movieTimes)
    {
        view.updateData(movieTimes);
    }
    
    public void test(List<MovieTime> rs)
    {
    	JOptionPane.showConfirmDialog(view,rs);
    }
   
   
}
