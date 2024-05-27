package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class RegisterScreen extends JFrame {

	private JPanel contentPane;
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	private JPanel imagePanel;
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	private JButton registerBtn;
	private JLabel fullnameLabel;
	private JTextField fullnameTextField;

	/**
	 * Create the frame.
	 */
	public RegisterScreen() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginScreen.class.getResource("/assets/serverIcon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 634, 516);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(236, 200, 123));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		imagePanel = new JPanel();
		imagePanel.setBackground(new Color(236, 200, 123));
		imagePanel.setBounds(197, 26, 196, 174);
		
		
		ImageIcon img = new ImageIcon("src/main/java/assets/user.png");
        // Set the desired width and height
        int width = 100;  // Width in pixels
        int height = 100; // Height in pixels
        // Create a scaled version of the ImageIcon
        Image scaledImage = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
	    JLabel lblImage = new JLabel();
	    lblImage.setIcon(scaledIcon);
	    imagePanel.add(lblImage, BorderLayout.CENTER);
		contentPane.add(imagePanel);
		
		userNameLabel = new JLabel("Tên đăng nhập");
		userNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userNameLabel.setBounds(103, 200, 141, 37);
		contentPane.add(userNameLabel);
		
		usernameTextField = new JTextField();
		usernameTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		usernameTextField.setBounds(254, 200, 242, 39);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		passwordLabel = new JLabel("Mật khẩu");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordLabel.setBounds(103, 250, 141, 37);
		contentPane.add(passwordLabel);
		
		passwordTextField = new JTextField();
		passwordTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordTextField.setColumns(10);
		passwordTextField.setBounds(254, 249, 242, 39);
		contentPane.add(passwordTextField);
		
		registerBtn = new JButton("Đăng kí");
		registerBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		registerBtn.setBounds(254, 379, 139, 37);
		contentPane.add(registerBtn);
		
		fullnameLabel = new JLabel("Họ và tên");
		fullnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fullnameLabel.setBounds(103, 299, 141, 37);
		contentPane.add(fullnameLabel);
		
		fullnameTextField = new JTextField();
		fullnameTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fullnameTextField.setColumns(10);
		fullnameTextField.setBounds(254, 298, 242, 39);
		contentPane.add(fullnameTextField);
		
		setVisible(true);
	}

}
