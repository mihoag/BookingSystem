package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.LoginScreen;
import views.RegisterScreen;

public class LoginScreenController implements ActionListener{

	private LoginScreen view;
	public LoginScreenController(LoginScreen view)
	{
		this.view = view;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	   if(e.getSource() == view.registerBtn)
	   {
		   new RegisterScreen();
	   }
	   else if(e.getSource() == view.loginBtn)
	   {
		   
	   }
		
	}

}
