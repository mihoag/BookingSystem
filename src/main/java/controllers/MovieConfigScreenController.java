package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import models.MovieTime;
import service.MovieService;
import service.MovieTimeService;
import views.ConfigServerScreen;
import views.MovieConfigScreen;
import views.SeatConfigScreen;

public class MovieConfigScreenController implements ActionListener {


	private MovieConfigScreen movieConfigView;
	private MovieTimeService service;
	private ConfigServerScreen serverView;
	
	public MovieConfigScreenController(MovieConfigScreen movieConfigView, ConfigServerScreen serverView)
	{
		this.movieConfigView = movieConfigView;
		this.serverView = serverView;
		service = new MovieTimeService();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == movieConfigView.addBtn)
		{
			String movieName = movieConfigView.movieNameTextField.getText();
			Calendar movieDate = (Calendar) movieConfigView.movieDateInput.getCalendar();
		    if(movieDate == null)
		    {
		       JOptionPane.showMessageDialog(movieConfigView, "Vui lòng chọn ngày chiếu!");	
		       return;
		    }
		    if(movieName == null || movieName.equals(""))
		    {
		       JOptionPane.showMessageDialog(movieConfigView, "Vui lòng nhập tên phim");	
		       return;
		    }
		    if (movieDate != null) {
		       LocalDate movieLocalDate = LocalDate.of(movieDate.get(Calendar.YEAR),
		            		movieDate.get(Calendar.MONTH) + 1,
		            		movieDate.get(Calendar.DAY_OF_MONTH));
		       LocalDate dateNow = LocalDate.now();
		       if(movieLocalDate.isBefore(dateNow) || movieLocalDate.equals(dateNow))
		       {
		    	   JOptionPane.showMessageDialog(movieConfigView, "Suất chiếu phải sau ngày hiện tại");
		    	   return;
		       }
		 
		       Boolean check = MovieService.getInstance().addMovie(movieName, movieLocalDate);
		       if(check)
		       {
		    	   // Reset input field
		    	   movieConfigView.resetInputField();
		    	   JOptionPane.showMessageDialog(movieConfigView, "Thêm phim thành công");  
		    	   
		    	   
		    	   // update table
		    	   movieConfigView.updateTable();
	 	           serverView.updateMovieCombobox(movieName);
		       }
		       else
		       {  
		    	  JOptionPane.showMessageDialog(movieConfigView, "Tên phim đã tồn tại");
		       }
		    }
		}
		else if(e.getSource() == movieConfigView.deleteBtn)
		{
			try {
	        	int cnt =  movieConfigView.movieTable.getSelectedRow();
				String movieName = (String) movieConfigView.movieTable.getValueAt(cnt, 1);
				Boolean check = MovieService.getInstance().deleteMovie(movieName);
				
				if(check)
				{
				   // update table
			       movieConfigView.updateTable();
		 	       serverView.updateMovieCombobox(null);
		 	       JOptionPane.showMessageDialog(movieConfigView, "Xóa phim thành công");
			    }
				else
				{
					JOptionPane.showMessageDialog(movieConfigView, "Xóa phim thất bại");
				}
				
			} catch (Exception e2) {
				// TODO: handle exception
			    e2.printStackTrace();
				JOptionPane.showMessageDialog(movieConfigView, e2.getMessage());
			}
		}
	}

}
