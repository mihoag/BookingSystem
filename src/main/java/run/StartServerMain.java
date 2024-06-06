package run;

import java.awt.EventQueue;

import javax.swing.UIManager;

import views.ConfigServerScreen;

public class StartServerMain {
   public static void main(String[] args) {
	   try {
           UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
       } catch (Exception e) {
           e.printStackTrace();
       }
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
