package threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.MovieTime;
import views.ConfigServerScreen;

public class ServerThread extends Thread { 
   private Set<UserThread> userThreads = new HashSet<>();
   private ConfigServerScreen view;
   private static Integer port = 3000;
   
   public ServerThread(ConfigServerScreen view)
   {
	   this.view = view;
   }
   
   public synchronized void updateUserNum()
   {
	   int num = userThreads.size();
	   view.updateUserNum(num);
   }
   
    @Override
	public void run() {
		// TODO Auto-generated method stub
	   try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Chat Server is listening on port " + port);
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("New user connected");
				UserThread newUser = new UserThread(socket, this);
				userThreads.add(newUser);
				updateUserNum();
				newUser.start();
			}

		} catch (IOException ex) {
			System.out.println("Error in the server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
   
   	public void broadcast(List<MovieTime> movieTimes)
   	{
   		for (UserThread aUser : userThreads) {
   			aUser.sendUpdatedData(movieTimes);
   		}
   	}
   	
   	public void updateView(List<MovieTime> movieTimes)
   	{
   	   
   	}
}
