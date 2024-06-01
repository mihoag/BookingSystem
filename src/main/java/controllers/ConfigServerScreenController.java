package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.List;

import javax.swing.JOptionPane;

import models.MovieTime;
import service.MovieTimeService;
import utils.TimeZoneUtitls;
import views.ConfigServerScreen;
import views.SeatConfigScreen;

public class ConfigServerScreenController implements ActionListener{
    private ConfigServerScreen view;
    private MovieTimeService movieTheaterSer;
    public ConfigServerScreenController(ConfigServerScreen view)
    {
    	this.view = view;
    	movieTheaterSer = new MovieTimeService();
    }
    
    
    public void configBroadcast(List<MovieTime> movieTimes)
    {
    	this.view.serverThread.broadcast(movieTimes);
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == view.stageConfigButton)
		{
			new SeatConfigScreen(this.view);
		}
		else if(e.getSource() == view.createTimeBtn)
		{
			String fromTime = view.timeStartText.getText();
			String toTime = view.timeEndText.getText();
			
			Boolean checkValid = TimeZoneUtitls.checkTimeFormat(fromTime) && TimeZoneUtitls.checkTimeFormat(toTime);
			if(!checkValid)
			{
				JOptionPane.showMessageDialog(view, "Dữ liệu phải ở dạng hh:mm:ss");
				return;
			}
			
			try {
				boolean check = movieTheaterSer.addMovieTime(fromTime, toTime);
				if(check)
				{
					JOptionPane.showMessageDialog(view, "Thêm suất chiếu thành công");
					this.view.updateMovieTimeCombobox(TimeZoneUtitls.getTimeZoneValueAsString(fromTime, toTime));
					List<MovieTime> movieTimes = movieTheaterSer.getMovieTimes();
					configBroadcast(movieTimes);
				}
				else
				{
					JOptionPane.showMessageDialog(view, "Thêm suất chiếu thất bại");
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(view, e1.getMessage());
			}
		}
		else if(e.getSource() == view.deleteTimeBtn)
		{
			String fromTime = view.timeStartText.getText();
			String toTime = view.timeEndText.getText();
			Boolean check =  movieTheaterSer.deleteMovieTime(fromTime, toTime);
			if(check)
			{
				// update client
				List<MovieTime> movieTimes = movieTheaterSer.getMovieTimes();
				this.view.updateMovieTimeCombobox(null);
				this.view.updateSeatMap();
				this.view.resetTextField();
				// broadcast
				configBroadcast(movieTimes);
				// Show dialog
				JOptionPane.showMessageDialog(view, "Xóa suất chiếu thành công");	
			}
			else
			{
				JOptionPane.showMessageDialog(view, "Xóa suất chiếu thất bại");
			}
		}
	}
}
