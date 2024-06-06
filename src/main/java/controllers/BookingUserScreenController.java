package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import threads.ClientWriteThread;
import utils.SeatUtils;
import views.BookingUserScreen;

public class BookingUserScreenController implements ActionListener{

 	private BookingUserScreen view;
 	
 	public BookingUserScreenController(BookingUserScreen view)
 	{
 		this.view = view;
 	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() instanceof JButton)
		{
			String movieName = (String) view.movieNameCombobox.getSelectedItem();
			String username = view.user.getUsername();
			String zoneTime = (String) view.movieTimeCombobox.getSelectedItem();
			String seatId = e.getActionCommand();
			
			String bookingInfo = username + "|" + zoneTime + "|" + movieName + "|"+ seatId;
			
			view.clientThread.userWriteThread.sendBookingDataToServer(bookingInfo);
		}
	}

}
