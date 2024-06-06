package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import models.User;
import service.UserService;
import utils.HashUtils;
import views.LoginScreen;
import views.RegisterScreen;

public class RegisterScreenController implements ActionListener{
     

	private RegisterScreen view;
	private LoginScreen loginView;
	private UserService service;
	
	
	public RegisterScreenController(RegisterScreen view, LoginScreen loginView)
	{
		this.loginView = loginView;
		this.view = view;
		service = new UserService();
	}
	
	private boolean checkValidData(String username, String password, String fullname)
	{
		if(username == null || username.equals("") || username.contains(" "))
		{
		   JOptionPane.showMessageDialog(view, "Tên đăng nhập không hợp lệ");
		   return false;
		}

		if(username.contains("\\|"))
		{
		   JOptionPane.showMessageDialog(view, "Tên đăng nhập không được bao gồm kí tự |");
		   return false;
		}
		
		if(password == null || password.equals("") || password.contains(" "))
		{
		   JOptionPane.showMessageDialog(view, "Mật khẩu không hợp lệ");
		   return false;
		}
		
		if(fullname != null && fullname.equals(""))
		{
			JOptionPane.showMessageDialog(view, "Họ và tên không hợp lệ");
			return false;
		}
		
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	    if(e.getSource() == view.registerBtn)
	    {
	    	String username = view.usernameTextField.getText();
	    	String password = view.passwordTextField.getText();
	    	String fullname = view.fullnameTextField.getText();
	    	String tel = view.telTextField.getText();
	    	
	    	boolean check = checkValidData(username, password, fullname);
	    	if(check)
	    	{
	    		User user = new User(username, HashUtils.hashString(password), tel,fullname);
	    		Boolean checkRegister = service.addUser(user);
	    		if(checkRegister)
	    		{
	    			JOptionPane.showMessageDialog(view, "Đăng kí thành công. Quay lại màn hình đăng nhập!");
	    		}
	    		else
	    		{
	    			JOptionPane.showMessageDialog(view, "Tài khoản đã tồn tại!");	
	    		}	    		
	    	}
	    	
	    }
	    else if(e.getSource() == view.loginBtn)
	    {
	    	loginView.setVisible(true);
	    	view.setVisible(false);
	    }
	}

}
