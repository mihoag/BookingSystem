package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controllers.ConfigServerScreenController;
import models.Movie;
import models.MovieTheater;
import models.MovieTime;
import models.Seat;
import models.User;
import models.Zone;
import service.MovieService;
import service.MovieTimeService;
import threads.ServerThread;
import threads.UserThread;
import utils.MovieUtils;
import utils.TimeZoneUtitls;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.ComboBoxModel;

public class ConfigServerScreen extends JFrame {
 
	private static final long serialVersionUID = 1L;
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	// Components for UI
	private JLabel header; 
	private JPanel stageMapPanel;
	private JLabel movieTimeLabel;
	public DefaultComboBoxModel<String> MovieTimeModelCombobox;
	public JComboBox movieTimeCombobox;
	public JButton stageConfigButton;
	private JLabel userAccessNumLabel;
	private JLabel movieTimeConfigLabel;
	private JLabel timeStartLabel;
	private JLabel timeEndLabel;
	public JButton createTimeBtn;
	public JButton deleteTimeBtn;	
	private MovieTimeService movieTimeService;
	public JTextField timeStartText;
	public JTextField timeEndText;
	private JPanel movieScreenPanel;
	private JLabel movieScreenTitleLabel;
	private JLabel userNumLabel;
	public ServerThread serverThread;
	public DefaultComboBoxModel<String> movieModelCombobox;
	public JComboBox movieNameCombobox;
	private JLabel labelMovieName;
	public JButton movieConfigButton;


	
	public void updateUserNum(Integer userNum)
	{
	   userNumLabel.setText(userNum + "");	
	}
	public void drawSeatMap(MovieTheater movieTheater)
	{
	    stageMapPanel.removeAll();
		List<Zone> zones = movieTheater.sortZoneASCByPrice();
		
		JPanel zonePannel = new JPanel(new GridLayout(zones.size(), 1, 15, 15));
		addMovieScreenToPanel();	
		for(Zone zone : zones)
		{
			int ROWS = zone.getRowNum();
		    int COLUMNS = zone.getSeatsPerRow();   
	        
		    JPanel seatPanel = new JPanel(new GridLayout(ROWS, COLUMNS, 5, 5));
		    seatPanel.setBackground(new Color(255, 255, 255));
		
	        // Add buttons to the panel
	        for (int row = 0; row < ROWS; row++) {
	            for (int col = 0; col < COLUMNS; col++) {
	                JButton seatButton = new JButton();
	                Seat seat = zone.getSeats().get(row).get(col);
	                if(seat.isStatus())
	                {
	                   seatButton.setBackground(Color.lightGray);
	                   // add event when clicking ( show user's info booking movie)
	                   User user = seat.getUser();
	                   String title = "Thông tin đặt vé";
	                   String zoneTime = (String) movieTimeCombobox.getSelectedItem();
	                   StringBuilder strBuilder = new StringBuilder();
	                   strBuilder.append("Suất chiếu: ").append(zoneTime).append("\n").append("Họ tên người đặt: ").append(user.getFullname()).append("\n").append("Số điện thoại: ").append(user.getTel());
	                   seatButton.addActionListener(new ActionListener() {
					  
	                   @Override
					   public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							JOptionPane.showMessageDialog(stageMapPanel,strBuilder.toString(), title, JOptionPane.INFORMATION_MESSAGE);
					   }
					});
	                }
	                else
	                {
	                   seatButton.setBackground(Color.GREEN);
	                }
	                seatButton.setOpaque(true);
	                seatButton.setBorderPainted(false);
	                seatButton.setText(zone.getName() + "-" + row + "-" + col);
	                seatPanel.add(seatButton);
	            }
	        }
	        zonePannel.add(seatPanel);
	    }
        stageMapPanel.add(zonePannel, BorderLayout.CENTER);
        stageMapPanel.revalidate();
        stageMapPanel.repaint();
	}
	
	public void updateMovieTimeCombobox(String timeZoneValue)
	{
		try {
			MovieTimeModelCombobox.removeAllElements();
			
			String movieName = (String) movieModelCombobox.getSelectedItem();

			List<String> movieTimes = MovieService.getInstance().getMovieTimeAsString(movieName);
			
			for(String str : movieTimes)
			{
				MovieTimeModelCombobox.addElement(str);
			}
			
			if(timeZoneValue != null && movieTimes.contains(timeZoneValue))
			{
				MovieTimeModelCombobox.setSelectedItem(timeZoneValue);
			}
			updateSeatMap();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	public void updateTextField()
	{
		String movieTime = (String) movieTimeCombobox.getSelectedItem();
		if(movieTime != null)
		{
			String[] components = TimeZoneUtitls.splitTimeZone(movieTime);
			setTextField(components[0], components[1]);
		}
		else
		{
			resetTextField();
		}
		
	}
	
	public void updateMovieCombobox(String movieName)
	{
		try {
			movieModelCombobox.removeAllElements();
			List<String> movieNames = MovieService.getInstance().getMovieAsString();
			for(String str : movieNames)
			{
				movieModelCombobox.addElement(str);
			}
			
			if(movieName != null)
			{
				if(movieNames.contains(movieName))
				{
					movieModelCombobox.setSelectedItem(movieName);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String timeZone = (String) movieTimeCombobox.getSelectedItem();
		updateMovieTimeCombobox(timeZone);
	}
	
	public void updateSeatMap()
	{
		// Update seat map
		String movieName = (String) movieModelCombobox.getSelectedItem();
		String timeZoneValue = (String)movieTimeCombobox.getSelectedItem();

		if(movieName != null && timeZoneValue != null)
		{
			
			List<LocalTime> timeZoneComponents = TimeZoneUtitls.getTimeZoneFromString(timeZoneValue);
			// Set text for text field
			setTextField(timeZoneComponents.get(0).format(formatter), timeZoneComponents.get(1).format(formatter));
			
			if(timeZoneComponents.get(0) != null && timeZoneComponents.get(1) != null)
			{
				MovieTime movieTime = MovieService.getInstance().getMovieTimeFromTimeZone(movieName, timeZoneComponents.get(0),timeZoneComponents.get(1));
				if(movieTime !=null)
				{	
					drawSeatMap(movieTime.getMovieTheater());
				}
			}   	
		}
		else
		{
			drawSeatMap(new MovieTheater());
		}
	}
	
	public void initData()
	{
		updateMovieCombobox(null);
		updateTextField();
	}
	
	public void updateView()
	{	
		String movieName = (String) movieNameCombobox.getSelectedItem();
		updateMovieCombobox(movieName);
	}
	
	public void comboboxSelectionChange()
	{
		updateSeatMap();
	}
	
	public void addMovieScreenToPanel()
	{
		movieScreenTitleLabel = new JLabel("Màn hình chiếu phim");
		movieScreenTitleLabel.setForeground(new Color(255, 255, 255));
		movieScreenTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		movieScreenPanel.removeAll();
		movieScreenPanel.add(movieScreenTitleLabel);
		stageMapPanel.add(movieScreenPanel, BorderLayout.SOUTH);
	}
	
	public void resetTextField()
	{
		this.timeStartText.setText("");
		this.timeEndText.setText("");
	}
	
	public void setTextField(String fromTime, String toTime)
	{
		this.timeStartText.setText(fromTime);
		this.timeEndText.setText(toTime);
	}
	
	public void addEventForComponent()
	{ 
       
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				int response = JOptionPane.showConfirmDialog(stageMapPanel, 
			            "Bạn có muốn thoát khỏi ứng dụng?", 
			            "Xác nhận thoát", 
			            JOptionPane.YES_NO_OPTION, 
			            JOptionPane.QUESTION_MESSAGE);
			        
			        if (response == JOptionPane.YES_OPTION) {
			            serverThread.sendMessageToClients("disconnect");
			            dispose();
			            // Optionally, exit the application
			            System.exit(0);
			        }
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	    movieNameCombobox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			    updateMovieTimeCombobox(null);	
			}
		});
	    
	    movieTimeCombobox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	   movieTimeCombobox.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			updateSeatMap();
		}
	});
	    
	}
	
	public ConfigServerScreen()
	{	
		// Create components for UI
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConfigServerScreen.class.getResource("/assets/serverIcon.png")));
		setResizable(false);
		getContentPane().setBackground(new Color(236, 200, 123));
		getContentPane().setLayout(null);
		serverThread = new ServerThread(this);
		ConfigServerScreenController ac = new ConfigServerScreenController(this);
		movieTimeService = new MovieTimeService();
		
		header = new JLabel("HỆ THỐNG QUẢN LÍ ĐẶT VÉ XEM PHIM");
		header.setFont(new Font("Tahoma", Font.BOLD, 30));
		header.setBounds(170, 10, 753, 64);
		getContentPane().add(header);
		
		stageMapPanel = new JPanel();
		stageMapPanel.setBounds(10, 141, 561, 392);
		getContentPane().add(stageMapPanel);
		stageMapPanel.setLayout(new BorderLayout(0, 0));
		
		movieScreenPanel = new JPanel();
		movieScreenPanel.setForeground(new Color(255, 255, 255));
		movieScreenPanel.setBackground(new Color(0, 0, 0));
		stageMapPanel.add(movieScreenPanel, BorderLayout.SOUTH);
		
		addMovieScreenToPanel();
		
		movieTimeLabel = new JLabel("Suất chiếu:");
		movieTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		movieTimeLabel.setBounds(581, 191, 141, 50);
		getContentPane().add(movieTimeLabel);
		
		MovieTimeModelCombobox = new DefaultComboBoxModel<>();
		movieTimeCombobox = new JComboBox(MovieTimeModelCombobox);
		movieTimeCombobox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		movieTimeCombobox.setBounds(718, 202, 229, 33);
		getContentPane().add(movieTimeCombobox);
		
		stageConfigButton = new JButton("Cấu hình sân khấu");
		stageConfigButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		stageConfigButton.setBounds(751, 148, 196, 33);
		stageConfigButton.addActionListener(ac);
		getContentPane().add(stageConfigButton);
		
		userAccessNumLabel = new JLabel("Số lượng người đang truy cập:");
		userAccessNumLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userAccessNumLabel.setBounds(581, 95, 272, 43);
		getContentPane().add(userAccessNumLabel);
		
		movieTimeConfigLabel = new JLabel("Cấu hình suất chiếu");
		movieTimeConfigLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		movieTimeConfigLabel.setBounds(581, 240, 287, 43);
		
		getContentPane().add(movieTimeConfigLabel);
		
		timeStartLabel = new JLabel("Giờ bắt đầu");
		timeStartLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		timeStartLabel.setBounds(581, 288, 133, 43);
		getContentPane().add(timeStartLabel);
		
		timeEndLabel = new JLabel("Giờ kết thúc");
		timeEndLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		timeEndLabel.setBounds(581, 335, 141, 43);
		getContentPane().add(timeEndLabel);
	
		createTimeBtn = new JButton("Thêm");
		createTimeBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		createTimeBtn.setBounds(657, 392, 85, 35);
		createTimeBtn.addActionListener(ac);
		getContentPane().add(createTimeBtn);
		
		deleteTimeBtn = new JButton("Xóa");
		deleteTimeBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deleteTimeBtn.setBounds(758, 392, 85, 35);
	    deleteTimeBtn.addActionListener(ac);
		getContentPane().add(deleteTimeBtn);
		
		timeStartText = new JTextField();
		timeStartText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		timeStartText.setBounds(718, 288, 229, 34);
		getContentPane().add(timeStartText);
		timeStartText.setColumns(10);
		
		timeEndText = new JTextField();
		timeEndText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		timeEndText.setColumns(10);
		timeEndText.setBounds(718, 341, 229, 34);
		getContentPane().add(timeEndText);
		
		userNumLabel = new JLabel("0");
		userNumLabel.setForeground(new Color(255, 0, 0));
		userNumLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userNumLabel.setBounds(863, 95, 31, 43);
		getContentPane().add(userNumLabel);
		
		labelMovieName = new JLabel("Tên phim:");
		labelMovieName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelMovieName.setBounds(10, 89, 141, 50);
		getContentPane().add(labelMovieName);
		
		movieModelCombobox = new DefaultComboBoxModel<>();
		movieNameCombobox = new JComboBox(movieModelCombobox);
		movieNameCombobox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		movieNameCombobox.setBounds(106, 98, 465, 33);
		getContentPane().add(movieNameCombobox);
		
		movieConfigButton = new JButton("Cấu hình phim");
		movieConfigButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		movieConfigButton.setBounds(581, 148, 161, 33);
		movieConfigButton.addActionListener(ac);
		getContentPane().add(movieConfigButton);
		this.setSize(976, 600);
		setBackground(new Color(236, 200, 123));
		
		
		setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	   
	    // Add event for components
	    addEventForComponent();
	    
	    // Init data for combobox and seat map
	    initData();
	    /// Execute server
	    serverThread.start();  
	}
}
