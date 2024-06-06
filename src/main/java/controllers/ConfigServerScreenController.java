package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.List;

import javax.swing.JOptionPane;

import models.Movie;
import models.MovieTime;
import service.MovieService;
import service.MovieTimeService;
import utils.TimeZoneUtitls;
import views.ConfigServerScreen;
import views.MovieConfigScreen;
import views.SeatConfigScreen;

public class ConfigServerScreenController implements ActionListener{
    private ConfigServerScreen view;
  
    public ConfigServerScreenController(ConfigServerScreen view)
    {
    	this.view = view;
    }
   
    public void configBroadcast(List<Movie> movies)
    {
    	this.view.serverThread.broadcast(movies);
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
			
			String movieName = (String) view.movieModelCombobox.getSelectedItem();
			boolean check = MovieService.getInstance().addMovieTime(movieName, fromTime, toTime);
			if(check)
			{
				JOptionPane.showMessageDialog(view, "Thêm suất chiếu thành công");
				this.view.updateMovieTimeCombobox(TimeZoneUtitls.getTimeZoneValueAsString(fromTime, toTime));
				//List<MovieTime> movieTimes = movieTheaterSer.getMovieTimes();
				//configBroadcast(movieTimes);
			}
			else
			{
				JOptionPane.showMessageDialog(view, "Thêm suất chiếu thất bại");
			}
		}
		else if(e.getSource() == view.deleteTimeBtn)
		{
			String movieName = (String) view.movieNameCombobox.getSelectedItem();
			String fromTime = view.timeStartText.getText();
			String toTime = view.timeEndText.getText();
			
			if(movieName != null && fromTime != null && toTime != null)
			{
				Boolean check = MovieService.getInstance().deleteMovieTime(movieName, fromTime, toTime);
			    if(check)
			    {
				  // update client
				  List<Movie> movies = MovieService.getInstance().getMovies(); 
				  
				  this.view.updateMovieTimeCombobox(null);
				  this.view.updateSeatMap();
				  this.view.resetTextField();
				  // broadcast
				  configBroadcast(movies);
				  // Show dialog
				  JOptionPane.showMessageDialog(view, "Xóa suất chiếu thành công");	
			     }
				 else
				 {
					JOptionPane.showMessageDialog(view, "Xóa suất chiếu thất bại");
				 }
			}
			
		}
		else if(e.getSource() == view.movieConfigButton)
		{
		   new MovieConfigScreen(view);
		}
		
	}
}
