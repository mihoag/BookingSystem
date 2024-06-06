package run;

import java.awt.EventQueue;

import javax.swing.UIManager;

import views.LoginScreen;

public class StartClientMain {
	public static void main(String[] args) {
	    try {
	           UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	    } catch (Exception e) {
	           e.printStackTrace();
	    }
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
