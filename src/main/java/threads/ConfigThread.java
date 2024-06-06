package threads;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ConfigThread extends Thread {
	private Socket socket;
	private ServerThread server;
	
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
