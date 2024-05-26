package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import Utils.TimeZoneSplitUtitls;
import controllers.ConfigServerScreenController;
import models.MovieTheater;
import models.MovieTime;
import models.Zone;
import service.MovieTimeService;

import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.List;
import java.awt.event.ActionEvent;

public class ConfigServerScreen extends JFrame {

	private JPanel contentPane;
	public JTextField timeStartText;
	public JTextField timeEndText;
	public JComboBox<String> movieTimeCombobox;
	private DefaultComboBoxModel<String> modelCombobox;
	private JLabel header; 
	private JLabel movieTimeLabel;
	private JPanel stageMapPanel;
	private JLabel userAccessNumLabel;
	private JLabel timeStartLabel;
	private JLabel movieTimeConfigLabel;
	private JLabel timeEndLabel;
	public JButton createTimeBtn;
	public JButton deleteTimeBtn;
	public JButton stageConfigButton;
	private JPanel screenMoviePanel;
	private JLabel lblNewLabel;
	private MovieTimeService movieTimeService;
	
	/**
	 * Launch the application.
	*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigServerScreen frame = new ConfigServerScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void refresh()
	{
		this.repaint();
	}

	public void drawSeatMap(MovieTheater movieTheater)
	{
	    stageMapPanel.removeAll();
		List<Zone> zones = movieTheater.getListZone();
		JPanel zonePannel = new JPanel(new GridLayout(zones.size(), 1, 10, 5));
		
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
	                seatButton.setBackground(Color.GREEN);
	                seatButton.setOpaque(true);
	                seatButton.setBorderPainted(false);
	                seatPanel.add(seatButton);
	            }
	        }
	        zonePannel.add(seatPanel);
	    }
        stageMapPanel.add(zonePannel);
        stageMapPanel.revalidate();
        stageMapPanel.repaint();
	}
	
	
	
	public void updateMovieTimeCombobox()
	{
		try {
			modelCombobox.removeAllElements();
			List<String> movieTimes = movieTimeService.getMovieTimeAsString();
			for(String str : movieTimes)
			{
				modelCombobox.addElement(str);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateSeatMap()
	{
		// Update seat map
		String timeZoneValue = (String)movieTimeCombobox.getSelectedItem();
	
		if(timeZoneValue != null)
		{
			List<LocalTime> timeZoneComponents = TimeZoneSplitUtitls.getTimeZoneFromString(timeZoneValue);
			//
			if(timeZoneComponents.get(0) != null && timeZoneComponents.get(1) != null)
			{
				MovieTime movieTime = movieTimeService.getMovieTimeFromTimeZone(timeZoneComponents.get(0),timeZoneComponents.get(1));
				if(movieTime !=null)
				{
					
					drawSeatMap(movieTime.getMovieTheater());
				}
			}   	
		}			
	}

	public void initData()
	{
		updateMovieTimeCombobox();	
		updateSeatMap();
	}
	
	public void comboboxSelectionChange()
	{
	
		
		stageMapPanel = new JPanel();
		contentPane.add(stageMapPanel);
		stageMapPanel.setBounds(10, 138, 558, 398);
	    stageMapPanel.setLayout(new BorderLayout(0, 0));
		 
	    screenMoviePanel = new JPanel();
	    screenMoviePanel.setBackground(new Color(0, 0, 0));
        stageMapPanel.add(screenMoviePanel, BorderLayout.SOUTH);
        
        lblNewLabel = new JLabel("Màn hình chiếu phim");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        screenMoviePanel.add(lblNewLabel);
		initData();
	}
	
	public ConfigServerScreen()
	{
		
	}
}
