package run;

import java.awt.EventQueue;

import views.ConfigServerScreen;

public class StartServerMain {
   public static void main(String[] args) {
	   EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigServerScreen screen = new ConfigServerScreen();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
}
}
