package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.BookingUserScreenController;
import models.Movie;
import models.MovieTheater;
import models.MovieTime;
import models.Seat;
import models.User;
import models.Zone;
import repository.MovieTimeRepository;
import service.MovieService;
import threads.ClientThread;
import utils.MovieUtils;
import utils.TimeZoneUtitls;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JTextField;
import javax.swing.ComboBoxModel;

public class BookingUserScreen extends JFrame {

	private JPanel contentPane;
    private DefaultComboBoxModel<String> movieTimeModelCombobox;
    public JComboBox<String> movieTimeCombobox;
	private JLabel movieTimeLabel;
	private JLabel nameLabel;
	private JLabel headerLabel;
    private JPanel movieStagePanel;
    private JPanel movieScreenPanel;
    private JLabel movieScreenTitleLabel;
    public User user;
    private List<Movie> movies;
    public ClientThread clientThread;
    private JLabel movieNameLabel;
    private DefaultComboBoxModel<String> movieNameModelCombobox;
    public JComboBox<String> movieNameCombobox;
    private JLabel lblImage;
    private JPanel imagePanel;
	
   
    public void updateMovieCombobox(String movieName)
	{
		movieNameModelCombobox.removeAllElements();	
		for(Movie movie : movies)
		{
			movieNameModelCombobox.addElement(movie.getMovieName());
		}
		
		if(movieName != null)
		{
			if(MovieUtils.checkExistMovieName(movies, movieName))
			{
				movieNameModelCombobox.setSelectedItem(movieName);
			}
		}
		String timeZone = (String) movieTimeCombobox.getSelectedItem();
		updateMovieTimeCombobox(timeZone);
	}
    
