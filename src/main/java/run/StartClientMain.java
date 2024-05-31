package run;

import java.awt.EventQueue;

import views.LoginScreen;

public class StartClientMain {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen screen = new LoginScreen();
					screen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
