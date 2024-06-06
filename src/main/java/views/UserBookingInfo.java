package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controllers.SeatConfigScreenController;
import models.Movie;
import models.MovieTime;
import models.Seat;
import models.User;
import models.Zone;
import service.MovieService;
import utils.TimeZoneUtitls;

public class UserBookingInfo extends JFrame {

	private JPanel contentPane;
	public JTable bookingInfoTable;
	public DefaultTableModel bookingInfoTableModel;
	private JLabel bookingInfoLabel;
    private String movieName;
    private String timeZone;
 
	/**
	 * Launch the application.
	 */
	
	
	public String getListSeatAsString(List<String> listString)
	{
	   String str= "";
	   
	   for(int i = 0; i < listString.size(); i++)
	   {
		   if(i == listString.size()-1)
		   {
			   str += listString.get(i);
		   }
		   else
		   {
			   str += listString.get(i) + ", ";
		   }
	   }
	   return str;
	}

	public void initData()
	{
		
		if(timeZone != null && movieName != null)
		{
			List<LocalTime> listLocalTime = TimeZoneUtitls.getTimeZoneFromString(timeZone);
	        MovieTime movieTime = MovieService.getInstance().getMovieTimeFromTimeZone(movieName, listLocalTime.get(0), listLocalTime.get(1));
	        List<Zone> zones = movieTime.getMovieTheater().getListZone();
	        Map<User,List<String>> mapInfo = new HashMap<>();
	        for(Zone zone : zones)
	        {
	        	String nameZone = zone.getName();
	        	ArrayList<ArrayList<Seat>> arr2D = zone.getSeats();
	        	for(int i = 0 ; i < arr2D.size(); i++)
	        	{
	        		List<Seat> arrSeat = arr2D.get(i);
	        		for(int j = 0 ; j < arrSeat.size(); j++)
	        		{
	        			Seat seat = arrSeat.get(j);
	        			User user = seat.getUser();
	        			if(user != null)
	        			{
	        				 if(mapInfo.containsKey(user))
	 	        		    {
	 	        		    	mapInfo.get(user).add(nameZone + "-" + i + "-" + j);
	 	        		    }
	 	        		    else
	 	        		    {
	 	        		    	mapInfo.put(user, new ArrayList<>());
	 	        		    	mapInfo.get(user).add(nameZone + "-" + i + "-" + j);
	 	        		    }
	        			}
	        		}
	        	}
	        }
	        
	        int count =1;
	        bookingInfoTableModel.setRowCount(0);
	        for(User user : mapInfo.keySet())
	        {
	            bookingInfoTableModel.addRow(new Object[] {count++, user.getUsername(), user.getFullname(), user.getTel(), getListSeatAsString(mapInfo.get(user))});
	        }
		}
	}
	
	/**
	 * Create the frame.
	 */
	public UserBookingInfo(String movieName, String timeZone) {
		this.movieName = movieName;
		this.timeZone = timeZone;
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SeatConfigScreen.class.getResource("/assets/serverIcon.png")));
		setBounds(100, 100, 816, 525);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(236, 200, 123));
	
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		bookingInfoLabel = new JLabel("THÔNG TIN ĐẶT VÉ");
		bookingInfoLabel.setBounds(231, 10, 340, 37);
		bookingInfoLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		contentPane.add(bookingInfoLabel);
		
		bookingInfoTableModel = new DefaultTableModel();
		bookingInfoTableModel.addColumn("STT");
		bookingInfoTableModel.addColumn("Tài khoản");
		bookingInfoTableModel.addColumn("Họ và tên");
		bookingInfoTableModel.addColumn("Điện thoại");
		bookingInfoTableModel.addColumn("Vị trí");
	    
	    
		bookingInfoTable = new JTable(bookingInfoTableModel);
		
		bookingInfoTable.setBounds(27, 166, 745, 312);
		
	    JScrollPane sc = new JScrollPane(bookingInfoTable);
	    sc.setLocation(22, 73);
		sc.setSize(756, 405);
	    sc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(sc);
	
		setVisible(true);
		
		initData();
	}
}