	public void updateMovieTimeCombobox(String timeZoneValue)
	{
		String movieName = (String) movieNameModelCombobox.getSelectedItem();
	    Movie movie = MovieUtils.getMovieFromName(movies, movieName);
	    
		movieTimeModelCombobox.removeAllElements();
		
		if(movie != null)
		{
			List<MovieTime> listMovieTimes = movie.getMovieTimes();
			List<String> movieTimesAsString = TimeZoneUtitls.getListTimeZoneAsString(listMovieTimes);
			
			for(String str :  movieTimesAsString)
			{
				movieTimeModelCombobox.addElement(str);
			}
			if(timeZoneValue != null)
			{
			    if(movieTimesAsString.contains(timeZoneValue))
			    {
			    	movieTimeModelCombobox.setSelectedItem(timeZoneValue);
			    }	
			}
		}
		updateSeatMap();		
	}
	
	
	public void updateSeatMap()
	{
		// Update seat map
		String timeZoneValue = (String)movieTimeCombobox.getSelectedItem();
		String movieName = (String) movieNameCombobox.getSelectedItem();
		if(timeZoneValue != null && movieName != null)
		{
			List<LocalTime> timeZoneComponents = TimeZoneUtitls.getTimeZoneFromString(timeZoneValue);
			//
			if(timeZoneComponents.get(0) != null && timeZoneComponents.get(1) != null)
			{
				Movie movie = MovieUtils.getMovieFromName(movies, movieName);
				List<MovieTime> movieTimes = movie.getMovieTimes();
				MovieTime movieTime = MovieUtils.getMovieTimeFromTimeZone(movieTimes,timeZoneComponents.get(0),timeZoneComponents.get(1));
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
	
	public void comboboxSelectionChange()
	{
		updateSeatMap();
	}
	
	public void addMovieScreenToPanel()
	{
		movieScreenPanel.removeAll();
		movieScreenPanel.add(movieScreenTitleLabel);
		movieStagePanel.add(movieScreenPanel, BorderLayout.SOUTH);
	}
	
	
	public void drawSeatMap(MovieTheater movieTheater)
	{
	    ActionListener ac = new BookingUserScreenController(this);
		
		movieStagePanel.removeAll();
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
	                   if(seat.getUser().getUsername().equals(user.getUsername()))
	                   {
	                	   seatButton.setBackground(Color.yellow);   
	                   }
	                   else
	                   {
	                	   seatButton.setBackground(Color.lightGray);   
	                   }
	                }
	                else
	                {
	                	seatButton.setBackground(Color.GREEN);
	                } 
	                seatButton.setOpaque(true);
	                seatButton.setBorderPainted(false);
	                seatButton.setText(zone.getName() + "-" + row + "-" + col);
	                seatButton.addActionListener(ac);
	                seatPanel.add(seatButton);
	            }
	        }
	        zonePannel.add(seatPanel);
	    }
		movieStagePanel.add(zonePannel, BorderLayout.CENTER);
		movieStagePanel.revalidate();
		movieStagePanel.repaint();
	}
	
	public void initData(List<Movie> movies)
	{
		System.out.println(movies.size());
		this.movies = movies;
		updateMovieCombobox(null);
	}
	
	public void updateData(List<Movie> movies)
	{
		this.movies = movies;
		String movieName = (String) movieNameCombobox.getSelectedItem();
		updateMovieCombobox(movieName);
	}

	
	
	public void addEventForComponent()
	{
		movieTimeCombobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateSeatMap();
			}
		});
		movieNameCombobox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateMovieTimeCombobox(null);
			}
		});

		
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
				int response = JOptionPane.showConfirmDialog(contentPane, 
			            "Bạn có muốn thoát khỏi ứng dụng?", 
			            "Xác nhận thoát", 
			            JOptionPane.YES_NO_OPTION, 
			            JOptionPane.QUESTION_MESSAGE);
			        
			        if (response == JOptionPane.YES_OPTION) {
			        	
			        	clientThread.userWriteThread.sendBookingDataToServer("disconnected");
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
	
	}
	
	public void showInfoDisconnect()
	{
		JOptionPane.showMessageDialog(contentPane, "Server đã tắt", "Thông báo đóng kết nối", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public BookingUserScreen(User user) {
		// Create components for UI
		this.user = user;
		clientThread = new ClientThread(this);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BookingUserScreen.class.getResource("/assets/serverIcon.png")));
		setBounds(100, 100, 805, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(236, 200, 123));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		headerLabel = new JLabel("HỆ THỐNG ĐẶT VÉ XEM PHIM");
		headerLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		headerLabel.setBounds(171, 0, 449, 57);
		contentPane.add(headerLabel);
		
		movieStagePanel = new JPanel();
		movieStagePanel.setBounds(10, 131, 768, 422);
		contentPane.add(movieStagePanel);
		movieStagePanel.setLayout(new BorderLayout(0, 0));
		
		movieScreenPanel = new JPanel();
		movieScreenPanel.setBackground(new Color(0, 0, 0));
		movieStagePanel.add(movieScreenPanel, BorderLayout.SOUTH);
		
		movieScreenTitleLabel = new JLabel("Màn hình chiếu phim");
		movieScreenTitleLabel.setForeground(new Color(255, 255, 255));
		movieScreenTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		movieScreenPanel.add(movieScreenTitleLabel);
		
		ImageIcon img = new ImageIcon("src/main/java/assets/user.png");
        // Set the desired width and height
        int width = 30;  // Width in pixels
        int height = 30; // Height in pixels
        // Create a scaled version of the ImageIcon
        
        Image scaledImage = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
	    lblImage = new JLabel();
	    lblImage.setIcon(scaledIcon);
	    imagePanel = new JPanel();
	    imagePanel.setBackground(new Color(236, 200, 123));
	    imagePanel.setLocation(11, 44);
	    imagePanel.setSize(48, 48);
	    imagePanel.add(lblImage, BorderLayout.CENTER);
		contentPane.add(imagePanel);
		
		System.out.println(user.getFullname());
		nameLabel = new JLabel(user.getFullname());
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		nameLabel.setBounds(69, 48, 141, 31);
		contentPane.add(nameLabel);
		
		movieNameLabel = new JLabel("Tên phim");
		movieNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		movieNameLabel.setBounds(10, 85, 107, 38);
		contentPane.add(movieNameLabel);
		
	   
		movieNameModelCombobox = new DefaultComboBoxModel<>();
		movieNameCombobox = new JComboBox(movieNameModelCombobox);
		movieNameCombobox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		movieNameCombobox.setBounds(108, 88, 317, 33);
		contentPane.add(movieNameCombobox);
		
		
		movieTimeLabel = new JLabel("Suất chiếu");
		movieTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		movieTimeLabel.setBounds(465, 83, 134, 38);
		contentPane.add(movieTimeLabel);
		
		movieTimeModelCombobox = new DefaultComboBoxModel<String>();
		movieTimeCombobox = new JComboBox(movieTimeModelCombobox);
		movieTimeCombobox.setBounds(572, 88, 206, 33);
		contentPane.add(movieTimeCombobox);
		movieTimeCombobox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		movieTimeCombobox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    comboboxSelectionChange();	
			}
		});
		setVisible(true);
		
		/// Add event for components
		addEventForComponent();
		
		// Execute client thread
		clientThread.start();
	}
}
