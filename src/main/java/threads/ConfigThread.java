package threads;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import service.MovieTimeService;

public class ConfigThread extends Thread {
	private Socket socket;
	private ServerThread server;
	private MovieTimeService service;
	
	public ConfigThread(Socket socket, ServerThread server)
	{
		this.socket = socket;
		this.server = server;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//super.run();
	}
}
