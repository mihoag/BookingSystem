package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import models.User;
import repository.UserRepository;
import service.UserService;
import views.BookingUserScreen;
import views.LoginScreen;
import views.RegisterScreen;

public class LoginScreenController implements ActionListener{

	private LoginScreen view;
	private UserService userService;
	public LoginScreenController(LoginScreen view)
	{
		this.view = view;
		userService = new UserService();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	   if(e.getSource() == view.registerBtn)
	   {
		   new RegisterScreen(view);
		   view.setVisible(false);
	   }
	   else if(e.getSource() == view.loginBtn)
	   {
		   String username = view.usernameTextField.getText();
		   String password = view.passwordTextField.getText();
		   
		   Boolean check = false;
		   User user = userService.getUserByUsername(username);
		   if(user != null)
		   {
			   if(user.getPassword().equals(password))
			   {
				   check = true;
			   }
		   }
		   if(check)
		   {
			   view.setVisible(false);   
			   new BookingUserScreen(user);
		   }
		   else
		   {
			    JOptionPane.showMessageDialog(view, "Đăng nhập thất bại. Tài khoản hoặc mật khẩu không đúng!"); 
		   }
	   }
		
	}

}
