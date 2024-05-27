package views;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.LoginScreenController;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

public class LoginScreen extends JFrame {

	private JPanel contentPane;
	public JTextField usernameTextField;
	public JTextField passwordTextField;
	private JPanel imagePanel;
	private JLabel userNameLabel;
	private JLabel lblMtKhu;
	public JButton loginBtn;
	public JButton registerBtn;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen frame = new LoginScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginScreen() {
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
		
		lblMtKhu = new JLabel("Mật khẩu");
		lblMtKhu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMtKhu.setBounds(103, 247, 141, 37);
		contentPane.add(lblMtKhu);
		
		passwordTextField = new JTextField();
		passwordTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordTextField.setColumns(10);
		passwordTextField.setBounds(254, 249, 242, 39);
		contentPane.add(passwordTextField);
		
		LoginScreenController ac = new LoginScreenController(this);
		
		loginBtn = new JButton("Đăng nhập");
		loginBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		loginBtn.setBounds(254, 314, 139, 37);
		loginBtn.addActionListener(ac);
		contentPane.add(loginBtn);
		
		registerBtn = new JButton("Đăng kí");
		registerBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		registerBtn.setBounds(254, 362, 139, 37);
		registerBtn.addActionListener(ac);
		contentPane.add(registerBtn);
	}
}
