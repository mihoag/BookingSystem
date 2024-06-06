package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import models.Movie;
import models.MovieTime;
import service.MovieService;
import utils.TimeZoneUtitls;
import views.ConfigServerScreen;
import views.SeatConfigScreen;

public class SeatConfigScreenController implements ActionListener{

	private SeatConfigScreen view;
	private ConfigServerScreen serverView;
    
	public SeatConfigScreenController(SeatConfigScreen view, ConfigServerScreen serverScreen)
	{
		this.view = view;
		this.serverView = serverScreen;
	}
	
	  
    public void configBroadcast(List<Movie> movies)
    {
    	this.serverView.serverThread.broadcast(movies);
    }
	
    public boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
	public boolean checkInputData(String name, String rowNum, String seatPerRow, String price)
	{
		if(name == null || name.equals(""))
		{
			JOptionPane.showMessageDialog(view, "Tên khu không hợp lệ");
			return false;
		}
		
		if(rowNum == null || !isNumeric(rowNum))
		{
			JOptionPane.showMessageDialog(view, "Dữ liệu số hàng không hợp lệ");
			return false;
		}
		
		if(seatPerRow == null || !isNumeric(seatPerRow))
		{
			JOptionPane.showMessageDialog(view, "Dữ liệu số chỗ ngồi không hợp lệ");
			return false;
		}
		
		if(price == null || !isNumeric(price))
		{
			JOptionPane.showMessageDialog(view, "Dữ liệu giá tiền không hợp lệ");
			return false;
		}
		
		return true;
	}
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	     if(e.getSource() == view.addBtn)
	     {
	    	 try {
	    		String movieName = (String) serverView.movieModelCombobox.getSelectedItem();
	    		// Check if exist movie time
	    		List<MovieTime> lsMovieTime = MovieService.getInstance().getListMovieTimesFromMovie(movieName);
	    		if(lsMovieTime.size() == 0)
	    		{
	    			JOptionPane.showMessageDialog(view, "Danh sách suất chiếu đang rỗng. Cần thêm 1 suất chiếu trước!");
	    			return;
	    		}
	    		 
	    		if(!checkInputData(view.zoneNameTextField.getText(),view.rowNumTextField.getText(),view.seatNumPerRowText.getText(),view.priceTextField.getText()))
	    		{
	    			return;
	    		}
	    		
	    		String name = view.zoneNameTextField.getText();
	 	        Integer rowNum = Integer.parseInt(view.rowNumTextField.getText());  
	 	        Integer seatNumPerRow = Integer.parseInt(view.seatNumPerRowText.getText());
	 	        Double price = Double.parseDouble(view.priceTextField.getText());
	 	         
	 	        ///
	 	        String movieTime = (String) serverView.MovieTimeModelCombobox.getSelectedItem();
	 	        String[] components  = TimeZoneUtitls.splitTimeZone(movieTime);

	 	        /// 
	 	        boolean check = MovieService.getInstance().addZone(movieName, components[0], components[1],name, rowNum, seatNumPerRow, price);
	 	        if(check)
	 	        {
	 	           JOptionPane.showMessageDialog(view, "Thêm khu thành công");
	 	           // update table
	 	           view.zoneInfoTableModel.addRow(new Object[] {name, rowNum, seatNumPerRow, price});
	 	           view.resetTextField();
	 	           // update seat map
	 	           serverView.updateSeatMap();
	 	           // update client
				   List<Movie> movies = MovieService.getInstance().getMovies();
				   configBroadcast(movies);
	 	        }
	 	        else
	 	        {
	 	           JOptionPane.showMessageDialog(view, "Thêm khu thất bại");	
	 	        }
			} catch (Exception e2) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(view, e2.getMessage());
			}
	     } 
	     
	     else if(e.getSource() == view.deleteBtn)
	     {
	        try {
	        	int cnt =  view.zoneInfoTable.getSelectedRow();
				String zoneName = (String) view.zoneInfoTable.getValueAt(cnt, 0);
				String movieName = (String) serverView.movieModelCombobox.getSelectedItem();
				String zoneTime = (String) serverView.movieTimeCombobox.getSelectedItem();
				String[] components = TimeZoneUtitls.splitTimeZone(zoneTime);
				
			    boolean check = MovieService.getInstance().deleteZoneByName(movieName, components[0], components[1], zoneName);
				
				if(check)
				{
				   view.updateZoneTable();
				   serverView.updateSeatMap();  
				   // update client
					List<Movie> movies = MovieService.getInstance().getMovies();
					configBroadcast(movies);
				   JOptionPane.showMessageDialog(view, "Xóa khu thành công");
			    }
				else
				{
					JOptionPane.showMessageDialog(view, "Xóa khu thất bại");
				}	
			} catch (Exception e2) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(view, e2.getMessage());
			}
	     }
	}

}
