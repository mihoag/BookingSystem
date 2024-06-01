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
import models.MovieTheater;
import models.MovieTime;
import models.Seat;
import models.User;
import models.Zone;
import repository.MovieTimeRepository;
import service.MovieTimeService;
import threads.ClientThread;
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

public class BookingUserScreen extends JFrame {

	private JPanel contentPane;
	private JPanel imagePanel;
    private DefaultComboBoxModel<String> modelCombobox;
    public JComboBox<String> movieTimeCombobox;
	private JLabel movieTimeLabel;
	private JLabel nameLabel;
	private JLabel headerLabel;
    private JPanel movieStagePanel;
    private JLabel lblImage;
    private JPanel movieScreenPanel;
    private MovieTimeService movieTimeService;
    private JLabel movieScreenTitleLabel;
    public User user;
    private List<MovieTime> movieTimes;
    public ClientThread clientThread;
	
    public MovieTime getMovieTimeFromTimeZone(List<MovieTime> movieTimes,LocalTime fromTimeLocalTime, LocalTime endTimeLocalTime)
    {
    	 for(MovieTime movieTime : movieTimes)
    	 {
    	   if(movieTime.getFromTime().equals(fromTimeLocalTime) && movieTime.getToTime().equals(endTimeLocalTime))
    	   {
    		   return movieTime;
    	   }
    	 }
    	 return null;
    }
    
    
	public void updateMovieTimeCombobox(String timeZoneValue)
	{
		List<String> timeZoneAsString = TimeZoneUtitls.getListTimeZoneAsString(movieTimes);
		modelCombobox.removeAllElements();
		for(String str : timeZoneAsString)
		{
		   modelCombobox.addElement(str);
		}
		if(timeZoneValue != null)
		{
			modelCombobox.setSelectedItem(timeZoneValue);
			updateSeatMap();
		}
	}
	
	
	public void updateSeatMap()
	{
		// Update seat map
		String timeZoneValue = (String)movieTimeCombobox.getSelectedItem();
		if(timeZoneValue != null)
		{
			List<LocalTime> timeZoneComponents = TimeZoneUtitls.getTimeZoneFromString(timeZoneValue);
			//
			if(timeZoneComponents.get(0) != null && timeZoneComponents.get(1) != null)
			{
				MovieTime movieTime = getMovieTimeFromTimeZone(movieTimes,timeZoneComponents.get(0),timeZoneComponents.get(1));
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
	
	public void initData(List<MovieTime> listMovieTimes)
	{
		movieTimes = listMovieTimes;
		updateMovieTimeCombobox(null);
		updateSeatMap();	
	}
	
	public void updateData(List<MovieTime> listMovieTimmes)
	{
		movieTimes = listMovieTimmes;
		String timeZoneValue = (String) movieTimeCombobox.getSelectedItem();
		List<String> timeZoneAsString = TimeZoneUtitls.getListTimeZoneAsString(movieTimes);
		if(!timeZoneAsString.contains(timeZoneValue))
		{
			timeZoneValue = null;
		}
	    updateMovieTimeCombobox(timeZoneValue);
	    updateSeatMap();
	}

	public BookingUserScreen()
	{
		
	}
	
	public void addEventForComponent()
	{
		movieTimeCombobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
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
	
	public BookingUserScreen(User user) {
		// Create components for UI
		this.user = user;
		this.movieTimeService = new MovieTimeService();
		clientThread = new ClientThread(this);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BookingUserScreen.class.getResource("/assets/serverIcon.png")));
		setBounds(100, 100, 805, 594);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(236, 200, 123));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		headerLabel = new JLabel("HỆ THỐNG ĐẶT VÉ XEM PHIM");
		headerLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		headerLabel.setBounds(171, 10, 449, 57);
		contentPane.add(headerLabel);
		
		movieStagePanel = new JPanel();
		movieStagePanel.setBounds(10, 125, 768, 422);
		contentPane.add(movieStagePanel);
		movieStagePanel.setLayout(new BorderLayout(0, 0));
		
		movieScreenPanel = new JPanel();
		movieScreenPanel.setBackground(new Color(0, 0, 0));
		movieStagePanel.add(movieScreenPanel, BorderLayout.SOUTH);
		
		movieScreenTitleLabel = new JLabel("Màn hình chiếu phim");
		movieScreenTitleLabel.setForeground(new Color(255, 255, 255));
		movieScreenTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		movieScreenPanel.add(movieScreenTitleLabel);
		
		imagePanel = new JPanel();
		imagePanel.setBackground(new Color(236, 200, 123));
		imagePanel.setBounds(534, 77, 67, 67);
		
		ImageIcon img = new ImageIcon("src/main/java/assets/user.png");
        // Set the desired width and height
        int width = 30;  // Width in pixels
        int height = 30; // Height in pixels
        // Create a scaled version of the ImageIcon
        Image scaledImage = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
	    lblImage = new JLabel();
	    lblImage.setIcon(scaledIcon);
	    imagePanel.add(lblImage, BorderLayout.CENTER);
		contentPane.add(imagePanel);
		
		System.out.println(user.getFullname());
		nameLabel = new JLabel(user.getFullname());
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		nameLabel.setBounds(599, 79, 141, 31);
		contentPane.add(nameLabel);
		
		
		modelCombobox = new DefaultComboBoxModel<String>();
		movieTimeCombobox = new JComboBox(modelCombobox);
		movieTimeCombobox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		movieTimeCombobox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    comboboxSelectionChange();	
			}
		});
		movieTimeCombobox.setBounds(122, 82, 218, 33);
		getContentPane().add(movieTimeCombobox);
		movieTimeLabel = new JLabel("Suất chiếu");
		movieTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		movieTimeLabel.setBounds(20, 77, 107, 38);
		contentPane.add(movieTimeLabel);
		setVisible(true);
		
		/// Add event for components
		addEventForComponent();
		
		// Execute client thread
		clientThread.start();
	}
}
